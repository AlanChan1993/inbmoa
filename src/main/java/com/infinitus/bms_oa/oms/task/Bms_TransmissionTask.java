package com.infinitus.bms_oa.oms.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.oms.enums.StatusEnum;
import com.infinitus.bms_oa.oms.pojo.*;
import com.infinitus.bms_oa.oms.service.Bms_Wms_Order_ExpressService;
import com.infinitus.bms_oa.oms.service.OMSBMSReturnOrderInfoService;
import com.infinitus.bms_oa.oms.utils.*;
import com.infinitus.bms_oa.bms_free.empty.PlatformType;
import com.infinitus.bms_oa.enums.ResultEnums;
import com.infinitus.bms_oa.exception.BMSException;
import com.infinitus.bms_oa.utils.DateUtil;
import com.infinitus.bms_oa.utils.IpassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
public class Bms_TransmissionTask {

    @Autowired
    private Bms_Wms_Order_ExpressService service;

    @Autowired
    private OMSBMSReturnOrderInfoService infoService;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${OMS.URL}")
    private String baseUrl;

    @Value("${OMS.secret}")
    private String secret;

    @Value("${OMS.customerId}")
    private String customerId;

    @Value("${OMS.TMmethod}")
    private String TMmethod;

    @Value("${OMS.ROImethod}")
    private String ROImethod;

    @Value("${OMS.app_key}")
    private String app_key;

    // dev 以下配置要素需要找iPaaS管理员获取 -- 严建芳
    //private static final String I_PASS_AK_DEV = "mo";

    @Value("${IPASS.I_PASS_AK_DEV}")
    private String I_PASS_AK_DEV;
    
    @Value("${IPASS.BASE_URL_DEV}")
    private String BASE_URL_DEV;

    @Value("${IPASS.I_PASS_SK_DEV}")
    private String I_PASS_SK_DEV;

    @Value("${IPASS.APP_KEY_DEV}")
    private String APP_KEY_DEV;

    @Value("${IPASS.IpassUri}")
    private String IpassUri;
   //---------------------------定时器开------------------------

    @Scheduled(cron="0 30 3 * * ?")//凌晨3点半
    //@Scheduled(fixedRate = 1000 * 5 * 60)//测试间隔一小时
    public void excuseByDay() throws Exception {
        getOmsReturnOrderInfo();
    }

    //@Scheduled(fixedRate = 1000 * 5 * 60)//生产
    @Scheduled(fixedRate = 1000 * 1 * 60)//测试使用
    public void excuseByTima() throws Exception {
        executeTransmission();
    }

    //---------------------------定时器关------------------------

    /**
     * 运单关联退货单信息
     * 从中台拉取数据存入新建表OMS_BMS_ReturnOrder_Info
     * 作为数据中间表，由bms程序去判断处理
     * 按照金波要求 生产上每天凌晨3点取一次该数据
     * */
    public void getOmsReturnOrderInfo() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");

        PlatformType platformType = new PlatformType();
        platformType.setUseTimeStart(dfm.format(date)+" 00:00:00");
        platformType.setUseTimeEnd(dfm.format(date)+" 23:59:59");
        log.info("platformType=:{}" + platformType);

        // POST 接口请求方式及参数，接入方根据请求的具体接口做相应的变动
        //String uri = "/openapi/oms-cnbp/v1/return-order/query";//新接口已变更请求地址
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("updateTimeStart", platformType.getUseTimeStart());//"2023-07-01 00:00:00"  || platformType.getUseTimeStart()
        mapData.put("updateTimeEnd", platformType.getUseTimeEnd());
        mapData.put("currentPage", 1);
        mapData.put("pageSize", 500);
        log.info("【TransmissionTask.getOmsReturnOrderInfo】，mapData:{}", mapData);
        JSONObject jsonObject = new JSONObject(mapData);
        log.info("【TransmissionTask.getOmsReturnOrderInfo】postReq()调用前。。。。jsonObject:{}", jsonObject.toString());

        try {
            JSONObject response = new IpassUtil().postReq(IpassUri, jsonObject.toString(), APP_KEY_DEV, I_PASS_AK_DEV, I_PASS_SK_DEV, BASE_URL_DEV);
            log.info("【TransmissionTask.getOmsReturnOrderInfo】response:{}", response);
            if (response.get("total").equals(0)) {
                throw new BMSException(ResultEnums.ZERO);
            }

            List<OMSBMSReturnOrderInfo> infoList = (List<OMSBMSReturnOrderInfo>) JSON.parse(response.get("data").toString());

            //调用接口获取数据完成后插入数据
            log.info("【TransmissionTask.getOmsReturnOrderInfo】数据插入start");
            boolean result = infoService.createOMSBMSReturnOrderInfoList(infoList);
            log.info("【TransmissionTask.getOmsReturnOrderInfo】数据插入成功", result);
        } catch (Exception ex) {
            log.info("【TransmissionTask.getOmsReturnOrderInfo】拉取数据失败", ex);
        }

    }


    /**
     * 每20s处理一次   1000 * 1 * 20
     * 执行定时任务
     * bms_wms_order_express
     * status ='0'的数据才同步到OMS  同步成功 更新为2  失败为4   message记录异常信息 sys_date 记录同步时间
     */
    public void  executeTransmission() throws IOException {
        log.info("【TransmissionTask.executeTransmission】info", new DateUtil().getNowDate2());

        //1.查符合条件的数据
        List<Bms_Wms_Order_Express> orderExpressList = service.getBms_Wms_Order_Express(StatusEnum.NOSYN.getCodeString());
        if (orderExpressList.size() == 0) {
            log.info("【TransmissionTask.executeTransmission】orderExpressList.size()==0", orderExpressList.size());
            return;
        }

        for (int i = 0; i < orderExpressList.size(); i++) {
            //2.封装
            Bms_Wms_Order_Express bmsWmsOrderExpress = orderExpressList.get(i);
            Map<String, Object> mapData = new HashMap<>();
            mapData.put("expressCode", bmsWmsOrderExpress.getShipmentNo());
            mapData.put("shipmentNo", bmsWmsOrderExpress.getExpressCode());
            mapData.put("expressCompanyCode", bmsWmsOrderExpress.getExpressCompanyCode());

            LocalDateTime time = LocalDateTime.now();
            String synDate = df.format(time);

            AliParam aliParam = new AliParamUtil().pushTransmission(mapData, synDate, secret, customerId, TMmethod, app_key);
            log.info("【TransmissionTask.executeTransmission】aliParam=:{}", aliParam);

            ResultEntity resultEntity = new ResultEntity();
            String resultStr = null;
            //3.调用接口
            try {
                String url = baseUrl + "?app_key=infinitus_infor&method=" + TMmethod +
                        "&v=2.0&customerId=" + customerId + "&format=json&sign_method=md5&timestamp=" + URLEncoder.encode(synDate, "UTF-8") + "&sign=" + aliParam.getSign();
                resultStr = new RequestUtil().doPost(url, mapData);
                //log.info("【TransmissionTask.executeTransmission】resultStr=:{}", resultStr);
                log.info("--executeTransmission--");

                resultEntity = JSON.parseObject(resultStr, ResultEntity.class);
                log.info("【【TransmissionTask.executeTransmission】】===resultEntity=:{}", resultEntity);

                if (!"true".equals(resultEntity.getSuccess())) {
                    //失败后更新状态
                    service.update_Order_Express(StatusEnum.NoShipmentInformation.getCodeString(), bmsWmsOrderExpress.getID(), resultStr, synDate);
                    log.info("【TransmissionTask.executeTransmission】00推送中台失败后，更新status=1");
                    throw new BMSException(ResultEnums.FAIL);
                }

               //4.调用后更新记录状态
                //service.updateSattus(StatusEnum.SUCCESSFUL.getCodeString(), bmsWmsOrderExpress.getID());

                //synDate
                service.update_Order_Express(StatusEnum.SUCCESSFUL.getCodeString(), bmsWmsOrderExpress.getID(), resultStr, synDate);
                log.info("【TransmissionTask.executeTransmission】推送中台成功后，更新status=2");
            } catch (Exception ex) {
                log.info("【TransmissionTask.executeTransmission】11推送中台失败");
                //失败后更新状态
                service.update_Order_Express(StatusEnum.FAIL.getCodeString(), bmsWmsOrderExpress.getID(), resultStr, synDate);
                log.info("【TransmissionTask.executeTransmission】22推送中台失败后，更新status=4");
            }
        }
    }


}
