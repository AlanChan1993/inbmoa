package com.infinitus.bms_oa.oms.pojo;

import lombok.Data;

@Data
public class WmsConvention {
    private long wmsConventionId;
    private String addressCode;
    private String province;
    private String city;
    private String district;
    private String storeWarehouseCode;
    private String storeWarehouse;
    private String homeWarehouseCode;
    private String homeWarehouse;
    private String gmtModified;
    private String isDeleted;

}
