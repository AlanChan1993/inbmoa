package com.infinitus.bms_oa.oms.utils;

import com.infinitus.bms_oa.oms.pojo.DTO.ResultData;
import lombok.Data;

import java.util.List;

@Data
public class ResultOMS {
    private String success;
    private String errCode;
    private String errDesc;
    private List<ResultData> data;
}
