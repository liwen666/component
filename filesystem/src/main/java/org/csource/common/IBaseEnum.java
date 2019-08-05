package org.csource.common;

/**
 * @Auther: liq
 * @Date: 2019/5/18 17:14
 * @Description:
 */
public interface IBaseEnum<T> {
    int code();

    String desc();

    String getDesc(Integer code);

//    T getEnum(int code);
//
//    boolean isValid(int code);
}
