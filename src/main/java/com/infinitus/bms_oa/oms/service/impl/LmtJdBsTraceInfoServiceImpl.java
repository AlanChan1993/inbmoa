package com.infinitus.bms_oa.oms.service.impl;

import com.infinitus.bms_oa.oms.enums.StatusEnum;
import com.infinitus.bms_oa.oms.mapper.LmtJdBsTraceInfoMapper;
import com.infinitus.bms_oa.oms.pojo.IpassResultEntity;
import com.infinitus.bms_oa.oms.pojo.LmtJdBsTraceInfo;
import com.infinitus.bms_oa.oms.pojo.VO.LmtJdBsTraceInfoVO;
import com.infinitus.bms_oa.oms.pojo.converter.LmtJdBsTraceInfoVOConverter;
import com.infinitus.bms_oa.oms.service.LmtJdBsTraceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LmtJdBsTraceInfoServiceImpl implements LmtJdBsTraceInfoService {
    @Autowired
    private LmtJdBsTraceInfoMapper mapper;

    @Override
    public List<LmtJdBsTraceInfo> getTransmission(String doNo) {
        return mapper.getTransmission(doNo);
    }

    @Override
    public IpassResultEntity getLmtJdBsTraceInfoVO(String doNo) {
        IpassResultEntity resultEntity = new IpassResultEntity();
        try {
            List<LmtJdBsTraceInfo> infoList = mapper.getTransmission(doNo);
            List<LmtJdBsTraceInfoVO> infoVOList = LmtJdBsTraceInfoVOConverter.convertList(infoList);
            resultEntity.setState(StatusEnum.SUCCESS_LMT.getMsg());
            resultEntity.setCode(StatusEnum.SUCCESS_LMT.getCode());
            if (null == infoVOList) {
                resultEntity.setTotal(0);
            }else{
                resultEntity.setTotal(infoVOList.size());
            }
            resultEntity.setData(infoVOList);
        } catch (Exception e) {
            log.info("【LmtJdBsTraceInfoServiceImpl.getLmtJdBsTraceInfoVO】接口报错");
        }
        return resultEntity;
    }

}
