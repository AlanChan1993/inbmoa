package com.infinitus.bms_oa.oms.pojo;

import lombok.Data;

@Data
public class IpassResultEntity {
    private String state="success";
    private Integer code=2;
    private Integer total;
    private Object data;

}
