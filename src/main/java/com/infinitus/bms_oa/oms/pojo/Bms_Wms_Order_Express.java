package com.infinitus.bms_oa.oms.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Bms_Wms_Order_Express {
    private Integer ID;
    private String expressCode;
    private String shipmentNo;
    private String expressCompanyCode;
    private String status;
    private Date insert_date;
    private Date sys_date;
    private String message;
}
