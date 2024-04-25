package com.infinitus.bms_oa.wms_receipt.service;

import com.infinitus.bms_oa.wms_receipt.pojo.Receipt;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;

import java.util.List;

public interface ReceiptService {
    List<ReceiptVO> getReceipt();

    List<ReceiptDetailVO> getReceiptDetailVOList(String key);

}
