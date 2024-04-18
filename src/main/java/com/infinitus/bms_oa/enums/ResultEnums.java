package com.infinitus.bms_oa.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {
    SUCCESS(200, "成功"),
    FAIL(500,"失败"),
    ZERO(0,"无数据需要同步")
    ;

    private Integer code;
    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
