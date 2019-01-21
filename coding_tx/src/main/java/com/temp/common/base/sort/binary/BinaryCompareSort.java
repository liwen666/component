package com.temp.common.base.sort.binary;

import java.util.Collection;

public class BinaryCompareSort{
	
	/** 
	  * 插入排序 
	  * @param ary 
	 * @return 
	  */ 
	 public static <T> T[] binaryInsert(Collection<? extends Comparable<T>> collection) { 
		@SuppressWarnings("unchecked")
		T [] ary =  (T[]) collection.toArray();
	  int setValueCount = 0; 
	  // 从集合第二个元素开始排序，因为第一个元素本身肯定是已经排好序的 
	  for (int j = 1; j < ary.length; j++) {// 复杂度 n 
	   // 保存当前值 
	   T key = ary[j]; 
	   // ∆ 利用二分查找定位插入位置 
	   printArray(ary); 
	   int index = binarySearchDesc(ary, ary[j], 0, j - 1);// 复杂度：O(logn) 
	   System.out.println();
	   System.out.println("第" + j +"个索引上的元素"+key+"要插入的位置是：" + index); 
	   // 将目标插入位置，同时右移目标位置右边的元素 
	   for (int i = j; i > index; i--) {// 复杂度,最差情况：(n-1)+(n-2)+...+n/2=O(n^2) 
	    ary[i] = ary[i - 1]; //i-1 <==> index 
	    setValueCount++; 
	   } 
	   ary[index] = key; 
	   setValueCount++; 
	  } 
	  System.out.println("\n 设值次数(setValueCount)=====> " + setValueCount); 
	  return ary;
	 } 
	 
	 /** 
	  * 二分查找 降序， 非递归 
	  */
	 @SuppressWarnings("unchecked")
	private static <T>int binarySearchDesc(T[] ary, T ary2, int from, int to) { 
	  for (; from < to; ) { 
	   int mid = (from + to) >>> 1; 
	   if (((Comparable<T>)ary[mid]).compareTo(ary2)==1) { 
	    from = mid + 1; 
	   } else { 
	    to = mid -1; 
	   } 
	  } 
	  if (((Comparable<T>)ary[from]).compareTo(ary2)==1) {//如 5,4, 要插入的是4 
	   return from + 1; 
	  } else { 
	   return from; 
	  } 
	 } 
	 private static <T> void printArray(T[] ary) { 
		  for (T i : ary) { 
		   System.out.print(i + " "); 
		  } 
		 } 
		  

}
