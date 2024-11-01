package com.infinitus.bms_oa.oms.pojo.VO;

import lombok.Data;

@Data
public class SKUVO {
    private String skusubCls;
    private String skuCls;
    private String isManagerLot;
    private String carToniZeuom;
    private String isExpressBag;
    private String isHasInvert;
    private String isBarcodeFlg;
    private double stdcube;
    private String minBoxB;
    private String minBoxC;
    private double stdCubeC;
    private double stdLengthB;
    private double stdWidthB;
    private double stdHeightB;
    private double stdLengthC;
    private double stdWidthC;
    private double stdHeightC;
    private String sku;
    private String descr;
}
