package com.infinitus.bms_oa.wms_ws.service;

import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsLogisticsOrders;
import com.infinitus.bms_oa.wms_ws.utils.ResponseEntity;

import java.util.List;

public interface ImaWmsLogisticsOrdersService {
    List<ImaWmsLogisticsOrders> getImaWmsLogisticsOrdersList();

    List<String> getSap2WmsDeptOutcomeList();

    boolean updateStatus(String status, Integer id);

    boolean updateSap2WmsStatus(String status,String  rsnum);

    ResponseEntity updateItemByNum(String number);

    ResponseEntity getOrderByNum(String number);
}
