package com.infinitus.bms_oa.wms_receipt.controller;

import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;
import com.infinitus.bms_oa.wms_receipt.service.ReceiptService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("ReceiptConteoller")
public class ReceiptController {
    @Autowired
    private ReceiptService service;

    @RequestMapping("getRecepitVOList")
    @ResponseBody
    public List<ReceiptVO> get() {
        return service.getReceipt();
    }

    @ResponseBody
    @RequestMapping("getSkuList")
    public List<ReceiptDetailVO> getSkuList(@Param("externreceiptkey") String key){
        return service.getReceiptDetailVOList(key);
    }

}
