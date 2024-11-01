package com.infinitus.bms_oa.wms_rfid.controller;

import com.infinitus.bms_oa.enums.ResultEnums;
import com.infinitus.bms_oa.wms_rfid.pojo.DTO.Wms2hd100_OrdersDTO;
import com.infinitus.bms_oa.wms_rfid.pojo.RFIDInfo;
import com.infinitus.bms_oa.wms_rfid.pojo.ResultEntity;
import com.infinitus.bms_oa.wms_rfid.pojo.VO.Wms2hd100_OrdersVO;
import com.infinitus.bms_oa.wms_rfid.pojo.Wms2hd100_Orders;
import com.infinitus.bms_oa.wms_rfid.pojo.result.Result_2hd100_Orders;
import com.infinitus.bms_oa.wms_rfid.service.RFIDInfoService;
import com.infinitus.bms_oa.wms_rfid.service.Wms2hd100_OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("RFIDInfo")
@Slf4j
public class RFIDInfoController {

    @Autowired
    private RFIDInfoService service;

    @Autowired
    private Wms2hd100_OrdersService ordersService;

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


    /**
     * RFID获取WMS当天应扫订单汇总
     * */
    @RequestMapping("get2HD100Orders")
    public Result_2hd100_Orders getWms2HD100Orders(@RequestBody Wms2hd100_OrdersDTO ordersDTO){
        log.info("【RFIDInfoController.getWms2HD100Orders】ordersDTO=:{}", ordersDTO);
        Result_2hd100_Orders result = new Result_2hd100_Orders();
        try {
            List<Wms2hd100_Orders> orders = ordersService.getWms2HD100Orders(ordersDTO);
            List<Wms2hd100_OrdersVO> voList = new ArrayList<>();
            if (orders.size() > 0) {
                orders.stream().forEach(e->{
                    Wms2hd100_OrdersVO ordersVO = new Wms2hd100_OrdersVO();
                    ordersVO.setOrderId(e.getOrderno());
                    ordersVO.setWAVEKEY(e.getWavekey());
                    ordersVO.setTaskTime(e.getAddDate());
                    voList.add(ordersVO);
                });
            }
            result.setCode(ResultEnums.SUCCESS.getCode());
            result.setMessage("success");
            result.setResult(voList);
        } catch (Exception e) {
            result.setCode(ResultEnums.FAIL.getCode());
            result.setMessage(ResultEnums.FAIL.getMsg());
        }
        return result;
    }
}
