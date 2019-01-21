package com.temp.common.base.sort.comparable;

import java.util.Comparator;

public class BookComparetor implements Comparator<Book>{

	@Override
	public int compare(Book o1, Book o2) {
		if(o1==null||o2==null){return -1;}
		if(o1.getWeight()>o2.getWeight()){
			return 1;
		}
		return -1;
//		int compareTo = (o1.getWeight()).compareTo(o2.getWeight());
//		System.out.println(compareTo+"????????????????");
//		return compareTo;
	}

}
