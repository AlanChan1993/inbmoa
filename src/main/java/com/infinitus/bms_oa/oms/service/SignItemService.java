package com.infinitus.bms_oa.oms.service;

import com.infinitus.bms_oa.oms.pojo.SignItem;

import java.util.List;

public interface SignItemService {
    List<SignItem> selectAll();

    boolean updateStatus(String orderCode,String status,String message);

}
