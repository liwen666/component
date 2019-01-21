package com.temp.common.base.sort.binary;


/**
 * Created by Administrator on 2017/5/2.
 */
public class BinarySort {
    public static void main (String []args){
        int []array={1,2,9,3};
        printArray(array);
        BinInsertSort(array);
        System.out.println("\n");
        printArray(array);
    }

    public static  void BinInsertSort(int a[])
    {
        int key, left, right, middle;
        for (int i=1; i<a.length; i++)
        {
            key = a[i];  //key为要排的目的值。其前面为已经排好的数组
            left = 0;
            right = i-1;
            while (left<=right)
            {
                middle = (left+right)/2;
                if (a[middle]>key)
                    right = middle-1;    //比较中值与要排的值大小。更新数组下标left与right的值
//                                             进而缩短要插入的数组长度
                else
                    left = middle+1;
            }

            for(int j=i-1; j>=left; j--)
            {
                a[j+1] = a[j];   //输出排序后的数组。此时小于更新后的下标left值的数组值都要改变
            }

            a[left] = key;
        }
    }
    public static void printArray(int[]array){
    	for(int i=0;i<array.length;i++){      //数组的输出，频繁调用
    		System.out.print(array[i]+" ");
    	}
    	
    }
}