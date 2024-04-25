package com.infinitus.bms_oa.wms_receipt.pojo;

import lombok.Data;

/**
 *
 *调用存储过程主題
 */
@Data
public class SpReceiveCommit {
    private String externreceiptkey;
    private String sku;
    private String lot3;
    private String qty;
    private String sap_area_code;
    private String loc;
    private String flag;
    private String v_msgout;
}
