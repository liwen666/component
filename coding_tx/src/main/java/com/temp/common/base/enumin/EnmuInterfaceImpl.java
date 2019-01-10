package com.temp.common.base.enumin;

public enum  EnmuInterfaceImpl implements EnumInterface {
    SECUSS(0,"成功"),
    FAIL(1,"失败"),
    OTHER(2,"其他操作");


    private  Integer code;
    private  String message;


    EnmuInterfaceImpl(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
