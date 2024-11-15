package com.infinitus.bms_oa.oms.pojo.converter;

import com.infinitus.bms_oa.oms.pojo.SKU;
import com.infinitus.bms_oa.oms.pojo.VO.SKUVO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SkuConvertSkuVO {
    public SKUVO convert(SKU sku) {
        SKUVO skuvo = new SKUVO();
        skuvo.setSkuCls(sku.getSkuCls());
        skuvo.setSkusubCls(sku.getSkuSubCls());

        String IsManagerLot=sku.getIsManagerLot();
        if (StringUtils.isBlank(IsManagerLot)){
            IsManagerLot = "0";
        }else{ IsManagerLot ="1";}
        skuvo.setIsManagerLot(IsManagerLot);

        String CarTonizeuom=sku.getCarTonizeuom();
        if (StringUtils.isBlank(CarTonizeuom)){
            CarTonizeuom = "0";
        }else{ CarTonizeuom ="1";}
        skuvo.setCarToniZeuom(CarTonizeuom);

        String setIsExpressBag=sku.getC_isexpressbag();
        if (StringUtils.isBlank(setIsExpressBag)){
            setIsExpressBag = "0";
        }else{ setIsExpressBag ="1";}
        skuvo.setIsExpressBag(setIsExpressBag);

        String IsHasInvert=sku.getC_hasinvert();
        if (StringUtils.isBlank(IsHasInvert)){
            IsHasInvert = "0";
        }else{ IsHasInvert ="1";}
        skuvo.setIsHasInvert(IsHasInvert);

        String IsBarcodeFlg = sku.getBarcodeflg();
        if (StringUtils.isBlank(IsBarcodeFlg)){
            IsBarcodeFlg = "0";
        }else{ IsBarcodeFlg ="1";}
        skuvo.setIsBarcodeFlg(IsBarcodeFlg);

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
