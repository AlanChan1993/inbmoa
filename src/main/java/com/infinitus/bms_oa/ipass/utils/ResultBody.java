package com.infinitus.bms_oa.ipass.utils;

import com.infinitus.bms_oa.ipass.pojo.Bms_ipass_sku_relation;
import lombok.Data;

import java.util.List;

@Data
public class ResultBody {
    private ResultEx_return EX_RETURN;
    private List<Bms_ipass_sku_relation> ET_DATA;
}
