package com.infinitus.bms_oa.rfid.controller;

import com.infinitus.bms_oa.rfid.pojo.RFIDInfo;
import com.infinitus.bms_oa.rfid.pojo.ResultEntity;
import com.infinitus.bms_oa.rfid.service.RFIDInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("RFIDInfo")
@Slf4j
public class RFIDInfoController {

    @Autowired
    private RFIDInfoService service;

    @ResponseBody
    @RequestMapping("insertRFIDInfos")
    public ResultEntity insertRFIDInfos(@RequestBody List<RFIDInfo> infos) {
        log.info("【RFIDInfoController.insertRFIDInfo】infos=:{}", infos);
        ResultEntity rs = new ResultEntity();
        if (infos.size() > 0) {
            boolean a = service.insertRFIDInfos(infos);
            if (a == true) {
                rs.setSize(infos.size());
                rs.setSuccess(true);
            }else {
                rs.setSize(0);
                rs.setSuccess(a);
            }

        }
        return rs;
    }

    @RequestMapping("createRFIDInfo")
    public String createRFIDInfo(RFIDInfo rfidInfo) {
        boolean a = service.createRFIDInfo(rfidInfo);
        return "success";
    }

    @RequestMapping("getRFIDInfo")
    public RFIDInfo getRFIDInfo(String id){
        return service.getRFIDInfoBytid(id);
    }


}
