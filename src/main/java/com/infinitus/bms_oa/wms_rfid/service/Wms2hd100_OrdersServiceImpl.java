package com.infinitus.bms_oa.wms_rfid.service;

import com.infinitus.bms_oa.wms_rfid.mapper.Wms2hd100_OrdersMapper;
import com.infinitus.bms_oa.wms_rfid.pojo.DTO.Wms2hd100_OrdersDTO;
import com.infinitus.bms_oa.wms_rfid.pojo.Wms2hd100_Orders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class Wms2hd100_OrdersServiceImpl implements Wms2hd100_OrdersService{
    @Autowired
    private Wms2hd100_OrdersMapper mapper;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Wms2hd100_Orders> getWms2HD100Orders(Wms2hd100_OrdersDTO ordersDTO) {
        if (StringUtils.isBlank(ordersDTO.getStartTime())) {
            ordersDTO.setStartTime("2024-01-01");
        }
        if (StringUtils.isBlank(ordersDTO.getEndTime())) {
            ordersDTO.setEndTime(dateFormat.format(new Date()));
        }

        return mapper.getWms2HD100Orders(ordersDTO);
    }

}
