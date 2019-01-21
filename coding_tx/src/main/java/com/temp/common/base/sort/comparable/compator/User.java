package com.temp.common.base.sort.comparable.compator;
/**
* 根据order对User排序
*/
public class User implements Comparable
{
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
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return this.getOrder().compareTo(((User)o).getOrder());
	}
}