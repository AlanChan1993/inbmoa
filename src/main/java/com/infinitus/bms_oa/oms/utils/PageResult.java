package com.infinitus.bms_oa.oms.utils;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Integer pageSize;
    private long totalNum;
    private Integer totalPage;
    private Integer currentPage;
    private List<T> data;
}
