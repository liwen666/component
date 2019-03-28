package com.temp.common.designer23.bridge;

/**
 * 来源地A1
 */
public class AreaA1 extends AreaA {

    @Override
    void fromAreaA() {
        System.out.println("我来自A1");
    }
    
}

/**
 * 来源地A2
 */
class AreaA2 extends AreaA {

    @Override
    void fromAreaA() {
        System.out.println("我来自A2");
    }

}

/**
 * 来源地A3
 */
class AreaA3 extends AreaA {

    @Override
    void fromAreaA() {
        System.out.println("我来自A3");
    }

}