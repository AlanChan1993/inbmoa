package com.infinitus.bms_oa.oms.service.impl;

import com.infinitus.bms_oa.oms.mapper.OMSBMSReturnOrderInfoMapper;
import com.infinitus.bms_oa.oms.pojo.OMSBMSReturnOrderInfo;
import com.infinitus.bms_oa.oms.service.OMSBMSReturnOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OMSBMSReturnOrderInfoServiceImpl implements OMSBMSReturnOrderInfoService {
    @Autowired
    private OMSBMSReturnOrderInfoMapper mapper;
    @Override
    public boolean createOMSBMSReturnOrderInfoList(List<OMSBMSReturnOrderInfo> list) {
        return mapper.createOMSBMSReturnOrderInfoList(list);
    }

}
