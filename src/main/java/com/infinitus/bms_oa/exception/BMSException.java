package com.infinitus.bms_oa.exception;

import com.infinitus.bms_oa.enums.ResultEnums;

public class BMSException extends RuntimeException {
    private Integer code;

    public BMSException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code=resultEnums.getCode();
    }

    public BMSException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
