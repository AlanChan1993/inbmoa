package com.infinitus.bms_oa.wms_ws.pojo;

import lombok.Data;

@Data
public class ImaWmsOrdersDetail {
    private String DETAIL_ID;
    private String ITEM_NUMBER;
    private String ITEM_SAPNUMBER;
    private String ITEM_PRODUCTCODE;
    private String ITEM_PRODUCTNAME;
    private String ITEM_UNIT;
    private String ITEM_MAINPRICE;
    private int ITEM_QUANTITY;
    private String ITEM_BATCH;
    private String ITEM_CLASS;
    private String STATUS;
    private String MJAHR;
}
