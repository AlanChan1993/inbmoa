package com.infinitus.bms_oa.wms_ws.utils;

import lombok.Data;

@Data
public class ResponseEntity {

    private Object data;
    private String code;
    private String message;
    private String success;
}
