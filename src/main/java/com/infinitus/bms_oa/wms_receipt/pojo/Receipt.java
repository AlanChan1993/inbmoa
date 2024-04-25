package com.infinitus.bms_oa.wms_receipt.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Receipt {
    private String sku;
    private String descr;
    private String externreceiptkey;
    private Date adddate;
}
