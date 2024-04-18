package com.infinitus.bms_oa.oms.mapper;

import com.infinitus.bms_oa.oms.pojo.OMSBMSReturnOrderInfo;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface OMSBMSReturnOrderInfoMapper {
    boolean createOMSBMSReturnOrderInfoList(List<OMSBMSReturnOrderInfo> list);

}
