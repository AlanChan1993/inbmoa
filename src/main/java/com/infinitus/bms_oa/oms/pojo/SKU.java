package com.infinitus.bms_oa.oms.pojo;

import lombok.Data;

@Data
public class SKU {
    private String skuSubCls;//货品细分类

    private String skuCls;//货品分类

    private String isManagerLot;//是否批次管理 1：是；0：否

    private String carTonizeuom;//是否装箱1：是；0：否

    private String c_isexpressbag;//是否快递 1：是；0：否

    private String c_hasinvert;//是否倒放1：是；0：否

    private String barcodeflg;//是否扫码1：是；0：否

    private double stdcube;//B体积 /(CM3)

    private String c_minboxtype_b;//最小箱型B

    private String c_minboxtype_c;//最小箱型C

    private double c_stdcube_c;//C体积/(CM3)

    private double c_stdlength_b;//B长/（CM）

    private double c_stdwidth_b;//B宽/（CM）

    private double c_stdheight_b;//B高/（CM）

    private double c_stdlength_c;//C长/（CM）

    private double c_stdwidth_c;//C宽/（CM）

    private double c_stdheight_c;//C高/（CM）

    private String sku;//物料代码

    private String descr;//物料名称

}
