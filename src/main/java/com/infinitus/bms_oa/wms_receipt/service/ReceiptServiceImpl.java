package com.infinitus.bms_oa.wms_receipt.service;

import com.infinitus.bms_oa.wms_receipt.mapper.ReceiptMapper;
import com.infinitus.bms_oa.wms_receipt.pojo.*;
import com.infinitus.bms_oa.wms_receipt.pojo.convert.ReceiptDeatailToDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.convert.ReceiptToRecepiptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public SpReceiptVO spReceive(SpReceiveCommit spReceiveCommit) {
        SpReceiptVO spReceiptVO = new SpReceiptVO();

        if (null != spReceiveCommit || !"".equals(spReceiveCommit)) {
            log.info("【ReceiptServiceImpl.spReceive】spReceiveCommit=:{}", spReceiveCommit);
            Map<String, String> map = new HashMap<>();
            map.put("externreceiptkey", spReceiveCommit.getExternreceiptkey());
            map.put("sku", spReceiveCommit.getSku());
            map.put("lot3",spReceiveCommit.getLot3());
            map.put("qty", spReceiveCommit.getQty());
            map.put("sap_area_code", spReceiveCommit.getSap_area_code());
            map.put("loc", spReceiveCommit.getLoc());
            mapper.spReceive(map);
            spReceiptVO.setFlag(map.get("flag"));
            spReceiptVO.setV_msgout(map.get("v_msgout"));
        }
        return spReceiptVO;
    }

    @Override
    public void spSkey_etkey(Map<String, String> map) {
         mapper.spSkey_etkey(map);
    }
}
