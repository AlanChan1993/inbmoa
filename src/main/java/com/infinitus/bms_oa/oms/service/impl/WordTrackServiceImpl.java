package com.infinitus.bms_oa.oms.service.impl;

import com.infinitus.bms_oa.oms.enums.StatusEnum;
import com.infinitus.bms_oa.oms.mapper.WordTrackMapper;
import com.infinitus.bms_oa.oms.pojo.WordTrack;
import com.infinitus.bms_oa.oms.service.WordTrackService;
import com.infinitus.bms_oa.oms.utils.ResultEntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
