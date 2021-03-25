package com.temp.common.base.JDBC.greeplum.driver;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/15  18:17
 */
public class TestDriver {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.temp.common.base.JDBC.greeplum.driver.Driver");
        System.out.println(Manager.m.get("test"));
    }
}
