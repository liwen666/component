package org.csource.common;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;

/**
 * 订单角色枚举
 *
 * @Auther: liq
 * @Date: 2019/5/31 16:42
 * @Description:
 */
public enum FileGroupEnum implements IBaseEnum {

    DEFAULT(1,"group1","默认文件存储"),
    CASUALLY(2, "group2","临时文件存储"),
    PERSISTENCE(3, "group3","持久文件存储");

    private int code;
    private String name;
    private String desc;

    FileGroupEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDesc(Integer code) {
        if (null == code) {
            return null;
        }

        FileGroupEnum c = getEnum(code);
        if (null == c) {
            return code + "";
        } else {
            return c.desc;
        }
    }

    public static FileGroupEnum getEnum(Integer code) {
        if (null == code) {
            return null;
        }

        for (FileGroupEnum c : FileGroupEnum.values()) {
            if (c.code() == code.intValue()) {
                return c;
            }
        }
        return null;
    }

    public static boolean isValid(Integer code) {
        if (null == code) {
            return false;
        }

        return null != getEnum(code);
    }

    public static void main(String[] args) throws IllegalAccessException {
        FileGroupEnum[] values = FileGroupEnum.values();
        for (FileGroupEnum f:values){
            System.out.println(JSON.toJSONString(f));
            Field[] declaredFields = f.getClass().getDeclaredFields();
            for (Field ff:declaredFields){
                System.out.print(ff.get(f)+"  ");
            }
        }
    }
}
