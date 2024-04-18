package com.infinitus.bms_oa.bms_op.empty;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OP_ThirdBody_NopoItems {
    private String accountCode;//account_Code
    private String accountName;//account_Name
    private String comment;//comment
    private String costCenterCode;//costCenter_Code
    private String costCenterName;//costCenter_Name
    private String currencyCode;//currency_Code
    private String currencyName;//currency_Name
    private String internalOrderCode;//internalOrder_Code
    private String internalOrderName;//internalOrder_Name
    private String itemCode;//item_Code
    private String noBillApplyId;//no_Bill_Apply_Id
    private BigDecimal paymentAmount;//paym_amt
    private String tty;//account_Code+/accountName

    private String expenseCategoryCode;//exp_cate_code
    private String expenseCategoryName;//exp_cate_name
    private String flowInitiator;//flow_Initiator
    private String flowInitiatorName;//flow_InitiatorName
}
