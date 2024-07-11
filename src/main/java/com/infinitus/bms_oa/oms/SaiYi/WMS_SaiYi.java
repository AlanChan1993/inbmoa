package com.infinitus.bms_oa.oms.SaiYi;

import com.infinitus.bms_oa.oms.pojo.AliParam;
import com.infinitus.bms_oa.oms.utils.AliParamUtil;
import com.infinitus.bms_oa.oms.utils.RequestUtil;
import com.infinitus.bms_oa.oms.utils.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Controller
@RequestMapping("testW")
public class WMS_SaiYi {
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("postBody")
    @ResponseBody
    public Object post(@RequestBody WorkTrack workTrack) throws UnsupportedEncodingException {
        String url = "";
        String  method="";
        String  secret="";
        String  customerId="";
        String  app_key="";
        LocalDateTime time = LocalDateTime.now();
        String synDate = df.format(time);
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("orderList", workTrack);
        AliParam aliParam = null;
        try {
            aliParam = new AliParamUtil().pushTransmission(mapData, synDate, secret, customerId, method, app_key);
            log.info("【TransmissionTask.putOrderSign】aliParam=:{}", aliParam);
        } catch (IOException ex) {
            log.info("【IOException】ex=:{}", ex);
        }

        String urlZ= url + "?app_key=" + app_key + "&method=" + method +
                "&v=2.0&__AppLinkCode=" + app_key + "&customerId=" + customerId + "&format=json&sign_method=md5" +
                "&timestamp="+ URLEncoder.encode(synDate, "UTF-8") + "&sign=" + aliParam.getSign() +
                "&__http_entry=1";

        return new RequestUtil().doPost(urlZ, mapData);
    }

}
