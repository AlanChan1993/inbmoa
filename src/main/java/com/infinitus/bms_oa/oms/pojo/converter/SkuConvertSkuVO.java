package com.infinitus.bms_oa.oms.pojo.converter;

import com.infinitus.bms_oa.oms.pojo.SKU;
import com.infinitus.bms_oa.oms.pojo.VO.SKUVO;

import java.util.List;
import java.util.stream.Collectors;

public class SkuConvertSkuVO {
    public SKUVO convert(SKU sku) {
        SKUVO skuvo = new SKUVO();
        skuvo.setSkuCls(sku.getSkuCls());
        skuvo.setSkusubCls(sku.getSkuSubCls());
        skuvo.setIsManagerLot(sku.getIsManagerLot());
        skuvo.setCarToniZeuom(sku.getCarTonizeuom());
        skuvo.setIsExpressBag(sku.getC_isexpressbag());
        skuvo.setIsHasInvert(sku.getC_hasinvert());
        skuvo.setIsExpressBag(sku.getC_isexpressbag());
        skuvo.setStdcube(sku.getStdcube());
        skuvo.setMinBoxB(sku.getC_minboxtype_b());
        skuvo.setMinBoxC(sku.getC_minboxtype_c());
        skuvo.setStdCubeC(sku.getC_stdcube_c());
        skuvo.setStdLengthB(sku.getC_stdlength_b());
        skuvo.setStdWidthB(sku.getC_stdwidth_b());
        skuvo.setStdHeightB(sku.getC_stdheight_b());
        skuvo.setStdLengthC(sku.getC_stdlength_c());
        skuvo.setStdWidthC(sku.getC_stdwidth_c());
        skuvo.setStdHeightC(sku.getC_stdheight_c());
        skuvo.setSku(sku.getSku());
        skuvo.setDescr(sku.getDescr());

        return skuvo;
    }

    public List<SKUVO> convertList (List<SKU> list){
        return list.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
