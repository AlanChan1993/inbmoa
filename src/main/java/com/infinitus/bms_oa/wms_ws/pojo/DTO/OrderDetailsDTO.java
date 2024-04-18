package com.infinitus.bms_oa.wms_ws.pojo.DTO;

import lombok.Data;

@Data
public class OrderDetailsDTO {
    private String prodCode;
    private int count;
    private String materialType;
    private String batchNo;
    private String project;
}
