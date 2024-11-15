package com.infinitus.bms_oa.oms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infinitus.bms_oa.oms.pojo.DTO.SkuDTO;
import com.infinitus.bms_oa.oms.pojo.SKU;
import com.infinitus.bms_oa.oms.pojo.VO.SKUVO;
import com.infinitus.bms_oa.oms.pojo.converter.SkuConvertSkuVO;
import com.infinitus.bms_oa.oms.service.SKUService;
import com.infinitus.bms_oa.oms.utils.PageResult;
import com.infinitus.bms_oa.oms.utils.ResultEntity;
import com.infinitus.bms_oa.oms.utils.ResultOMS;
import com.infinitus.bms_oa.oms.utils.SKUResult;
import com.infinitus.bms_oa.pojo.VO.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/skuCtl")
public class SkuController {
    @Autowired
    private SKUService service;

    @RequestMapping("getSku")
    public SKUResult getSku(@RequestBody SkuDTO dto){
        SKUResult res = new SKUResult();
        PageResult pageResult = new PageResult();
        if (dto.getPageIndex() == null || dto.getPageIndex() == 0) dto.setPageIndex(1);
        if(dto.getPageSize()==null||dto.getPageSize()==0) dto.setPageSize(500) ;
        try {
            PageHelper.startPage(dto.getPageIndex(), dto.getPageSize());
            List<SKU> list = service.getSku(dto);
            List<SKUVO> skuvoList = new SkuConvertSkuVO().convertList(list);

            PageInfo<SKUVO> pageInfo = new PageInfo<>(skuvoList);
            pageResult.setPageSize(pageInfo.getPageSize());//每页数目
            pageResult.setTotalNum(pageInfo.getTotal());//总数目
            pageResult.setTotalPage(pageInfo.getPages());//总页数
            pageResult.setCurrentPage(pageInfo.getPageNum());//当前页
            pageResult.setData(pageInfo.getList());//响应业务数据

            res.setSuccess("true");
            res.setData(pageResult);
        } catch (Exception ex) {
            res.setSuccess("false");
            res.setErrCode("500");
            res.setErrDesc("获取失败");
        }
        return res;
    }

}
