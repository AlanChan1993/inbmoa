package com.infinitus.bms_oa.wms_ws.pojo;

import lombok.Data;

@Data
public class ImaWmsLogisticsOrders {
    private Integer ORDER_ID;
    private String ITEM_NUMBER;
    private String ITEM_APPLYPERSON;
    private String ITEM_APPLYDEPT;
    private String ITEM_APPLYPERSON_NAME;
    private String ITEM_APPLYDATE;
    private String ITEM_ISCOMPENSATE;
    //private String ITEM_PRODUCTCODE;
    //private String ITEM_PRODUCTNAME;
    //private String ITEM_UNIT;
    //private String ITEM_MAINPRICE;
    //private String ITEM_QUANTITY;
    //private String ITEM_BATCH;
    private String ITEM_APPLICATIONNAME;
    private String ITEM_CONSIGNEEUSER;
    private String ITEM_CONSIGNEEUSER_NAME;
    private String ITEM_SAPNUMBER;
    private String ITEM_PROJECT;
    private String ITEM_COMPANYCF;
    private String ITEM_MOVETYPE;
    private String SYN_DATE;
    private String BWART;
    private String MJAHR;
    private String KOSTL;
}
