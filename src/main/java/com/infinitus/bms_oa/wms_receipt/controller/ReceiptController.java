package com.infinitus.bms_oa.wms_receipt.controller;

import com.infinitus.bms_oa.oms.excetion.BMSException;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;
import com.infinitus.bms_oa.wms_receipt.pojo.SpReceiveCommit;
import com.infinitus.bms_oa.wms_receipt.service.ReceiptService;
import com.infinitus.bms_oa.wms_receipt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public ResultUtil getRecepit() {
        ResultUtil resultUtil = new ResultUtil();
        List<ReceiptVO> list = new ArrayList<>();
        boolean a = true;
        try {
            list = service.getReceipt();
            if (list.size() > 0) {
                resultUtil.setSize(list.size());
                resultUtil.setCode(a);
                resultUtil.setData(list);
            }

        } catch (BMSException ex) {
            log.info("【ReceiptController.getRecepit失败】ex=:{}", ex);
        }
        return resultUtil;
    }

    /**
     * 订单物料查询
     * */
    @ResponseBody
    @RequestMapping("getSkuList")
    public ResultUtil getSkuList(@RequestParam("etkey") String key){
        List<ReceiptDetailVO> detailVOS = new ArrayList<>();
        ResultUtil resultUtil = new ResultUtil();
        try {
            detailVOS = service.getReceiptDetailVOList(key);
            if (detailVOS.size() > 0) {
                resultUtil.setSize(detailVOS.size());
                resultUtil.setCode(true);
                resultUtil.setData(detailVOS);
            }
        } catch (BMSException e) {
            log.info("【ReceiptController.getSkuList失败】ex=:{}", e);
        }
        return resultUtil;
    }

    /**
     * 收货过账 调用存储过程
     * */
    @ResponseBody
    @RequestMapping("spReceive")
    public boolean spReceive(SpReceiveCommit spReceiveCommit){
        boolean a = false;
        try {
            a = service.spReceive(spReceiveCommit);
        } catch (BMSException ex) {
            log.info("【ReceiptController.spReceive失败】ex=:{}", ex);
        }
        return a;
    }

}
