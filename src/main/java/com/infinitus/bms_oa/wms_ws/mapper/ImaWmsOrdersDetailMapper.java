package com.infinitus.bms_oa.wms_ws.mapper;

import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsOrdersDetail;

import java.util.List;

public interface ImaWmsOrdersDetailMapper {
    List<ImaWmsOrdersDetail> getImaWmsOrdersDetails(String itemNumber);

}
