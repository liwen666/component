package com.temp.common.base.singleton;

public class TestPersion {
    public static void main(String[] args) {
        System.out.println(PersionService.getPersionService().getId());
        PersionService.getPersionService().setId(0);
        System.out.println(PersionService.getPersionService().getId());

    }
}
class TestPersionMultThead{
    public static void main(String[] args) {
        System.out.println("debug");
        System.out.println(PersionService.getPersionService().getId());
    }
}
