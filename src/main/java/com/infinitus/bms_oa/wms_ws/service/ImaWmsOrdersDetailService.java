package com.infinitus.bms_oa.wms_ws.service;

import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsOrdersDetail;

import java.util.List;

public interface ImaWmsOrdersDetailService {
    List<ImaWmsOrdersDetail> getImaWmsOrdersDetails(String itemNumber);
}
