package vip.dcpay.validate.sdk.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: liq
 * @Date: 2019/6/17 10:17
 * @Description:
 */
public enum ValidateItemTypeEnum {

    SMS(0, "短信验证", "SMS"),
    MAIL(1, "邮件验证", "MAIL"),
    GA(2, "GA验证", "GA"),
    REAL_NAME(3, "实名验证", "REAL_NAME"),
    TRADE_PWD(4, "交易密码验证", "TRADE_PWD"),
    LOGIN_PWD(5, "登录密码校验", "LOGIN_PWD");


    private int code;
    private String desc;
    private String remark;

    private ValidateItemTypeEnum(int code, String desc, String remark) {
        this.code = code;
        this.desc = desc;
        this.remark = remark;
    }

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDesc(Integer code) {
        ValidateItemTypeEnum c = getEnum(code);
        if (null == c) {
            return code + "";
        } else {
            return c.desc;
        }
    }

    public static ValidateItemTypeEnum getEnum(Integer code) {
        if (null == code) {
            return null;
        }

        for (ValidateItemTypeEnum c : ValidateItemTypeEnum.values()) {
            if (c.code() == code.intValue()) {
                return c;
            }
        }
        return null;
    }

    public static ValidateItemTypeEnum getEnumByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        return ValidateItemTypeEnum.valueOf(name.toUpperCase());
    }

    public static boolean isValid(Integer code) {
        return null != getEnum(code);
    }

}
