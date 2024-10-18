package com.infinitus.bms_oa.wms_rfid.mapper;

import com.infinitus.bms_oa.wms_rfid.pojo.RFIDInfo;

import java.util.List;

public interface RFIDInfoMapper {

    boolean createRFIDInfo(RFIDInfo rfidInfo);

    RFIDInfo getRFIDInfoBytid(String tid);

    boolean insertRFIDInfos(List<RFIDInfo> list);

}
