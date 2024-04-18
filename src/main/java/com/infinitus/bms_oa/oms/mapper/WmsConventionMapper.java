package com.infinitus.bms_oa.oms.mapper;

import com.infinitus.bms_oa.oms.pojo.WmsConvention;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

//@Mapper
@MapperScan
public interface WmsConventionMapper {
    boolean createWmsConvention(WmsConvention wmsConvention);
    boolean createWmsConventionList(List<WmsConvention> wmsConventionList);


}
