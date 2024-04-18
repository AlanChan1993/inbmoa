package com.infinitus.bms_oa.rfid.service;

import com.infinitus.bms_oa.rfid.pojo.RFIDInfo;

import java.util.List;

public interface RFIDInfoService {
    boolean createRFIDInfo(RFIDInfo rfidInfo);

    RFIDInfo getRFIDInfoBytid(String tid);

    boolean insertRFIDInfos(List<RFIDInfo> rfidInfos);

}
