package com.infinitus.bms_oa.oms.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultEntityUtils {
    private boolean success = true;
    private Integer code;
    private String desc;
    private String data;
}
