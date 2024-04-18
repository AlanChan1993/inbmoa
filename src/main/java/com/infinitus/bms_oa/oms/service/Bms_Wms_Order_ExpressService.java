package com.infinitus.bms_oa.oms.service;

import com.infinitus.bms_oa.oms.pojo.Bms_Wms_Order_Express;

import java.util.List;

public interface Bms_Wms_Order_ExpressService {
    List<Bms_Wms_Order_Express> getBms_Wms_Order_Express(String status);

    boolean updateSattus(String status,Integer id);

    boolean update_Order_Express(String status, Integer id, String message, String sysDate);

}
