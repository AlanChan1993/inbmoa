package com.infinitus.bms_oa.wms_ws.service;

import com.infinitus.bms_oa.wms_ws.mapper.ImaWmsOrdersDetailMapper;
import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsOrdersDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImaWmsOrdersDetailServiceImpl implements ImaWmsOrdersDetailService{

    @Autowired
    private ImaWmsOrdersDetailMapper mapper;

    @Override
    public List<ImaWmsOrdersDetail> getImaWmsOrdersDetails(String itemNumber) {
        return mapper.getImaWmsOrdersDetails(itemNumber);
    }

}
