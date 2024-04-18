package com.infinitus.bms_oa.wms_pos.service;

import com.infinitus.bms_oa.wms_pos.pojo.WmsPosConfirOutbound;

import java.util.List;

public interface WmsPosConfirOutboundService {
    boolean createWmsPosConfirOutboundS(List<WmsPosConfirOutbound> list);

    WmsPosConfirOutbound getWmsPosConfirOutboundService(String troCode);
}