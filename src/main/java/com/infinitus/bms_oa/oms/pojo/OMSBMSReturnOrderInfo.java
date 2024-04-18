package com.infinitus.bms_oa.oms.pojo;

import lombok.Data;

@Data
public class OMSBMSReturnOrderInfo {
    private String returnOrderCode;
    private String waybillCode;
    private String pOrderCode;
    private String updateTime;
    private Integer isDelete;

}
