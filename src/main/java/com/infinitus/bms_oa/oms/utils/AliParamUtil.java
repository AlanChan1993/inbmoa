package com.infinitus.bms_oa.oms.utils;

import com.alibaba.fastjson.JSON;
import com.infinitus.bms_oa.oms.pojo.AliParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AliParamUtil {
    public AliParam pushTransmission(Map<String, Object> mapData, String synDate, String secret, String customerId,
                                     String method,String app_key) throws IOException {
        AliParam aliParam = new AliParam();
        aliParam.setSecret(secret);

        Map<String, String> headers = new HashMap<>();
        headers.put("app_key", app_key);
        headers.put("method", method);
        headers.put("v", "2.0");
        headers.put("customerId", customerId);
        headers.put("format", "json");
        headers.put("sign_method", "md5");
        headers.put("timestamp", synDate);

        String sign = QimenSignUtils.signTopRequest(headers, JSON.toJSONString(mapData), secret, "md5");
        aliParam.setSign(sign);
        return aliParam;
    }
}
