package com.temp.common.base.singleton.base;

import com.temp.common.base.singleton.Persion;

public class Test {
    public static void main(String[] args) {
        String name = PersionService.getInstance().getName();
        System.out.println(name);
        PersionService.getInstance().setName("b");
        System.out.println(PersionService.getInstance().getName());
    }
}
class Test1{
    public static void main(String[] args) {
        System.out.println(PersionService.getInstance().getName());
    }
}