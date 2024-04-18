package com.infinitus.bms_oa.ipass.mapper;

import com.infinitus.bms_oa.ipass.pojo.Bms_ipass_sku_relation;
import org.apache.ibatis.annotations.Mapper;

@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
public interface Bms_ipass_sku_relationMapper {
    boolean createBms_ipass_sku_relation(Bms_ipass_sku_relation bmsIpassSkuRelation);

    Bms_ipass_sku_relation getBms_ipass_sku_relationBySku(String productno);

}
