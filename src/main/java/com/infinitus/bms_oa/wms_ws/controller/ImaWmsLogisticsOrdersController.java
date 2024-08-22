package com.infinitus.bms_oa.wms_ws.controller;

import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsOrdersDetail;
import com.infinitus.bms_oa.wms_ws.service.ImaWmsLogisticsOrdersService;
import com.infinitus.bms_oa.wms_ws.service.ImaWmsOrdersDetailService;
import com.infinitus.bms_oa.wms_ws.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/WmsWsController")
public class ImaWmsLogisticsOrdersController {
    @Autowired
    private ImaWmsLogisticsOrdersService service;

    @Autowired
    private ImaWmsOrdersDetailService detailService;

    @RequestMapping("getHeath")
    public String getHeath() {
        return "Hello Man";
    }

    @PostMapping("getIMAOrders")
    public Object putOrderList() {
        return "";
    }

    @ResponseBody
    @RequestMapping("getSAPByStatus")
    public List<String> get() {
        return service.getSap2WmsDeptOutcomeList();
    }

    @ResponseBody
    @RequestMapping("getSAPByNum")
    public ResponseEntity getSAPByNum(String number) {
        List<ImaWmsOrdersDetail> detailList = detailService.getImaWmsOrdersDetails(number);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode("200");
        responseEntity.setData(detailList);
        return responseEntity;
    }

    @RequestMapping("updateItemByNum")
    public String updateItemByNum(String num){
        return "";
    }
}
