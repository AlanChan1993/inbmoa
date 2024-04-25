package com.infinitus.bms_oa.wms_receipt.pojo.convert;

import com.infinitus.bms_oa.wms_receipt.pojo.Receipt;
import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptDetailVO;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptDeatailToDetailVO {
    public ReceiptDetailVO convert(Receipt r) {
        ReceiptDetailVO vo = new ReceiptDetailVO();
        vo.setDescr(r.getDescr());
        vo.setSku(r.getSku());
        return vo;
    }

    public List<ReceiptDetailVO> convert(List<Receipt> list) {
        return list.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
