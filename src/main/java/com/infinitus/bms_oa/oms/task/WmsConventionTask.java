package com.infinitus.bms_oa.oms.task;

import com.alibaba.fastjson.JSON;
import com.infinitus.bms_oa.oms.pojo.AliParam;
import com.infinitus.bms_oa.oms.utils.ResultEntity;
import com.infinitus.bms_oa.oms.pojo.WmsConvention;
import com.infinitus.bms_oa.oms.service.WmsConventionService;
import com.infinitus.bms_oa.oms.utils.AliParamUtil;
import com.infinitus.bms_oa.oms.utils.RequestUtil;
import com.infinitus.bms_oa.bms_free.empty.PlatformType;
import com.infinitus.bms_oa.enums.ResultEnums;
import com.infinitus.bms_oa.exception.BMSException;
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
public class WmsConventionTask {
    @Autowired
    private WmsConventionService wmsConventionService;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${OMS.URL}")
    private String baseUrl;

    @Value("${OMS.secret}")
    private String secret;

    @Value("${OMS.customerId}")
    private String customerId;

    @Value("${OMS.WCmethod}")
    private String method;

    @Value("${OMS.app_key}")
    private String app_key;

    /**
     * 定时任务 生产
     * 需求变更为每日凌晨3点执行一次
     */
    @Scheduled(cron="0 15 3 * * ?")
    //@Scheduled(fixedRate = 1000 * 10 * 60)//一小时
    private void excuseWmsConventionTask() throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");

        PlatformType platformType = new PlatformType();
        platformType.setUseTimeStart(dfm.format(date)+" 00:00:00");
        platformType.setUseTimeEnd(dfm.format(date)+" 23:59:59");
        log.info("platformType=:{}" + platformType);

        Map<String, Object> mapData = new HashMap<>();
        mapData.put("startLastUpdateTime", platformType.getUseTimeStart());
        mapData.put("endLastUpdateTime", platformType.getUseTimeEnd());
        mapData.put("pageIndex", 1);
        mapData.put("pageSize", 500);

        LocalDateTime time = LocalDateTime.now();
        String synDate = df.format(time);

        AliParam aliParam = new AliParamUtil().pushTransmission(mapData, synDate, secret, customerId, method, app_key);
        ResultEntity resultEntity = new ResultEntity();
        String resultStr = null;
        //3.调用接口
        try {
            String url = baseUrl + "?app_key=" + app_key + "&method=" + method +
                    "&v=2.0&customerId=" + customerId + "&format=json&sign_method=md5&timestamp=" + URLEncoder.encode(synDate, "UTF-8") + "&sign=" + aliParam.getSign();
            resultStr = new RequestUtil().doPost(url, mapData);
            //log.info("【WmsConventionTask.excuseWmsConventionTask】resultStr=:{}", resultStr);

            resultEntity = JSON.parseObject(resultStr, ResultEntity.class);
            log.info("【WmsConventionTask.excuseWmsConventionTask】===resultEntity=:{}", resultEntity);

            if (!"true".equals(resultEntity.getSuccess())) {
                throw new BMSException(ResultEnums.FAIL);
            }

            //4.调用后更新记录状态
            //JSONObject object = (JSONObject) JSON.toJSON(resultEntity.getData());
            //WmsConvention wmsConvention = new WmsConvention();
            if (resultEntity.getData().length() <= 2) {
                throw new BMSException(ResultEnums.ZERO);
            }

            List<WmsConvention> wmsConventionList = (List<WmsConvention>) JSON.parse(resultEntity.getData());
            boolean createSult = wmsConventionService.createWmsConventionList(wmsConventionList);
            //createSult
            log.info("【WmsConventionTask.excuseWmsConventionTask】拉取中台数据成功插入，createSult:{}", createSult);
        } catch (Exception ex) {
            log.info("【WmsConventionTask.excuseWmsConventionTask】调用中台接口失败，ex:{}", ex);
        }
    }
}
