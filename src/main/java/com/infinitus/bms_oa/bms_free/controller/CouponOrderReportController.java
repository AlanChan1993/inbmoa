package com.infinitus.bms_oa.bms_free.controller;

import com.infinitus.bms_oa.bms_free.empty.CouponOrderReport;
import com.infinitus.bms_oa.bms_free.service.CouponOrderReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/CouponOrderReportController")
public class CouponOrderReportController {
    @Autowired
    private CouponOrderReportService service;

    @RequestMapping("/createC")
    @ResponseBody
    public boolean create(@RequestBody CouponOrderReport couponOrderReport) {
        return service.createCouponOrderReport(couponOrderReport);
    }

    @RequestMapping("/judgment")
    public Integer judgment(String couponNumber){
        Integer a = service.judgCouponOrderReport(couponNumber);
        log.info(" a={}", a);
        return a;
    }


    }
