package com.infinitus.bms_oa.wms_receipt.mapper;


import com.infinitus.bms_oa.wms_receipt.pojo.Receipt;
import com.infinitus.bms_oa.wms_receipt.pojo.SpReceiveCommit;

import java.util.List;
import java.util.Map;

public interface ReceiptMapper {
    List<Receipt> getReceipt();
    List<Receipt> getReceiptDetailVOList(String key);
    void spReceive(Map<String, String> map);

    void spSkey_etkey(Map<String, String> map);

}
