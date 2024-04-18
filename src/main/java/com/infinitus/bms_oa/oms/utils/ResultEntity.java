package com.infinitus.bms_oa.oms.utils;

import lombok.Data;

@Data
public class ResultEntity {
    private String success;
    private String errCode;
    private String errDesc;
    private String  data;
}
