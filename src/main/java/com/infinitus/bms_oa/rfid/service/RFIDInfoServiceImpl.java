package com.infinitus.bms_oa.rfid.service;

import com.infinitus.bms_oa.rfid.mapper.RFIDInfoMapper;
import com.infinitus.bms_oa.rfid.pojo.RFIDInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RFIDInfoServiceImpl implements RFIDInfoService {
    @Autowired
    private RFIDInfoMapper mapper;

    @Override
    public boolean createRFIDInfo(RFIDInfo rfidInfo) {
        return mapper.createRFIDInfo(rfidInfo
        );
    }

    @Override
    public RFIDInfo getRFIDInfoBytid(String tid) {
        return mapper.getRFIDInfoBytid(tid);
    }

    @Override
    public boolean insertRFIDInfos(List<RFIDInfo> rfidInfos) {
        return mapper.insertRFIDInfos(rfidInfos);
    }

}
