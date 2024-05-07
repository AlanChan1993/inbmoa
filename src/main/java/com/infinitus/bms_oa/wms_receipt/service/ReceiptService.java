package com.infinitus.bms_oa.wms_receipt.service;

import com.infinitus.bms_oa.wms_receipt.pojo.*;

import java.util.List;
import java.util.Map;

public interface ReceiptService {
    List<ReceiptVO> getReceipt();

    List<ReceiptDetailVO> getReceiptDetailVOList(String key);

    SpReceiptVO spReceive(SpReceiveCommit spReceiveCommit);

    void spSkey_etkey(Map<String, String> map);


}
