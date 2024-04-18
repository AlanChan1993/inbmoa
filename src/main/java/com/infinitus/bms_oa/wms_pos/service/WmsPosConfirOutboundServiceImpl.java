package com.infinitus.bms_oa.wms_pos.service;

import com.infinitus.bms_oa.wms_pos.mapper.WmsPosConfirOutboundMapper;
import com.infinitus.bms_oa.wms_pos.pojo.WmsPosConfirOutbound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WmsPosConfirOutboundServiceImpl implements WmsPosConfirOutboundService{
    @Autowired
    private WmsPosConfirOutboundMapper mapper;

    @Override
    public boolean createWmsPosConfirOutboundS(List<WmsPosConfirOutbound> list) {
        return mapper.createWmsPosConfirOutboundS(list);
    }

    @Override
    public WmsPosConfirOutbound getWmsPosConfirOutboundService(String troCode) {
        return mapper.getWmsPosConfirOutboundService(troCode);
    }
}
