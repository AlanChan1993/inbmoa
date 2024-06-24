package com.infinitus.bms_oa.oms.service;

import com.infinitus.bms_oa.oms.pojo.WordTrack;
import com.infinitus.bms_oa.oms.utils.ResultEntityUtils;

import java.util.List;

public interface WordTrackService {

    ResultEntityUtils insertWordTrackS(List<WordTrack> wordTracks);

    ResultEntityUtils insertWordTrack(List<WordTrack> wordTracks);

}
