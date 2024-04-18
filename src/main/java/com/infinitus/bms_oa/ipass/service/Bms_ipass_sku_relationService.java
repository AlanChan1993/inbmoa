package com.infinitus.bms_oa.ipass.service;

import com.infinitus.bms_oa.ipass.pojo.Bms_ipass_sku_relation;

public interface Bms_ipass_sku_relationService {

    boolean createBms_ipass_sku_relation(Bms_ipass_sku_relation bmsIpassSkuRelation);

    Bms_ipass_sku_relation getBms_ipass_sku_relationBySku(String productno);
}
