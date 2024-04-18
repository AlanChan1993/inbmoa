package com.infinitus.bms_oa.oms.task;

import com.alibaba.fastjson.JSON;
import com.infinitus.bms_oa.oms.pojo.AliParam;
import com.infinitus.bms_oa.oms.pojo.DTO.ResultData;
import com.infinitus.bms_oa.oms.pojo.DTO.SignItemDTO;
import com.infinitus.bms_oa.oms.utils.ResultEntity;
import com.infinitus.bms_oa.oms.pojo.SignItem;
import com.infinitus.bms_oa.oms.service.SignItemService;
import com.infinitus.bms_oa.oms.utils.AliParamUtil;
import com.infinitus.bms_oa.oms.utils.RequestUtil;
import com.infinitus.bms_oa.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LmtTask {
    @Autowired
    private SignItemService signItemService;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${OmsLmt.OrderSignMethod}")
    private String OrderSignMethod;

    @Value("${OmsLmt.customerId}")
    private String customerId;

    @Value("${OmsLmt.URL}")
    private String URL;

    @Value("${OmsLmt.secret}")
    private String secret;

    @Value("${OmsLmt.app_key}")
    private String app_key;

    /**
     * 需求暂定五分钟推送一次
     * */
    @Scheduled(fixedRate = 1000 * 5 * 60)
    //@Scheduled(fixedRate = 1000 * 1 * 60)//测试使用
    public void excuseBySec() throws Exception {
        putOrderSign();
    }

    /**
     * orderSign推送数据规则：status=0 或者 status=''
     * 进行推送  30s 执行一次
     * */
    public void putOrderSign(){
        log.info("【TransmissionTask.putOrderSign】info", new DateUtil().getNowDate2());
        //1.取数据
        List<SignItem> signItems = signItemService.selectAll();
        if (signItems.size() > 0) {
            signItems.stream().forEach(e->{
                LocalDateTime time = LocalDateTime.now();
                String synDate = df.format(time);
                //2.封装数据
                List<SignItemDTO> itemDTOList = new ArrayList<>();
                SignItemDTO signItemDTO = new SignItemDTO();
                signItemDTO.setSignTime(e.getSignTime());
                //signItemDTO.setSignTime(synDate);//测试数据
                signItemDTO.setOrderCode(e.getOrderCode());
                itemDTOList.add(signItemDTO);
                //3.推送数据
                Map<String, Object> mapData = new HashMap<>();
                mapData.put("orderList", itemDTOList);

                AliParam aliParam = null;
                try {
                    aliParam = new AliParamUtil().pushTransmission(mapData, synDate, secret, customerId,
                            OrderSignMethod, app_key);
                } catch (IOException ex) {
                    log.info("【IOException】ex=:{}", ex);
                }
                log.info("【TransmissionTask.putOrderSign】aliParam=:{}", aliParam);
                ResultEntity resultEntity = new ResultEntity();
                String resultStr = null;
                String url = null;
                try {
                    url = URL + "?app_key=" + app_key + "&method=" + OrderSignMethod +
                            "&v=2.0&__AppLinkCode=" + app_key + "&customerId=" + customerId + "&format=json&sign_method=md5" +
                            "&timestamp="+ URLEncoder.encode(synDate, "UTF-8") + "&sign=" + aliParam.getSign() +
                            "&__http_entry=1";
                } catch (UnsupportedEncodingException ex) {
                    log.info("【UnsupportedEncodingException】ex=:{}",ex);
                }
                resultStr = new RequestUtil().doPost(url, mapData);
                log.info("【TransmissionTask.executeTransmission】resultStr=:{}", resultStr);

                resultEntity = JSON.parseObject(resultStr, ResultEntity.class);
                log.info("【【TransmissionTask.executeTransmission】】===resultEntity=:{}", resultEntity);
                //4.更新推送状态
                if ("true".equals(resultEntity.getSuccess())) {
                    String data = resultEntity.getData() .replace("[","")
                            .replace("]","");
                    //List<ResultData> dataList = new ArrayList<>();
                    ResultData resultData = JSON.parseObject(data, ResultData.class);
                    if ("true".equals(resultData.getSuccess())) {
                           signItemService.updateStatus(resultData.getOrderCode(),"2", resultData.getMessage());
                     }else {
                          signItemService.updateStatus(resultData.getOrderCode(),"4", resultData.getMessage());
                      }
                }
            });
        }

    }

}
