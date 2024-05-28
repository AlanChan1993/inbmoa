package com.infinitus.bms_oa.oms.controller;

import com.infinitus.bms_oa.oms.excetion.BMSException;
import com.infinitus.bms_oa.oms.pojo.AliParam;
import com.infinitus.bms_oa.oms.pojo.Bms_Wms_Order_Express;
import com.infinitus.bms_oa.oms.pojo.IpassResultEntity;
import com.infinitus.bms_oa.oms.pojo.OMSBMSReturnOrderInfo;
import com.infinitus.bms_oa.oms.service.LmtJdBsTraceInfoService;
import com.infinitus.bms_oa.oms.service.SignItemService;
import com.infinitus.bms_oa.oms.utils.ResultEntity;
import com.infinitus.bms_oa.oms.service.Bms_Wms_Order_ExpressService;
import com.infinitus.bms_oa.oms.service.OMSBMSReturnOrderInfoService;
import com.infinitus.bms_oa.oms.utils.RequestUtil;
import com.infinitus.bms_oa.oms.utils.SignMD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("lmt/TransmissionController")
public class TransmissionController {

    @Autowired
    private Bms_Wms_Order_ExpressService service;

    @Autowired
    private OMSBMSReturnOrderInfoService infoService;

    @Autowired
    private LmtJdBsTraceInfoService lmtJdBsTraceInfoService;

    @Autowired
    private SignItemService signItemService;

    @Value("${OMS.app_key}")
    private  String app_key ;

    @Value("${OMS.customerId}")
    private  String customerId ;

    @Value("${OMS.TMmethod}")
    private  String TMmethod ;

    @Value("${OMS.secret}")
    private  String secret ;

    @Value("${OMS.URL}")
    private  String URL ;

    @Value("${IPASS.I_PASS_AK_DEV}")
    private String I_PASS_AK_DEV;

    @Value("${IPASS.BASE_URL_DEV}")
    private String BASE_URL_DEV;

    @Value("${IPASS.I_PASS_SK_DEV}")
    private String I_PASS_SK_DEV;

    @Value("${IPASS.APP_KEY_DEV}")
    private String APP_KEY_DEV;

    @Value("${IPASS.IpassUri}")
    private String IpassUri;

    @ResponseBody
    @RequestMapping("setOMSBMSReturnOrderInfo")
    private boolean setOMSBMSReturnOrderInfo(List<OMSBMSReturnOrderInfo> list) {
        return infoService.createOMSBMSReturnOrderInfoList(list);
    }

    @ResponseBody
    @RequestMapping("getOrderExpress")
    public Object getOrderExpress(String status) {
        log.info("【TransmissionController.getOrderExpress】=========status=:{}", status);
        return service.getBms_Wms_Order_Express(status);
    }

    @RequestMapping("pushTransmission")
    public ResultEntity pushTransmission(String expressCode,String shipmentNo,String expressCompanyCode) throws IOException {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("expressCode", expressCode);
        mapData.put("shipmentNo", shipmentNo);
        mapData.put("expressCompanyCode", expressCompanyCode);
        AliParam aliParam = new AliParam();
        aliParam.setAppkey(app_key);
        aliParam.setCustomerId(customerId);
        aliParam.setFormat("json");
        aliParam.setMethod(TMmethod);
        aliParam.setSecret(secret);
        sign(aliParam, mapData);
        aliParam.setSignmethod("md5");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        aliParam.setTimestamp(df.format(time));
        aliParam.setUrl(URL);
        aliParam.setVersion("2.0");
        log.info("【TransmissionController.pushTransmission】==aliParam.getUrl:{}", aliParam.getUrl());
        log.info("【TransmissionController.pushTransmission】==aliParam.getRequestSuffix:{}", aliParam.getRequestSuffix());
        String resultStr = new RequestUtil().doPost(aliParam.getUrl() + "?" + aliParam.getRequestSuffix(), mapData);
        ResultEntity resultEntity = JSON.parseObject(resultStr, ResultEntity.class);
        log.info("【TransmissionController.pushTransmission】===resultEntity=:{}",resultEntity);
        return resultEntity;
    }

    /**
     * 进行数据签名
     * @param aliParam
     * @param mapData
     * @return
     * @throws IOException
     */
    public void sign(AliParam aliParam,Map<String,Object> mapData) throws IOException{
        String signStr = aliParam.getSignPrefix()+JSON.toJSONString(mapData)+aliParam.getSignSuffix();
        aliParam.setSign(SignMD5Utils.byte2hex(SignMD5Utils.encryptMd5(signStr)));
    }

    /**
     * 轨迹优化接口开发
     * @parm: DO_NO
     * @data: lmt_jd_bs_trace_info
     * 返回字段：运单号，执行动作，执行时间，描述，快递公司 ，快顶单号，状态
     * 通过ipaas给外部调用
     * @return IpassResultEntity
    * */
    @ResponseBody
    @GetMapping("getLmtJdBsTraceInfo")
    public IpassResultEntity getLmtJdBsTraceInfo(String doNo){
        if (null == doNo || "".equals(doNo)) {
            throw new BMSException();
        }
        IpassResultEntity infoVOList = lmtJdBsTraceInfoService.getLmtJdBsTraceInfoVO(doNo);
        log.info("【TransmissionController.getLmtJdBsTraceInfo】infoVOList=:{}", infoVOList);
        return infoVOList;
    }

    /**
     * 测试场景重复推送轨迹与接口
     */
    @ResponseBody
    @RequestMapping("postTransmission")
    public Object postTransmission(String expressCode, String shipmentNo, String expressCompanyCode) {
        if (Objects.isNull(expressCode) && Objects.isNull(shipmentNo) && Objects.isNull(expressCompanyCode)) {
            return "参数不能为空";
        }
        boolean a = service.insertExpress(expressCode,shipmentNo,expressCompanyCode);
        return a;
    }

    /**
     * 测试场景重复推送签收与接口
     * */
    @ResponseBody
    @RequestMapping("postOrderSign")
    public Object postOrderSign(String order_no){
        if (Objects.isNull(order_no)) {
            return "参数不能为空";
        }
        boolean a = signItemService.insertOrderSign(order_no);
        return a;
    }


}
