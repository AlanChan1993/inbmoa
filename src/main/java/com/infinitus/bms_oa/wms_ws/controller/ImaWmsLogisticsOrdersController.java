package com.infinitus.bms_oa.wms_ws.controller;

import com.infinitus.bms_oa.wms_ws.service.ImaWmsLogisticsOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/WmsWsController")
public class ImaWmsLogisticsOrdersController {
    @Autowired
    private ImaWmsLogisticsOrdersService service;

    @RequestMapping("getHeath")
    public String getHeath(){
        return "Hello Man";
    }

    @PostMapping("getIMAOrders")
    public Object putOrderList(){

        return "";
    }

}
