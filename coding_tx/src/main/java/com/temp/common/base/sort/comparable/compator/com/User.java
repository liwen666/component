package com.temp.common.base.sort.comparable.compator.com;
/*** 根据order对User排序   */
public class User { //此处无需实现Comparable接口
    private String name;
    private Integer order;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
}