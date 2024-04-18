package com.infinitus.bms_oa.wms_ws.service;

import com.infinitus.bms_oa.wms_ws.mapper.ImaWmsLogisticsOrdersMapper;
import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsLogisticsOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImaWmsLogisticsOrdersServiceImpl implements ImaWmsLogisticsOrdersService{
    @Autowired
    private ImaWmsLogisticsOrdersMapper mapper;
    @Override
    public List<ImaWmsLogisticsOrders> getImaWmsLogisticsOrdersList() {
        return mapper.getImaWmsLogisticsOrdersList();
    }

    @Override
    public boolean updateStatus(String status,Integer id) {
        return mapper.updateStatus(status, id);
    }
}
