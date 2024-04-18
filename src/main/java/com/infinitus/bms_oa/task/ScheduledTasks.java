package com.infinitus.bms_oa.task;

import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.enums.BmsOaLogStatusEnum;
import com.infinitus.bms_oa.enums.OaFlagEnum;
import com.infinitus.bms_oa.pojo.*;
import com.infinitus.bms_oa.service.BmsBillAdujestService;
import com.infinitus.bms_oa.service.Bms_OA_logService;
import com.infinitus.bms_oa.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ScheduledTasks {
    @Autowired
    private BmsBillAdujestService billService;

    @Autowired
    private Bms_OA_logService logService;

    @Value("${BMS.URL.billToOA}")
    private String  url;

    @Value("${BMS.flow.value}")
    private String  flow;

    private  static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private  static final SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 每30s处理一次   1000 * 1 * 30
     * 执行定时任务，用于将bms_oa_log中所需同步数据封装同步
     */
    @Scheduled(fixedRate = 1000 * 2 * 30)
    public void bmsToOA() {
        String keyValue = simpleDateFormat.format(new Date());
        try {
            log.info("【定时任务bmsToOA()处理开始】：keyValue:{}", keyValue);
            String statusFlag = BmsSynOA();
            log.info("【定时任务bmsToOA()处理结束】:statusFlag:{}", statusFlag);
        } catch (Exception ex) {
            log.info("【bmsToOA】执行错误:ex:{}", ex);
        }
    }

    /**
     * 每15s处理一次   1000 * 1 * 15
     * 执行定时任务，将bms_oa_log中已经回转状态的数据进行更改审批状态： status
     */
    @Scheduled(fixedRate = 1000 * 2 * 15)
    public void updateBillStatus() {
        String keyValue = simpleDateFormat.format(new Date());
        try {
            log.info("【定时任务updateBillStatus()处理开始】：keyValue:{}", keyValue);
            scheduledBmsOaLog();
            log.info("【定时任务updateBillStatus()处理结束】");
        } catch (Exception ex) {
            log.info("【ScheduledTasks】updateBillStatus执行错误:ex:{}", ex);
        }
    }

    /**
     * BMS_OA ->多对一
     * 1.取各类型数据，运输/仓储:异常，调整，扣款
     * 2.封装数据{
     *     Infinitus.MainTable.SelectUserV20
     *     Infinitus.InfinitusDetailTables.InfinitusDetailTablesRow
     * }
     * 3.post数据到OA
     * 4.改变bms_oa_log的同步状态与同步时间
     * 5.改变两张流程表的oa_flag
     * */
    public String BmsSynOA(){
        //1.从bms_oa_log取合并的未上传的单号
        List<Bms_OA_log> logList = logService.getBmsOaLog();
        List<String> noArray = new ArrayList<>();
        logList.stream().forEach(e->{
            Infinitus infinitus = new Infinitus();//Infinitus|-|Json第一层
            String loginName = "";
            log.info("【BmsSynOA()】·········begin··········");
            if (null != e.getCreate_id() && !"".equals(e.getCreate_id())) {
                loginName = billService.selectLoginNameById(e.getCreate_id());
            }
            log.info("【BmsSynOA()】，loginName:{}", loginName);
            infinitus.setWorkcode(loginName);//合并人 loginName
            infinitus.setWorkflowId(flow);//OA生产使用249，OA测试使用367
            infinitus.setRequestName("物流费用结算调整申请单："+e.getCode());//主单号

            InfinitusMainTable table = new InfinitusMainTable(); //MainTable 主表
            List<InfinitusDetailTables> detailTablesList = new ArrayList<>();//明细表，第一层
            InfinitusDetailTables infinitusDetailTables = new InfinitusDetailTables();
            infinitusDetailTables.setIndex(1);
            List<InfinitusDetailTablesRow> infinitusDetailTablesRowList = new ArrayList<>();//明细表第二层，具体明细

            InfinitusSelectUserV20 infinitusSelectUserV20 = new InfinitusSelectUserV20();//MainTable.SelectUserV20第三层
            infinitusSelectUserV20.setType("resourceId");
            infinitusSelectUserV20.setValue(loginName);
            table.setSqr(infinitusSelectUserV20);

            Double d = new Double(0);//用于计算总金额

            //2.用单号查找对应流程表详细数据
            List<BmsBillAdjust> adjustList = billService.getBillListDetailView(e.getCode(),e.getCreate_id());
            log.info("【BmsSynOA().adjustList】,adjustList:{}", adjustList);
            if (null == adjustList && adjustList.size() < 1) return;
            //3.打包数据提交接口:
            Date jsnyDateTable = null;
            for (int i = 0; i < adjustList.size(); i++) {
                //log.info("----------------------------------------------adjustList.size():{}",adjustList.size());
                Date jsnyDate = new Date();
                log.info("【BmsSynOA().jsnyDate】,jsnyDate:{}", jsnyDate);
                //log.info("【BmsSynOA().adjustList】,adjustList.get(i).getSettle_year_month():{}", adjustList.get(i).getSettle_year_month());
                if (null!=adjustList.get(i).getSettle_year_month() && !"".equals(adjustList.get(i).getSettle_year_month())) {
                    jsnyDate = adjustList.get(i).getSettle_year_month();
                    log.info("【BmsSynOA().jsnyDate1】,jsnyDate:{}", jsnyDate);
                    if (jsnyDateTable == null || adjustList.get(i).getSettle_year_month().getTime() > jsnyDateTable.getTime()) {
                        jsnyDateTable = adjustList.get(i).getSettle_year_month();
                    }
                }
                //log.info("【BmsSynOA().jsnyDate2】,jsnyDate:{}", jsnyDate);
                //table.setJsny(simpleDateFormat2.format(jsnyDate));//需求指出每个结算年月一致，就随机取最后一个为准
                /*//封装每行明细的数据
                InfinitusDetailTablesRow infinitusDetailTablesRow
                        = new InfinitusUtil().setInfinitusDetailTablesRow(adjustList.get(i));*/
                InfinitusDetailTablesRow infinitusDetailTablesRow = new InfinitusDetailTablesRow();
                infinitusDetailTablesRow.setSqr(adjustList.get(i).getCreate_id());
                infinitusDetailTablesRow.setDh(adjustList.get(i).getAdj_no());
                infinitusDetailTablesRow.setSqtjrq(simpleDateFormat.format(adjustList.get(i).getAdj_dt()));//申请提交日期
                if (null != jsnyDate && !"".equals(jsnyDate)) {
                    infinitusDetailTablesRow.setJsny(simpleDateFormat.format(jsnyDate));
                }
                infinitusDetailTablesRow.setJsc(adjustList.get(i).getSettle_wh_code());
                infinitusDetailTablesRow.setGys(adjustList.get(i).getVendor_no());
                infinitusDetailTablesRow.setCwkm(adjustList.get(i).getFinance_account_no());
                infinitusDetailTablesRow.setDzje(adjustList.get(i).getAdj_amount());
                String tznr=adjustList.get(i).getAdj_type();
                if ("01".equals(tznr)&&adjustList.get(i).getAdj_no().indexOf("IA-")>=0) {
                    tznr = "运费费用调整";
                } else if("02".equals(tznr)&&adjustList.get(i).getAdj_no().indexOf("IA-")>=0){
                    tznr = "仓储费用调整";
                }else if("01".equals(tznr)&&adjustList.get(i).getAdj_no().indexOf("TZ-")>=0){
                    tznr = "运输扣款调整";
                }else if("02".equals(tznr)&&adjustList.get(i).getAdj_no().indexOf("TZ-")>=0){
                    tznr = "仓储扣款调整";
                }else if("01".equals(tznr)&&(null==adjustList.get(i).getAdj_no()||"".equals(adjustList.get(i).getAdj_no()))){
                    tznr = "运输异常调整";
                }else if("02".equals(tznr)&&(null==adjustList.get(i).getAdj_no()||"".equals(adjustList.get(i).getAdj_no()))){
                    tznr = "仓储异常调整";
                }

                infinitusDetailTablesRow.setDznr(tznr);
                infinitusDetailTablesRow.setDzyy(adjustList.get(i).getAdj_reason());
                infinitusDetailTablesRow.setDzyysm(adjustList.get(i).getComments());
                infinitusDetailTablesRow.setHzdm(adjustList.get(i).getOwner_key());
                infinitusDetailTablesRow.setHzmc(adjustList.get(i).getOwner_name());

                //回收infinitusDetailTablesRow==>>infinitusDetailTablesRowList
                infinitusDetailTablesRowList.add(infinitusDetailTablesRow);
                d += Math.abs(adjustList.get(i).getAdj_amount());
            }
            table.setZje(d);
            table.setDh(e.getCode());
            if (null == jsnyDateTable || "".equals(jsnyDateTable)) {
                jsnyDateTable = new Date();
            }
            //log.info("-------------------------------table.setJsny=", simpleDateFormat2.format(jsnyDateTable));
            table.setJsny(simpleDateFormat2.format(jsnyDateTable));
            //log.info("【BmsSynOA().table.getJsny()】,table.getJsny():{}", table.getJsny());
            if (null == table.getJsny() || "".equals(table.getJsny())) {
                table.setJsny(simpleDateFormat2.format(new Date()));
            }
            infinitus.setMainTable(table);
            infinitusDetailTables.setRows(infinitusDetailTablesRowList);
            detailTablesList.add(infinitusDetailTables);
            infinitus.setDetailTables(detailTablesList);

            String jsonObject = JSONObject.toJSONString(infinitus);
            log.info("【BmsSynOA】····，jsonObject:{}",jsonObject);
            //4.httppost提交数据到OA
            JSONObject resultJson = HttpUtil.doPostJson(url,jsonObject,"Authorization","");
            log.info("【提交接口返回数据resultJson】----:resultJson:{}", resultJson);
            if (null != resultJson.get("success") && resultJson.get("success").equals(true)) {
                //log.info("【BmsSynOA修改已传oa_flag的值】,e.getCode():{}", e.getCode());
                billService.updateOA_flag(OaFlagEnum.SUCCESS.getCode(), e.getCode());//提交成功则改变oa_flag的值0 标识未上传  2 已经上传  4上传失败
                //log.info("【 table.getJsny().substring(0, 7)】， table.getJsny().substring(0, 7)：{}", table.getJsny().substring(0, 7));
                String jsny = billService.getBillListDetail_JSNY(e.getCode(), e.getCreate_id());
                if (null == jsny || "".equals(jsny)) {
                    jsny=simpleDateFormat2.format(new Date());
                }
                logService.updateOaFlagAndSettleDate(OaFlagEnum.SUCCESS.getCode(), e.getCode(), jsny.substring(0, 7));
                log.info("【BmsSynOA修改已传oa_flag的值】", OaFlagEnum.SUCCESS.getMsg());
            } else {
                log.info("【BmsSynOA修改传输失败的oa_flag的值】,e.getCode():{}", e.getCode());
                billService.updateOA_flag(OaFlagEnum.FALSE.getCode(), e.getCode());
                logService.updateOaFlag(OaFlagEnum.FALSE.getCode(), e.getCode());
                log.info("【BmsSynOA修改传输失败的oa_flag的值】", OaFlagEnum.FALSE.getMsg());
            }
        });
        return "SUCCESS";
    }

    /**
     * 1.定时巡查bms_oa_log表，status=2，则为已经同步并审批的记录
     * 2.将此条记录中的两张流程表中的记录的审批状态status=20，审批时间修改
     * 3.bms_oa_log 审批状态，2：已审批，需改流程表状态；6：已改
     */
    public void scheduledBmsOaLog() {
        try {
            //查询需要更新流程表的数据
            List<Bms_OA_log> logList = logService.getBillCodeByStatus(BmsOaLogStatusEnum.APPROVAL.getCodeString());
            if (logList.size() < 1) return;
            List<String> list = new ArrayList<>();
            logList.stream().forEach(e->{
                //更新流程表status与审批时间
                billService.updateStatusAndApeDate(e.getCode(), e.getApproval_dt(), "20");
                list.add(e.getCode());
            });
            if (list.size() < 1) return;
            //改变bms_oa_log的status状态
            logService.updateLogStatus(BmsOaLogStatusEnum.APPROVALED.getCodeString(), list);
            log.info("【scheduledBmsOaLog】，执行success,logList:{}", logList);
        } catch (Exception e) {
            log.info("【scheduledBmsOaLog】，执行错误，e:{}", e);
        }
    }
}
