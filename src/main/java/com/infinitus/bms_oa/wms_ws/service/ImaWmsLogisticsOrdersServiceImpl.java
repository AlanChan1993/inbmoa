package com.infinitus.bms_oa.wms_ws.service;

import com.infinitus.bms_oa.enums.ResultEnums;
import com.infinitus.bms_oa.wms_ws.mapper.ImaWmsLogisticsOrdersMapper;
import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsLogisticsOrders;
import com.infinitus.bms_oa.wms_ws.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ImaWmsLogisticsOrdersServiceImpl implements ImaWmsLogisticsOrdersService{
    @Autowired
    private ImaWmsLogisticsOrdersMapper mapper;
    @Override
    public List<ImaWmsLogisticsOrders> getImaWmsLogisticsOrdersList() {
        return mapper.getImaWmsLogisticsOrdersList();
    }

    @Override
    public List<String> getSap2WmsDeptOutcomeList() {
        return mapper.getSap2WmsDeptOutcomeList();
    }

    @Override
    public boolean updateStatus(String status,Integer id) {
        return mapper.updateStatus(status, id);
    }

    @Override
    public boolean updateSap2WmsStatus(String status, String  rsnum) {
        return mapper.updateSap2WmsStatus(status, rsnum);
    }

    /*
    * 2024-08-22
    * 根据WS对接同事需要重推数据而设计的接口
    * 进行充值两个表的status状态
    * 重置后，定时任务接口会自动推送
    * */
    @Override
    public ResponseEntity updateItemByNum(String number) {
        ResponseEntity responseEntity = new ResponseEntity();
        //1.判空
        if(null==number||"".equals(number)){
            responseEntity.setCode(ResultEnums.PARM_NULL.getCode().toString());
            responseEntity.setMessage(ResultEnums.PARM_NULL.getMsg());
        }else {
            //2.查询有无单据
            Integer orderSum = mapper.selectORDERSSumBySapNum(number);
            Integer sapSum = mapper.selectSAP2WMSSumByRSNUM(number);
            if (orderSum != 0 && sapSum != 0) {
                //3.默认status=0
                mapper.updateOrderStatus("0",number);
                mapper.updateSap2WmsStatus2("0", number);
                responseEntity.setCode(ResultEnums.UPDATE_SUCCESS.getCode().toString());
                responseEntity.setMessage(ResultEnums.UPDATE_SUCCESS.getMsg());
            } else {
                responseEntity.setCode(ResultEnums.SUM_NULL.getCode().toString());
                responseEntity.setMessage(ResultEnums.SUM_NULL.getMsg() + "\n" + "orderSum:" + orderSum + "\n" +
                        "sapSum:" + sapSum);
            }
        }
        log.warn("getOrderByNum===== responseEntity=:{}", responseEntity);
        return responseEntity;
    }

    @Override
    public ResponseEntity getOrderByNum(String number) {
        log.warn("getOrderByNum===== ");
        ResponseEntity responseEntity = new ResponseEntity();
        List<ImaWmsLogisticsOrders> list = mapper.getOrderByNum(number);
        responseEntity.setCode(ResultEnums.SUCCESS.getCode().toString());
        responseEntity.setMessage(ResultEnums.SUCCESS.getMsg());
        responseEntity.setData(list);
        log.warn("getOrderByNum===== responseEntity=:{}", responseEntity);
        return responseEntity;
    }

}
