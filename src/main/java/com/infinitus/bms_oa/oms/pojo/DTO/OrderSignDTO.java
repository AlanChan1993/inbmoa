package com.infinitus.bms_oa.oms.pojo.DTO;

import com.infinitus.bms_oa.oms.pojo.SignItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderSignDTO {
    private List<SignItem> orderList;
}
