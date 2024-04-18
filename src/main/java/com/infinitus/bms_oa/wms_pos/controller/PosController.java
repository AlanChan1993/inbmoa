package com.infinitus.bms_oa.wms_pos.controller;

import com.infinitus.bms_oa.wms_pos.pojo.WmsPosConfirOutbound;
import com.infinitus.bms_oa.wms_pos.service.WmsPosConfirOutboundService;
import com.infinitus.bms_oa.utils.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("PosController")
@Slf4j
public class PosController {
    @Autowired
    private WmsPosConfirOutboundService service;

    @ResponseBody
    @RequestMapping("pos2wms_confir_outbound")
    public ResultEntity createWmsPosConfirOutboundS(@RequestBody List<WmsPosConfirOutbound> infos) {
        log.info("【PosController.createWmsPosConfirOutboundS】infos=:{}", infos);
        ResultEntity rs = new ResultEntity();
        if (infos.size() > 0) {
            boolean a = service.createWmsPosConfirOutboundS(infos);
            if (a == true) {
                rs.setSize(infos.size());
                rs.setSuccess(true);
            } else {
                rs.setSize(0);
                rs.setSuccess(a);
            }
        }
        return rs;
    }

    @RequestMapping("getHeath")
    public String getHeath(){
        return "Hello";
    }
}
