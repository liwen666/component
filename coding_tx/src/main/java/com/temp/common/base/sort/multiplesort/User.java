package com.temp.common.base.sort.multiplesort;

import lombok.Data;

/*** 根据order对User排序   */
@Data
public class User { //此处无需实现Comparable接口
    private String name;
    private boolean top;
    private Integer order;
}