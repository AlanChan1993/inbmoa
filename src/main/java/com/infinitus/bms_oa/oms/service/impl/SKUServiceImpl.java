package com.infinitus.bms_oa.oms.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infinitus.bms_oa.oms.mapper.SKUMapper;
import com.infinitus.bms_oa.oms.pojo.DTO.SkuDTO;
import com.infinitus.bms_oa.oms.pojo.SKU;
import com.infinitus.bms_oa.oms.service.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SKUServiceImpl implements SKUService {
    @Autowired
    private SKUMapper mapper;

    @Override
    public List<SKU> getSku(SkuDTO dto) {
        List<SKU> list=mapper.getSku(dto.getSku());
        return  list;

    }
}
