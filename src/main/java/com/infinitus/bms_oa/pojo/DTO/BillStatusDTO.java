package com.infinitus.bms_oa.pojo.DTO;

import lombok.Data;

@Data
public class BillStatusDTO {
    private String id;
    private String adj_no;
    private String approval_dt;
    private String code;
    private String create_id;
    private String oa_flowId;
}
