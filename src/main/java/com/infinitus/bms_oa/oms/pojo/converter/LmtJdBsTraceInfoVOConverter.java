package com.infinitus.bms_oa.oms.pojo.converter;

import com.infinitus.bms_oa.oms.excetion.BMSException;
import com.infinitus.bms_oa.oms.pojo.LmtJdBsTraceInfo;
import com.infinitus.bms_oa.oms.pojo.VO.LmtJdBsTraceInfoVO;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LmtJdBsTraceInfoVOConverter {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static LmtJdBsTraceInfoVO convert(LmtJdBsTraceInfo lmtInfo) throws Exception {
        if (lmtInfo == null) {
            log.info("【LmtJdBsTraceInfoVOConverter.convert】lmtInfo==null(空)");
            throw new BMSException();
        }
        LmtJdBsTraceInfoVO lmtJdBsTraceInfoVO = new LmtJdBsTraceInfoVO();
        lmtJdBsTraceInfoVO.setDoNo(lmtInfo.getDO_NO());
        lmtJdBsTraceInfoVO.setName(lmtInfo.getOPE_NAME());
        lmtJdBsTraceInfoVO.setRemark(lmtInfo.getOPE_REMARK());
        lmtJdBsTraceInfoVO.setStatus(lmtInfo.getSTATUS());
        lmtJdBsTraceInfoVO.setTime(dateFormat.format(lmtInfo.getOPE_TIME()));
        lmtJdBsTraceInfoVO.setTitle(lmtInfo.getOPE_TITLE());
        lmtJdBsTraceInfoVO.setWayBillCode(lmtInfo.getWAYBILL_CODE());
        return lmtJdBsTraceInfoVO;
    }

    public static List<LmtJdBsTraceInfoVO> convertList(List<LmtJdBsTraceInfo> lmtJdBsTraceInfos) {
        if (lmtJdBsTraceInfos == null) {
            log.info("【LmtJdBsTraceInfoVOConverter.convertList】lmtInfo==null(空)");
            throw new BMSException();
        }
        List<LmtJdBsTraceInfoVO> infoVOList = new ArrayList<>();
        for (LmtJdBsTraceInfo info:lmtJdBsTraceInfos) {
            LmtJdBsTraceInfoVO infoVO = new LmtJdBsTraceInfoVO();
            infoVO.setDoNo(info.getDO_NO());
            infoVO.setWayBillCode(info.getWAYBILL_CODE());
            infoVO.setTitle(info.getOPE_TITLE());
            infoVO.setStatus(info.getSTATUS());
            infoVO.setRemark(info.getOPE_REMARK());
            infoVO.setName(info.getOPE_NAME());
            infoVO.setTime(dateFormat.format(info.getOPE_TIME()));

            infoVOList.add(infoVO);
        }
        return infoVOList;
    }
}
