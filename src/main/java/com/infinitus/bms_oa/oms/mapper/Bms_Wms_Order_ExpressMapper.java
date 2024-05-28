package com.infinitus.bms_oa.oms.mapper;

import com.infinitus.bms_oa.oms.pojo.Bms_Wms_Order_Express;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface Bms_Wms_Order_ExpressMapper {
    List<Bms_Wms_Order_Express> getBms_Wms_Order_Express(String status);

    boolean updateSattus(String status,Integer id);

    boolean update_Order_Express(String status, Integer id, String message, String sysDate);

    boolean insertExpress(String expressCode, String shipmentNo, String expressCompanyCode);
}
