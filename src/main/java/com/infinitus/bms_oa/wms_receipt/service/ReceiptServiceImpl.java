package com.infinitus.bms_oa.wms_receipt.service;

import com.infinitus.bms_oa.wms_receipt.mapper.ReceiptMapper;
import com.infinitus.bms_oa.wms_receipt.pojo.Receipt;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;
import com.infinitus.bms_oa.wms_receipt.pojo.SpReceiveCommit;
import com.infinitus.bms_oa.wms_receipt.pojo.convert.ReceiptDeatailToDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.convert.ReceiptToRecepiptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReceiptServiceImpl implements ReceiptService{
    @Autowired
    private ReceiptMapper mapper;

    @Override
    public List<ReceiptVO> getReceipt() {
        List<ReceiptVO> list = new ArrayList<>();
        List<Receipt> receiptList = mapper.getReceipt();
        log.info("【ReceiptServiceImpl.getReceipt】receiptList=:{}", receiptList);
        if (receiptList.size() > 0) {
            return new ReceiptToRecepiptVO().convert(receiptList);
        }
        return null;
    }

    @Override
    public List<ReceiptDetailVO> getReceiptDetailVOList(String key) {
        if (null != key || !"".equals(key)) {
            log.info("【ReceiptServiceImpl.getReceiptDetailVOList】key=:{}", key);
            List<Receipt> receipts = mapper.getReceiptDetailVOList(key);
            if (receipts.size() > 0) {
                return new ReceiptDeatailToDetailVO().convert(receipts);
            }
        }
        return null;
    }

    @Override
    public boolean spReceive(SpReceiveCommit spReceiveCommit) {
        if (null != spReceiveCommit || !"".equals(spReceiveCommit)) {
            log.info("【ReceiptServiceImpl.spReceive】spReceiveCommit=:{}", spReceiveCommit);
            return mapper.spReceive(spReceiveCommit);
        }
        return false;
    }
}
