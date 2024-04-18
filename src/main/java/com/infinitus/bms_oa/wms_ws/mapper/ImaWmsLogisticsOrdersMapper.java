package com.infinitus.bms_oa.wms_ws.mapper;

import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsLogisticsOrders;

import java.util.List;

public interface ImaWmsLogisticsOrdersMapper {
    List<ImaWmsLogisticsOrders> getImaWmsLogisticsOrdersList();

    boolean updateStatus(String status,Integer id);

}
