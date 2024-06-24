package com.infinitus.bms_oa.oms.mapper;

import com.infinitus.bms_oa.oms.pojo.WordTrack;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface WordTrackMapper {

    boolean insertWordTrackS(List<WordTrack> wordTracks);

    boolean insertWordTrack(WordTrack wordTrack);

    WordTrack selectWordTrack(String doNo, String express_code, String opeRemark, String opeTime, String opeTitle);

}
