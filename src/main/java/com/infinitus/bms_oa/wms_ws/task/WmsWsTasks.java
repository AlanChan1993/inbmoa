package com.infinitus.bms_oa.wms_ws.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.enums.ResultEnum;
import com.infinitus.bms_oa.utils.DateUtil;
import com.infinitus.bms_oa.utils.IpassUtil;
import com.infinitus.bms_oa.wms_ws.pojo.DTO.OrderDetailsDTO;
import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsLogisticsOrders;
import com.infinitus.bms_oa.wms_ws.pojo.ImaWmsOrdersDetail;
import com.infinitus.bms_oa.wms_ws.service.ImaWmsLogisticsOrdersService;
import com.infinitus.bms_oa.wms_ws.service.ImaWmsOrdersDetailService;
import com.infinitus.bms_oa.wms_ws.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
//@Component
public class WmsWsTasks {
    @Value("${WS.IPASS.baseURL}")
    private String baseURL;

    @Value("${WS.IPASS.ipassUri}")
    private String ipassUri;

    @Value("${WS.IPASS.AK}")
    private String AK;

    @Value("${WS.IPASS.SK}")
    private String SK;

    @Value("${WS.IPASS.AppKey}")
    private String AppKey;

    @Autowired
    private ImaWmsLogisticsOrdersService service;

    @Autowired
    private ImaWmsOrdersDetailService detailService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 30*1s执行一次
     * */
    @Scheduled(fixedRate = 1000 * 30 * 30)
    public void taskWmsWS() throws UnsupportedEncodingException {
        log.info("taskWmsWS当前时间:{}",new DateUtil().getNowDate2());
        synOrders();
    }

    public void synOrders(){
        List<ImaWmsLogisticsOrders> ordersList = service.getImaWmsLogisticsOrdersList();
        if (ordersList.size() > 0) {
            log.info("【WmsWsTasks.synOrders()】,ordersList.size()={}", ordersList.size());
            ordersList.stream().forEach(e->{
                //表单数据
                Map<String, Object> mapData = new HashMap<>();
                mapData.put("applyNo", e.getITEM_NUMBER());
                Date date = null;
                if (null != e.getITEM_APPLYDATE()) {
                    try {
                        date = sdf.parse(e.getITEM_APPLYDATE());
                    } catch (ParseException px) {
                        px.printStackTrace();
                    }
                }
                mapData.put("applyDateTime", date);
                mapData.put("applyDealerAccountName", e.getITEM_APPLYPERSON());
                mapData.put("applyDealerName", e.getITEM_APPLYPERSON_NAME());
                mapData.put("applyPurpose", e.getITEM_APPLICATIONNAME());
                mapData.put("receiverName", e.getITEM_CONSIGNEEUSER_NAME());
                mapData.put("receiverAccountName", e.getITEM_CONSIGNEEUSER());
                mapData.put("companyNo", e.getITEM_COMPANYCF());
                mapData.put("compensation", e.getITEM_ISCOMPENSATE());
                //mapData.put("project", e.getMJAHR());
                mapData.put("moveType", e.getBWART());
                mapData.put("costCenter", e.getKOSTL());
                mapData.put("orderNo", e.getITEM_NUMBER());
                mapData.put("compensation", e.getITEM_ISCOMPENSATE());
                //明细
                List<ImaWmsOrdersDetail> detailList = detailService.getImaWmsOrdersDetails(e.getITEM_NUMBER());
                List<OrderDetailsDTO> details =new ArrayList<>();
                if(detailList.size()>0){
                    detailList.stream().forEach(d->{
                        OrderDetailsDTO detailsDTO = new OrderDetailsDTO();
                        detailsDTO.setProdCode(d.getITEM_PRODUCTCODE());
                        detailsDTO.setMaterialType(d.getITEM_CLASS());
                        detailsDTO.setBatchNo(d.getITEM_BATCH());
                        detailsDTO.setCount(d.getITEM_QUANTITY());
                        detailsDTO.setProject(d.getMJAHR());
                        details.add(detailsDTO);
                    });
                }
                mapData.put("details", details);
                JSONObject jsonObject = new JSONObject(mapData);
                log.info("【WmsWsTasks.synOrders】jsonObject=:{}", jsonObject);
                ResponseEntity responseEntity = null;
                JSONObject response = null;
                try {
                    response = new IpassUtil().postReq(ipassUri, jsonObject.toString(), AppKey, AK, SK, baseURL);
                    log.info("【synOrders.IpassUtil().postReq】response={}", response);
                } catch (Exception ex) {
                    log.info("【synOrders.IpassUtil().postReq】ex={}", ex);
                }

                responseEntity = JSON.parseObject(response.toString(), ResponseEntity.class);
                log.info("【synOrders.IpassUtil().postReq】responseEntity=:{}", responseEntity);
                //推送成功后需要更新推送状态 status
                if ("true".equals(responseEntity.getSuccess())) {
                    log.info("responseEntity.getSuccess()=true推送成功");
                    service.updateStatus(ResultEnum.SUCCESS.getCode().toString(), e.getORDER_ID());
                }else {
                    //推送失败操作
                    service.updateStatus(ResultEnum.FALSE.getCode().toString(), e.getORDER_ID());
                    log.info("responseEntity.getSuccess()=flase推送失败");
                }
            });
        }

    }








}
