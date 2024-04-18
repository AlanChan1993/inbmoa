package com.infinitus.bms_oa.oms.service;

import com.infinitus.bms_oa.oms.pojo.IpassResultEntity;
import com.infinitus.bms_oa.oms.pojo.LmtJdBsTraceInfo;

import java.util.List;

public interface LmtJdBsTraceInfoService {
    List<LmtJdBsTraceInfo> getTransmission(String doNo);

    IpassResultEntity getLmtJdBsTraceInfoVO(String doNo);


}
