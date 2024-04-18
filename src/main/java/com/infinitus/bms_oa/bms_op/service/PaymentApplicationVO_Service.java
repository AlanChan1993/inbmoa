package com.infinitus.bms_oa.bms_op.service;

import com.infinitus.bms_oa.bms_op.empty.OP_ScondBody_PaymentApplication;

import java.util.List;

public interface PaymentApplicationVO_Service {
    List<OP_ScondBody_PaymentApplication> getAllPaymentApplicationVO();

    String getOAflowID(String INVOICE_NO);

    boolean updateOpFlag(String opFlag,String Invoice_no);
}
