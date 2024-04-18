package com.infinitus.bms_oa.oms.mapper;

import com.infinitus.bms_oa.oms.pojo.LmtJdBsTraceInfo;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
@MapperScan
public interface LmtJdBsTraceInfoMapper {
    List<LmtJdBsTraceInfo> getTransmission(String doNo);
}
