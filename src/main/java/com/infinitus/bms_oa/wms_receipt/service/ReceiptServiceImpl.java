package com.infinitus.bms_oa.wms_receipt.service;

import com.infinitus.bms_oa.wms_receipt.mapper.ReceiptMapper;
import com.infinitus.bms_oa.wms_receipt.pojo.Receipt;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;
import com.infinitus.bms_oa.wms_receipt.pojo.convert.ReceiptDeatailToDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.convert.ReceiptToRecepiptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService{
    @Autowired
    private ReceiptMapper mapper;

    @Override
    public List<ReceiptVO> getReceipt() {
        List<ReceiptVO> list = new ArrayList<>();
        List<Receipt> receiptList = mapper.getReceipt();
        if (receiptList.size() > 0) {
            return new ReceiptToRecepiptVO().convertList(receiptList);
        }
        return null;
    }

    @Override
    public List<ReceiptDetailVO> getReceiptDetailVOList(String key) {
        if (null != key || !"".equals(key)) {
            List<ReceiptDetailVO> detailVOS = new ArrayList<>();
            List<Receipt> receipts = mapper.getReceiptDetailVOList(key);
            if (receipts.size() > 0) {
                return new ReceiptDeatailToDetailVO().convert(receipts);
            }
        }
        return null;
    }
}
