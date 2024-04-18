package com.infinitus.bms_oa.oms.service;

import com.infinitus.bms_oa.oms.pojo.WmsConvention;

import java.util.List;

public interface WmsConventionService {
    boolean createWmsConvention(WmsConvention wmsConvention);

    boolean createWmsConventionList(List<WmsConvention> wmsConventionList);

}
