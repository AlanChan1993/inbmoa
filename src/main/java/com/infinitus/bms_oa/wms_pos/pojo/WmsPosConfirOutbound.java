package com.infinitus.bms_oa.wms_pos.pojo;

import lombok.Data;

@Data
public class WmsPosConfirOutbound {
    String dealDate;
    String troCode;
    String sendCode;
    String inventory;
    String sendType;
    String member;
    String orderNote;
    String contacts;
    String mobile;
    String adress;
    String commodityNumber;
    String commodityName;
    String quantity;
    String batch;
    String dueDate;
}
