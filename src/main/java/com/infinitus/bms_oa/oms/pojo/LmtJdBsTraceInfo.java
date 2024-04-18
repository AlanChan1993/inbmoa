package com.infinitus.bms_oa.oms.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class LmtJdBsTraceInfo {
    private String DO_NO;
    private String OPE_TITLE;
    private Date OPE_TIME;
    private String OPE_REMARK;
    private String OPE_NAME;
    private String WAYBILL_CODE;
    private String STATUS;
}
