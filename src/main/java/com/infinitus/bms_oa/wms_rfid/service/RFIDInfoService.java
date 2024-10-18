package com.infinitus.bms_oa.wms_rfid.service;

import com.infinitus.bms_oa.wms_rfid.pojo.RFIDInfo;

import java.util.List;

public interface RFIDInfoService {
    boolean createRFIDInfo(RFIDInfo rfidInfo);

    RFIDInfo getRFIDInfoBytid(String tid);

    boolean insertRFIDInfos(List<RFIDInfo> rfidInfos);

}
