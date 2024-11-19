package com.infinitus.bms_oa.oms.service;

import com.github.pagehelper.PageInfo;
import com.infinitus.bms_oa.oms.pojo.DTO.SkuDTO;
import com.infinitus.bms_oa.oms.pojo.SKU;

import java.util.List;

public interface SKUService {
    List<SKU>  getSku(SkuDTO dto);
    int getSkuSum(String sku);
}
