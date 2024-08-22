package com.infinitus.bms_oa.oms.mapper;

import com.infinitus.bms_oa.oms.pojo.LmtJdBsTraceInfo;
import com.infinitus.bms_oa.oms.pojo.WordTrack;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
@MapperScan
public interface LmtJdBsTraceInfoMapper {
    List<LmtJdBsTraceInfo> getTransmission(String doNo);

    boolean insertLMTJdBsTraceInfo(WordTrack wordTrack);

    WordTrack selectLmtJdBsTraceInfoBySomeThing(WordTrack wordTrack);

    WordTrack selectLmtJdBsTraceInfoBySome(String doNo,String  opeRemark,String opeTime);

    boolean updateLMTJdBsTraceInfoStatus(String t_id,String opeTitle,String status);
}
