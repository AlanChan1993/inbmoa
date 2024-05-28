package com.infinitus.bms_oa.oms.mapper;

import com.infinitus.bms_oa.oms.pojo.SignItem;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
@MapperScan
public interface SignItemMapper {
    List<SignItem> selectAll();

    boolean updateStatus(String orderCode,String status,String message);

    boolean insertOrderSign(String order_no);
}
