package com.infinitus.bms_oa.ipass.controller;

import com.infinitus.bms_oa.ipass.utils.IpaasUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("getIpaasData")
@Slf4j
public class IpaasDataController {
    @Value("${SomeSecret.IpassController.ipaas_url_dev}")
    private String ipaas_url_dev;

    @Value("${SomeSecret.IpassController.ak}")
    private String ak;

    @Value("${SomeSecret.IpassController.sk}")
    private String sk;

    @Value("${SomeSecret.IpassController.appkey}")
    private String appkey;

    @Value("${IPASS_Sku_Relation.ipaas_baseUrl}")
    private String ipaas_baseUrl;

    @Value("${IPASS_Sku_Relation.ak}")
    private String ak_re;

    @Value("${IPASS_Sku_Relation.sk}")
    private String sk_re;

    @Value("${IPASS_Sku_Relation.appkey}")
    private String appkey_re;

    @Value("${IPASS_Sku_Relation.relationURL}")
    private String relationURL;

    /***
     *
     * 获取运单收货满意度
     */
    @ResponseBody
    @RequestMapping("getSatisfaction")
    public String getSatisfaction(HttpServletRequest request){
        String getUrl="/openapi/gbss-po/po/receiveSatisfaction";
        Object o = IpaasUtils.getRequest(ak, sk, appkey, ipaas_url_dev, getUrl);
        log.info("【IpaasDataController.getSatisfaction】=========o:{}", o);
        return "success";
    }

    /**
     * 提供运单相关信息
     * */
    @ResponseBody
    @RequestMapping("getMaster")
    public String getMaster(HttpServletRequest request){
        String getUrl="/openapi/gbss-po/po/receiveMaster";
        Object o = IpaasUtils.getRequest(ak, sk, appkey, ipaas_url_dev, getUrl);
        log.info("【IpaasDataController.getSatisfaction】=========o:{}", o);
        return "success";
    }


    /**
     * 物料主数据的对应关系
    * */
    @ResponseBody
    @RequestMapping("getSkuRelation")
    public String getSkuRelation(HttpServletRequest request) throws NoSuchAlgorithmException {
        Object o = IpaasUtils.postRequest(ak_re, sk_re, appkey_re, ipaas_baseUrl, relationURL,"");
        log.info("【IpaasDataController.getSkuRelation】=========o:{}", o);
        return "success";
    }

    /**
     * 测试轨迹优化ipass接口
     * ak，sk未授权
     * */
    @ResponseBody
    @RequestMapping("getIpassLmtTrace")
    public Object gettraceInfo(String id){
        Object o = IpaasUtils.getRequest(ak, sk, appkey, ipaas_url_dev, "/openapi/RFIDInfo/TransmissionController" +
                "/getLmtJdBsTraceInfo?doNo=" + id);
        return o;
    }


}
