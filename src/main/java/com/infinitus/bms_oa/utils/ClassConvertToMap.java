package com.infinitus.bms_oa.utils;

import com.infinitus.bms_oa.wms_ws.pojo.DTO.ImaWmsLogisticsOrdersDTO;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassConvertToMap {
    public ClassConvertToMap(ImaWmsLogisticsOrdersDTO ordersDTO) {
    }

    public Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
