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

    /*
    * 重置status状态进行重推数据
    * */
    @RequestMapping("updateItemByNum")
    @ResponseBody
    public ResponseEntity updateItemByNum(String number){
        return service.updateItemByNum(number);
    }

    /**
    * 根据发货单号查IMA单号
    * */
    @ResponseBody
    @RequestMapping("getOrderByNum")
    public ResponseEntity getOrderByNum(String number) {
        return service.getOrderByNum(number);
    }

}
