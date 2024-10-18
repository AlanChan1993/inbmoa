package com.infinitus.bms_oa.wms_rfid.pojo;

import lombok.Data;

@Data
public class RFIDInfo {
    String epc;//rfid电子产品代码（可变）
    String tid;//rfid标签识别号（不可变）
    String vendorCode;//生产供应商代码
    String sku;//产品代码
    String createDate;//生产日期
}