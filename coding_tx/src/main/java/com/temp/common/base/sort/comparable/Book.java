package com.temp.common.base.sort.comparable;

public class Book {
	private String name;
	private Integer weight;
	public String getName() {
		return name;
	}
	public Book setName(String name) {
		this.name = name;
		return this;
	}
	
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", weight=" + weight + "]";
	}

}
