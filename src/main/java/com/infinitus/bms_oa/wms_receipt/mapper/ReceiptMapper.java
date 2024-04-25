package com.infinitus.bms_oa.wms_receipt.mapper;


import com.infinitus.bms_oa.wms_receipt.pojo.Receipt;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;

import java.util.List;

public interface ReceiptMapper {
    List<Receipt> getReceipt();
    List<Receipt> getReceiptDetailVOList(String key);

}
