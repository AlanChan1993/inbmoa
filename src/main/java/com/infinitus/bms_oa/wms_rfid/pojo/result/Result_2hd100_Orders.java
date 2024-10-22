package com.infinitus.bms_oa.wms_rfid.pojo.result;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Result_2hd100_Orders<T> {
    private String message;
    private Integer code;
    private List<T> result;
}
