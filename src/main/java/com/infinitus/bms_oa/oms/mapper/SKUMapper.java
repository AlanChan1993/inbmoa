package com.infinitus.bms_oa.oms.mapper;

import com.github.pagehelper.Page;
import com.infinitus.bms_oa.oms.pojo.SKU;


import java.util.List;

public interface SKUMapper {
    List<SKU> getSku( String sku);
    int getSkuSum(String sku);
}
