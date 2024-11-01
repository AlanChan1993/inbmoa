package com.infinitus.bms_oa.oms.utils;

import com.infinitus.bms_oa.oms.pojo.VO.SKUVO;
import lombok.Data;

import java.util.List;

@Data
public class SKUResult {
    private String success;
    private String errCode;
    private String errDesc;
    private PageResult  data;
}
