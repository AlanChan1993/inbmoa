package com.infinitus.bms_oa.oms.controller;

import com.infinitus.bms_oa.oms.pojo.SignItem;
import com.infinitus.bms_oa.oms.service.SignItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/OrderSignController")
public class OrderSignController {
    @Autowired
    private SignItemService service;

    @RequestMapping("getHeath")
    public String getHello() {
        return "Hello";
    }

    @ResponseBody
    @RequestMapping("getOrderSign")
    public List<SignItem> getOrderSignAll(){
        return service.selectAll();
    }

    @ResponseBody
    @RequestMapping("updateStatus")
    public boolean updateStatus(String status,String id,String message) {
        return service.updateStatus(id, status, message);
    }
}
