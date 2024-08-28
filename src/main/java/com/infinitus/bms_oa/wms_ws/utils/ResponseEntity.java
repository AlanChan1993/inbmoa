package com.infinitus.bms_oa.wms_ws.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//用于返回实体时，不返回值为null的属性
public class ResponseEntity {
    private Object data;
    private String code;
    private String message;
    private String success;
}
