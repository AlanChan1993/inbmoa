package com.infinitus.bms_oa.oms.service.impl;

import com.infinitus.bms_oa.oms.mapper.WmsConventionMapper;
import com.infinitus.bms_oa.oms.pojo.WmsConvention;
import com.infinitus.bms_oa.oms.service.WmsConventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WmsConventionServiceImpl implements WmsConventionService {

    @Autowired
    private WmsConventionMapper mapper;

    @Override
    public boolean createWmsConvention(WmsConvention wmsConvention) {
        return mapper.createWmsConvention(wmsConvention);
    }

    @Override
    public boolean createWmsConventionList(List<WmsConvention> wmsConventionList) {
        return mapper.createWmsConventionList(wmsConventionList);
    }

}
