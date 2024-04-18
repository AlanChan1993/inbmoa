package com.infinitus.bms_oa.wms_pos.mapper;

import com.infinitus.bms_oa.wms_pos.pojo.WmsPosConfirOutbound;

import java.util.List;

public interface WmsPosConfirOutboundMapper {
    boolean createWmsPosConfirOutboundS(List<WmsPosConfirOutbound> list);

    WmsPosConfirOutbound getWmsPosConfirOutboundService(String troCode);
}
