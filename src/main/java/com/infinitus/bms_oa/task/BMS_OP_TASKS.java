package com.infinitus.bms_oa.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.bms_op.empty.*;
import com.infinitus.bms_oa.bms_op.empty.VO.OP_ScondBody_PaymentApplicationVO;
import com.infinitus.bms_oa.bms_op.empty.VO.OP_ThirdBody_NopoItemsVO;
import com.infinitus.bms_oa.bms_op.service.Bms_po_api_returnService;
import com.infinitus.bms_oa.bms_op.service.NopoItemsService;
import com.infinitus.bms_oa.bms_op.service.PaymentApplicationVO_Service;
import com.infinitus.bms_oa.enums.OaFlagEnum;
import com.infinitus.bms_oa.utils.DateUtil;
import com.infinitus.bms_oa.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class BMS_OP_TASKS {
    @Autowired
    private NopoItemsService nopoItemsService;

    @Autowired
    private PaymentApplicationVO_Service payService;

    @Autowired
    private Bms_po_api_returnService bmsPoApiReturnService;

    @Value("${BMS.URL.bmsToOP}")
    private String  url;

    @Value("${target.token.bmsToOP}")
    private String token;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${BMS_OP_TASKS.signature}")
    private String  signature;

    /**
     * 每30s处理一次   1000 * 1 * 30
     * 执行定时任务，用于将bms_oa_log中所需同步数据封装同步
     */
    @Scheduled(fixedRate = 1000 * 1 * 30)
    public void BMS_OP_TASKS() {
        log.info("·································"+ new DateUtil().getNowDate());
        synOpPayMent();
    }

    /**
     * 1.查询主单的未同步订单，获取单号
     * 2.按照单号查询明细单
     * 3.封装数据
     * 4.提交数据到op
     * 5.接受返回信息插入相关表bms_po_api_return
     * 6.根据单号更新同步状态
     * */
    private void  synOpPayMent(){
        //1、查询OP_ScondBody_PaymentApplicationVO
        List<OP_ScondBody_PaymentApplication> list = payService.getAllPaymentApplicationVO();
        log.info("【list】=:{}",list);
        if (list.size() > 0) {
            list.stream().forEach(e->{
                //第一层
                OP_First_Body first_body = new OP_First_Body();
                //第二层1.
                OP_ScondBody_PersonVO personVO = new OP_ScondBody_PersonVO();
                personVO.setOrgCode(e.getOrgCode());
                personVO.setUserRealName(e.getUserRealName());
                personVO.setUsername(e.getUsername());
                //讲实体类转化为json对象
                JSONObject perJson = (JSONObject) JSONObject.toJSON(personVO);
                first_body.setPersonVo(JSON.toJSONString(perJson));
                log.info("【first_body】=:{}",first_body);
                //第二层2.
                OP_ScondBody_PaymentApplicationVO paymentApplicationVO = new OP_ScondBody_PaymentApplicationVO();
                BeanUtils.copyProperties(e,paymentApplicationVO);
                /**2023-01-13
                 * 由于业务更新需求，新增oa审批流程字段，顾对原程序进行调整
                 * */
                String invoiceNO = "IN";
                if (paymentApplicationVO.getInvoiceNO() != null && !"".equals(paymentApplicationVO.getInvoiceNO())) {
                    invoiceNO = paymentApplicationVO.getInvoiceNO();
                    String a = payService.getOAflowID(paymentApplicationVO.getInvoiceNO());
                    if (a != null && !"".equals(a)) {
                        paymentApplicationVO.setBmsOAId(a);
                    }
                    log.info("流程id不存在=============");
                }
                //将string类型的时间转化为时间戳
                try {
                    Date dateTimeApplicant = format.parse(e.getApplicantDate());
                    paymentApplicationVO.setApplicantDate((dateTimeApplicant.getTime()));
                    Date dateTimePaymentDateLimit =format.parse(e.getPaymentDateLimit());
                    paymentApplicationVO.setPaymentDateLimit(dateTimePaymentDateLimit.getTime());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                //2、查询明细
                List<OP_ThirdBody_NopoItems> nopoItemsList = nopoItemsService.getAllNopoItemsServiceByInvoice_no(e.getInvoiceNO());
                List<OP_ThirdBody_NopoItemsVO> nopoItemsVOS = new ArrayList<>();

                if (nopoItemsList != null && nopoItemsList.size() > 0) {
                    for (OP_ThirdBody_NopoItems item : nopoItemsList) {
                        OP_ThirdBody_NopoItemsVO nopoItemsVO = new OP_ThirdBody_NopoItemsVO();
                        paymentApplicationVO.setExpenseCategoryCode(item.getExpenseCategoryCode());
                        paymentApplicationVO.setExpenseCategoryName(item.getExpenseCategoryName());
                        paymentApplicationVO.setFlowInitiator(item.getFlowInitiator());
                        paymentApplicationVO.setFlowInitiatorName(item.getFlowInitiatorName());
                        item.setTty(item.getAccountCode().concat("/"+item.getAccountName()));
                        if ("".equals(item.getNoBillApplyId()) || null == item.getNoBillApplyId()) {
                            item.setNoBillApplyId("0");
                        }
                        BeanUtils.copyProperties(item, nopoItemsVO);
                        if (item.getNoBillApplyId().trim() != null && !"".equals(item.getNoBillApplyId().trim())) {
                            log.info("synOpPayMent2=======================:Integer.parseInt(item.getNoBillApplyId()):{}", Long.parseLong(item.getNoBillApplyId().trim()));
                            nopoItemsVO.setNoBillApplyId(Long.parseLong(item.getNoBillApplyId().trim()));
                        }
                        nopoItemsVOS.add(nopoItemsVO);
                    }
                }
                paymentApplicationVO.setNoPoItems(nopoItemsVOS);

                //3、封装数据
                JSONObject paymentApplicationVOJson = (JSONObject) JSONObject.toJSON(paymentApplicationVO);
                first_body.setPaymentApplicationVo(JSON.toJSONString(paymentApplicationVOJson));

                String jsonObject = JSONObject.toJSONString(first_body);
                log.info("info【url】=:{}", url);
                log.info("【synOpPayMent】jsonObject={}",jsonObject);
                try {
                    //4.httppost提交数据到OA
                    JSONObject resultJson = HttpUtil.doPostJson(url, jsonObject, "signature", signature);
                    log.info("【提交接口返回结果】=:{}", resultJson);
                    //5.接受返回信息插入相关表bms_po_api_return
                    if (null != resultJson.get("success") && resultJson.get("success").equals(true)) {
                        Bms_po_api_return bmsPoApiReturn = new Bms_po_api_return();
                        JSONObject bmsPoApiReturnJson = JSONObject.parseObject(String.valueOf(resultJson.get("returnObject")));//将建json对象转换为Person对象
                        bmsPoApiReturn.setDocno((String) bmsPoApiReturnJson.get("Docno"));
                        bmsPoApiReturn.setInvoiceNO((String) bmsPoApiReturnJson.get("invoiceNO"));
                        bmsPoApiReturn.setMsg_Log((String) bmsPoApiReturnJson.get("Msg_Log"));
                        bmsPoApiReturn.setMsg_Type((String) bmsPoApiReturnJson.get("Msg_Type"));
                        if (!bmsPoApiReturnJson.get("OP_ID").equals("") && null != bmsPoApiReturnJson.get("OP_ID")) {
                            bmsPoApiReturn.setOP_ID(Long.valueOf((String) bmsPoApiReturnJson.get("OP_ID")));
                        }
                        int i = bmsPoApiReturnService.createBms_po_api_return(bmsPoApiReturn);
                        log.info("【createBms_po_api_return】i : {}", i);
                        //6.根据单号更新同步状态
                        nopoItemsService.updateOpFlag(OaFlagEnum.SUCCESS.getCodeString(), e.getInvoiceNO());
                        payService.updateOpFlag(OaFlagEnum.SUCCESS.getCodeString(), e.getInvoiceNO());
                        log.info("【synOpPayMent_result】同步到OP成功");
                    }else{
                        //优化接口，记录因业务问题导致同步失败的原因，并呈现在日志表查看
                        Bms_po_api_return bmsPoApiReturn = new Bms_po_api_return();
                        bmsPoApiReturn.setInvoiceNO(invoiceNO);//报错单号
                        bmsPoApiReturn.setMsg_Type("f");//报错类型
                        bmsPoApiReturn.setMsg_Log(String.valueOf(resultJson.get("exceptionMessage")));//报错信息

                        int i = bmsPoApiReturnService.createBms_po_api_return(bmsPoApiReturn);
                        log.info("【createBms_po_api_return】i : {}", i);
                        log.info("【synOpPayMent_result】未成功同步");
                    }
                } catch (Exception ex) {
                    log.info("【synOpPayMent】同步到OP失败=:{}",ex);
                    nopoItemsService.updateOpFlag(OaFlagEnum.FALSE.getCodeString(), e.getInvoiceNO());
                    payService.updateOpFlag(OaFlagEnum.FALSE.getCodeString(), e.getInvoiceNO());
                }
            });
        }

    }
}
