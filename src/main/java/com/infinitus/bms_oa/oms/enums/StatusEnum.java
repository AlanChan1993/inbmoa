package com.infinitus.bms_oa.oms.enums;

import com.infinitus.bms_oa.enums.CodeEnum;

public enum StatusEnum implements CodeEnum {
    NOSYN(0,"未同步"),
    NoShipmentInformation(1,"没有触发发货信息"),
    SUCCESSFUL(2,"成功"),
    FAIL(4, "推送中台失败"),

    //轨迹优化接口
    SUCCESS_LMT(2,"success"),
    GETFAIL(6,"false"),
    GETNULL(7,"获取数据为空(null)"),

    ;
    private Integer code;
    private String msg;

    StatusEnum(Integer code, String msg) {
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
        return code.toString();
    }
}
