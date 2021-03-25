package com.temp.common.base.JDBC.greeplum.driver;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/15  18:09
 */
public class Driver {
    private static volatile boolean register;

    static {
        load();
    }

    private static void load() {
        if (!register) {
            register = true;
            Manager.m.put("test", new Driver());
        }
    }

}
