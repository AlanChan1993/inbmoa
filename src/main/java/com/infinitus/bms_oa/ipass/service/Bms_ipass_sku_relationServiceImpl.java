package com.infinitus.bms_oa.ipass.service;

import com.infinitus.bms_oa.ipass.mapper.Bms_ipass_sku_relationMapper;
import com.infinitus.bms_oa.ipass.pojo.Bms_ipass_sku_relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bms_ipass_sku_relationServiceImpl implements Bms_ipass_sku_relationService {
    @Autowired
    private Bms_ipass_sku_relationMapper mapper;

    @Override
    public boolean createBms_ipass_sku_relation(Bms_ipass_sku_relation bmsIpassSkuRelation) {
        return mapper.createBms_ipass_sku_relation(bmsIpassSkuRelation);
    }

    @Override
    public Bms_ipass_sku_relation getBms_ipass_sku_relationBySku(String productno) {
        return mapper.getBms_ipass_sku_relationBySku(productno);
    }

}
