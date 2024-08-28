package com.infinitus.bms_oa.enums;

import lombok.Getter;

@Getter
public enum ResultEnums implements CodeEnum{
    SUCCESS(200, "成功"),
    FAIL(500,"失败"),
    ZERO(0,"无数据需要同步"),

    PARM_NULL(500,"传入参数不能为空"),
    SUM_NULL(200,"没找到相关数据，如需其他操作请联系系统管理员"),
    UPDATE_SUCCESS(200,"更新成功")
    ;


    private Integer code;
    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public String getCodeString(){
        return code+"";
    }
}
