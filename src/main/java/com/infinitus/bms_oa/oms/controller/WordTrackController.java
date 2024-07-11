package com.infinitus.bms_oa.oms.controller;

import com.infinitus.bms_oa.oms.enums.StatusEnum;
import com.infinitus.bms_oa.oms.excetion.BMSException;
import com.infinitus.bms_oa.oms.pojo.WordTrack;
import com.infinitus.bms_oa.oms.service.WordTrackService;
import com.infinitus.bms_oa.oms.utils.ResultEntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/lmt/WordTrackController")
public class WordTrackController {

    @Autowired
    private WordTrackService service;

    @ResponseBody
    @RequestMapping("importTrack")
    public ResultEntityUtils createWordTrack(@RequestBody List<WordTrack> wordTracks) {
        log.info("【WordTrackController.createWordTrack】wordTracks=", wordTracks);
        if (wordTracks.size() < 1) {
            throw new BMSException(StatusEnum.PARM_NULL.getMsg());
        }
        return service.insertWordTrack(wordTracks);
    }

    @ResponseBody
    @RequestMapping("updateWTrackSign")
    public ResultEntityUtils  updateWTrack(@RequestBody List<WordTrack> wordTracks){
        if (wordTracks.size() < 1) {
            throw new BMSException(StatusEnum.PARM_NULL.getMsg());
        }
        return service.updateWTrack(wordTracks);

    }
}
