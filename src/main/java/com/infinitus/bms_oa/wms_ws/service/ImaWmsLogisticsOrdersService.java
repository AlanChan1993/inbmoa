package com.infinitus.bms_oa.wms_ws.service;

import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsLogisticsOrders;

import java.util.List;

public interface ImaWmsLogisticsOrdersService {
    List<ImaWmsLogisticsOrders> getImaWmsLogisticsOrdersList();

    boolean updateStatus(String status, Integer id);
}
