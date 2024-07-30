package com.infinitus.bms_oa.wms_receipt.pojo.convert;

import com.infinitus.bms_oa.wms_receipt.pojo.Receipt;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptToRecepiptVO {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ReceiptVO convert(Receipt r) {
        ReceiptVO receiptVO = new ReceiptVO();
        receiptVO.setAdddate(simpleDateFormat.format(r.getAdddate()));
        receiptVO.setExternreceiptkey(r.getExternreceiptkey());
        return receiptVO;
    }

    public List<ReceiptVO> convert(List<Receipt> list) {
        return list.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

}
