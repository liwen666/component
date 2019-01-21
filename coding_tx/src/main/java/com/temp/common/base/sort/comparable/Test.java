package com.temp.common.base.sort.comparable;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		
		
		Book a = new Book();
		Book b = new Book();
		Book c = new Book();
		a.setName("课本1").setWeight(10);;
		b.setName("课本2").setWeight(20);
		c.setName("课本3").setWeight(15);
		List< Book>list = new ArrayList<Book>();
		list.add(a);
		list.add(b);
		list.add(c);
		System.out.println(list);
		java.util.Collections.sort(list,new BookComparetor() );
		System.out.println(list);
//		Book[] binaryInsert = BinaryCompareSort.binaryInsert(list);
//		System.out.println(binaryInsert);
//		
	}
	
	
	

}
