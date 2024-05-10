package com.infinitus.bms_oa.wms_receipt.utils;

import com.infinitus.bms_oa.wms_receipt.pojo.ReceiptVO;
import lombok.Data;

import java.util.List;

@Data
public class ResultUtil {
    boolean code = false;
    Integer size = 0;
    Object data = null;
}
