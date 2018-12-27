package com.temp.common.base.constructor;

/**
 * 此代码说明创建一个类的同时可以改变其他单例类的属性信息
 */
public class TestConstructorExtend {
    public static void main(String[] args) {
        System.out.println(PersionService.getPersionService().getName());
        TargetUser targetUser = new TargetUser("target");
        System.out.println(PersionService.getPersionService().getName());
    }
}
