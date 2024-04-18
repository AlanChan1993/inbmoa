package com.infinitus.bms_oa.oms.service.impl;

import com.infinitus.bms_oa.oms.mapper.SignItemMapper;
import com.infinitus.bms_oa.oms.pojo.SignItem;
import com.infinitus.bms_oa.oms.service.SignItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SignItemServiceImpl implements SignItemService {
    @Autowired
    private SignItemMapper mapper;

    @Override
    public List<SignItem> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public boolean updateStatus(String orderCode, String status, String message) {
        return mapper.updateStatus(orderCode, status, message);
    }


}
