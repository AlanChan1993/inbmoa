package com.infinitus.bms_oa.rfid.mapper;

import com.infinitus.bms_oa.rfid.pojo.RFIDInfo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

public interface RFIDInfoMapper {

    boolean createRFIDInfo(RFIDInfo rfidInfo);

    RFIDInfo getRFIDInfoBytid(String tid);

    boolean insertRFIDInfos(List<RFIDInfo> list);

}
