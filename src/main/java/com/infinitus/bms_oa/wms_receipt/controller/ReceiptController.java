package com.infinitus.bms_oa.wms_receipt.controller;

import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;
import com.infinitus.bms_oa.wms_receipt.pojo.SpReceiveCommit;
import com.infinitus.bms_oa.wms_receipt.service.ReceiptService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("ReceiptConteoller")
public class ReceiptController {
    @Autowired
    private ReceiptService service;

    /**
     * 收货单查询
     * */
    @RequestMapping("getRecepitVOList")
    @ResponseBody
    public List<ReceiptVO> get() {
        return service.getReceipt();
    }

    /**
     * 订单物料查询
     * */
    @ResponseBody
    @RequestMapping("getSkuList")
    public List<ReceiptDetailVO> getSkuList(@RequestParam("externreceiptkey") String key){
        return service.getReceiptDetailVOList(key);
    }

    /**
     * 收货过账 调用存储过程
     * */
    @ResponseBody
    @RequestMapping("spReceive")
    public String spReceive(SpReceiveCommit spReceiveCommit){
        String result="E";
        boolean a = service.spReceive(spReceiveCommit);
        if (a) {
            result = "S";
        }
        return result;
    }

}
