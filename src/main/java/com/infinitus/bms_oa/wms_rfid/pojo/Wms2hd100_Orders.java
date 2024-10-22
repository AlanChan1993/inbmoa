package com.infinitus.bms_oa.wms_rfid.pojo;

import lombok.Data;

@Data
public class Wms2hd100_Orders {
    private String wavekey;
    private String orderno;
    private String status;
    private Integer totalaMount;
    private String dId;
    private String dName;
    private String addr;
    private String rdcId;
    private String OPSTATUS;
    private String addDate;
    private Integer serialKey;
    private String shipFlag;
    private String orderType;
    private String shipTiem;
    private String UUID;
}
