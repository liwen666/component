package com.temp.common.base.util.bigdecimal;

/**
 * author lw
 * date 2019/7/19  14:07
 * discribe fff
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(11133%100);
        int i1 = (11133 - 33) / 100;
        System.out.println(i1);
        int abc1 = 111;
        int abc2 = 33;
        System.out.println(((abc1-(abc1%abc2))+abc2)/abc2);
        System.out.println("-----------");
        int count1 = 0;//保存能被3整除的数的和
        int count2 = 0;//保存不能被3整除的数的和
        for(int i = 10;i<=99;i++)
        {
            if(i%3==0)
                count1 = count1+i;
            else
                count2 = count2 +i;
        }
        System.out.println("count1="+count1+" count2="+count2);
    }
}
