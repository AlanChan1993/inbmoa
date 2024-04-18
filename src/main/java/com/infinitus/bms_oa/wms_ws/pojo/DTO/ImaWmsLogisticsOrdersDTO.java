package com.infinitus.bms_oa.wms_ws.pojo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ImaWmsLogisticsOrdersDTO {
    private String applyNo;
    private String applyDateTime;
    private String applyDealerAccountName;
    private String applyDealerName;
    private String applyPurpose;
    private String receiverName;
    private String receiverAccountName;
    private String project;
    private String companyNo;
    private String moveType;
    private String costCenter;
    private String orderNo;
    private String compensation;
    private List<OrderDetailsDTO> details;

}
