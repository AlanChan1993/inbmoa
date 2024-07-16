package com.infinitus.bms_oa.oms.service.impl;

import com.infinitus.bms_oa.oms.enums.StatusEnum;
import com.infinitus.bms_oa.oms.excetion.BMSException;
import com.infinitus.bms_oa.oms.mapper.WordTrackMapper;
import com.infinitus.bms_oa.oms.pojo.WordTrack;
import com.infinitus.bms_oa.oms.service.WordTrackService;
import com.infinitus.bms_oa.oms.utils.ResultEntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class WordTrackServiceImpl implements WordTrackService {
    @Autowired
    private WordTrackMapper mapper;

    @Override
    public ResultEntityUtils insertWordTrackS(List<WordTrack> wordTracks) {
        ResultEntityUtils resultEntityUtils = new ResultEntityUtils();
        boolean a = mapper.insertWordTrackS(wordTracks);

        if (a) {
            log.info("【WordTrackServiceImpl.insertWordTrackS数据插入成功】wordTracks=:{}",wordTracks);
            resultEntityUtils.setCode(StatusEnum.SUCCESS_ALL.getCode());
            resultEntityUtils.setDesc("插入数量：" + wordTracks.size());
        }else {
            resultEntityUtils.setSuccess(a);
            resultEntityUtils.setCode(StatusEnum.IMPORT_FAIL.getCode());
            resultEntityUtils.setDesc(StatusEnum.IMPORT_FAIL.getMsg());
        }
        return resultEntityUtils;
    }

    @Override
    public ResultEntityUtils insertWordTrack(List<WordTrack> wordTracks) {
        if (wordTracks.size() < 1) {
            throw new BMSException(StatusEnum.PARM_NULL.getMsg());
        }

        ResultEntityUtils resultEntityUtils = new ResultEntityUtils();
        boolean a = true;
        List<WordTrack> wordTrackList = new ArrayList<>();
        Set<WordTrack> trackSet = new HashSet<>(wordTracks);

        for (WordTrack wordTrack : trackSet) {
            WordTrack wordTrackF = mapper.selectWordTrack(wordTrack);
            if (ObjectUtils.isEmpty(wordTrackF)) {
                a=mapper.insertWordTrack(wordTrack);
                wordTrackList.add(wordTrack);
            }
        }
        //log.info("【WordTrackServiceImpl.insertWordTrackS数据插入成功】wordTracks=:{}", wordTrackList);
        resultEntityUtils.setCode(StatusEnum.SUCCESS_ALL.getCode());
        resultEntityUtils.setDesc("插入数量：" + wordTrackList.size());
        return resultEntityUtils;
    }

    @Override
    public ResultEntityUtils updateWTrack(List<WordTrack> wordTracks) {
        if (wordTracks.size() < 1) {
            throw new BMSException(StatusEnum.PARM_NULL.getMsg());
        }
        ResultEntityUtils resultEntityUtils = new ResultEntityUtils();
        boolean a = true;
        List<WordTrack> wordTrackInsert = new ArrayList<>();
        List<WordTrack> wordTrackUpdate = new ArrayList<>();
        Set<WordTrack> trackSet = new HashSet<>(wordTracks);
        for (WordTrack wordTrack : trackSet) {
            WordTrack wordTrackF = mapper.selectWordTrackBySome(wordTrack.getDoNo(), wordTrack.getOpeRemark(), wordTrack.getOpeTime());
            if (ObjectUtils.isEmpty(wordTrackF)) {
                a=mapper.insertWordTrack(wordTrack);
                wordTrackInsert.add(wordTrack);
            }else{
                a = mapper.updateWTrack(wordTrackF.getT_id(), wordTrack.getOpeTitle(), wordTrack.getStatus());
                wordTrackUpdate.add(wordTrack);
            }
        }
        resultEntityUtils.setCode(StatusEnum.SUCCESS_ALL.getCode());
        resultEntityUtils.setDesc("更新数量：" + wordTrackUpdate.size() + ";新增数量：" + wordTrackInsert.size());
        return resultEntityUtils;
    }

}
