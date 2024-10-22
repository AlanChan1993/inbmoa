package com.infinitus.bms_oa.wms_rfid.mapper;

import com.infinitus.bms_oa.wms_rfid.pojo.DTO.Wms2hd100_OrdersDTO;
import com.infinitus.bms_oa.wms_rfid.pojo.Wms2hd100_Orders;

import java.util.List;

public interface Wms2hd100_OrdersMapper {
    List<Wms2hd100_Orders> getWms2HD100Orders(Wms2hd100_OrdersDTO ordersDTO);

}
