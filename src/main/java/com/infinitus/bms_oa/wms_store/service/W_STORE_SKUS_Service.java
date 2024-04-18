package com.infinitus.bms_oa.wms_store.service;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_SKUS;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface W_STORE_SKUS_Service {
    boolean updateW_STORE_SKUS(W_STORE_SKUS w_store_skus);

    List<W_STORE_SKUS> queryW_STORE_SKUS();

    boolean deleteW_STORE_SKUS();

    boolean insertSkusList(List<W_STORE_SKUS> list);

    int selectW_STORE_SKUS(String no);

    boolean insertSkusHistoryList(@Param("list")List<W_STORE_SKUS> list);


}
