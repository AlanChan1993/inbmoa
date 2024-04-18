package com.infinitus.bms_oa.oms.service.impl;

import com.infinitus.bms_oa.oms.mapper.Bms_Wms_Order_ExpressMapper;
import com.infinitus.bms_oa.oms.pojo.Bms_Wms_Order_Express;
import com.infinitus.bms_oa.oms.service.Bms_Wms_Order_ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Bms_Wms_Order_ExpressServiceImpl implements Bms_Wms_Order_ExpressService {
    @Autowired
    private Bms_Wms_Order_ExpressMapper bmsWmsOrderExpressMapper;

    @Override
    public List<Bms_Wms_Order_Express> getBms_Wms_Order_Express(String status) {
        return bmsWmsOrderExpressMapper.getBms_Wms_Order_Express(status);
    }

    @Override
    public boolean updateSattus(String status,Integer id) {
        return bmsWmsOrderExpressMapper.updateSattus(status, id);
    }

    @Override
    public boolean update_Order_Express(String status, Integer id, String message, String sysDate) {
        return bmsWmsOrderExpressMapper.update_Order_Express(status, id, message, sysDate);
    }

}
