package com.infinitus.bms_oa.ipass.task;


import com.alibaba.fastjson.JSON;
import com.infinitus.bms_oa.ipass.pojo.Bms_ipass_sku_relation;
import com.infinitus.bms_oa.ipass.service.Bms_ipass_sku_relationService;
import com.infinitus.bms_oa.ipass.utils.IpaasUtils;
import com.infinitus.bms_oa.ipass.utils.ResultBody;
import com.infinitus.bms_oa.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
* 数据对接到wms，已做判重
 * 4-10已上生产
* */
@Slf4j
//@Component
public class WmsSkuRelateTask {
    @Value("${IPASS_Sku_Relation.ipaas_baseUrl}")
    private String ipaas_baseUrl;

    @Value("${IPASS_Sku_Relation.ak}")
    private String ak;

    @Value("${IPASS_Sku_Relation.sk}")
    private String sk;

    @Value("${IPASS_Sku_Relation.appkey}")
    private String appkey;

    @Value("${IPASS_Sku_Relation.relationURL}")
    private String relationURL;

    @Autowired
    private Bms_ipass_sku_relationService skuService;
    /**
     * 需求暂定五分钟推送一次
     * */
    @Scheduled(fixedRate = 1000 * 5 * 60)
    //@Scheduled(fixedRate = 1000 * 60 * 60)//测试使用
    public void excuseBySec() throws Exception {
        getSKURelation();
    }

    public void getSKURelation() throws NoSuchAlgorithmException {
        log.info("【SkuRelateTask.getSKURelation】取数据————-------------start-----", new DateUtil().getNowDate2());
        String o = IpaasUtils.postRequest(ak, sk, appkey, ipaas_baseUrl, relationURL, "");
        //log.info("【SkuRelateTask.getSKURelation】o===:{}", o);
        if (null != o) {
            ResultBody resultBodyJSON = JSON.parseObject(o, ResultBody.class);
            //log.info("【SkuRelateTask.getSKURelation】resultBodyJSON===:{}", resultBodyJSON);
            if ("S".equals(resultBodyJSON.getEX_RETURN().getTYPE()) && null != resultBodyJSON.getET_DATA()) {
                List<Bms_ipass_sku_relation> bmsIpassSkuRelations = resultBodyJSON.getET_DATA();
                //log.info("【SkuRelateTask.getSKURelation】bmsIpassSkuRelations===:{}", bmsIpassSkuRelations.size());
                bmsIpassSkuRelations.stream().forEach(e->{
                    Bms_ipass_sku_relation bmsIpassSkuRelation = skuService.getBms_ipass_sku_relationBySku(e.getProductno());
                    if (null == bmsIpassSkuRelation) {
                        boolean a = skuService.createBms_ipass_sku_relation(e);
                    }
                });
            }
        }
    }


}
