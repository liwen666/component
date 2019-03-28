package com.temp.common.designer23.bridge;

/**
 * 目的地B1
 */
public class AreaB1 implements Qiao {

    @Override
    public void targetAreaB() {
        System.out.println("我要去B1");
    }

}

/**
 * 目的地B2
 */
class AreaB2 implements Qiao {

    @Override
    public void targetAreaB() {
        System.out.println("我要去B2");
    }

}

/**
 * 目的地B3
 */
class AreaB3 implements Qiao {

    @Override
    public void targetAreaB() {
        System.out.println("我要去B3");
    }

}