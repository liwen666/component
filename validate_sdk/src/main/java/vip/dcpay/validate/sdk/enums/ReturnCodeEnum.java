package vip.dcpay.validate.sdk.enums;

/**
 * @Auther: liq
 * @Date: 2019/6/23 12:16
 * @Description:
 */
public enum ReturnCodeEnum {

    SUCCEED(0, "成功"),

    FAILED(-1, "失败"),
    INVALID(-10, "token无效"),
    TIMEOUT(-11, "token过期"),

    VALIDATE_ITEM_NOT_FIT(-20, "不符合最小校验要求"),
    VALIDATE_RESULT_NOT_PASS(-21, "校验未通过"),

    GET_USER_CONFIG_FAILED(-30, "获取用户验证配置信息失败"),
    GET_SYSTEM_CONFIG_FAILED(-31, "获取系统验证配置信息失败");


    private int code;
    private String desc;

    private ReturnCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public String getDesc(Integer code) {
        ReturnCodeEnum c = getEnum(code);
        if (null == c) {
            return code + "";
        } else {
            return c.desc;
        }
    }

    public static ReturnCodeEnum getEnum(Integer code) {
        if (null == code) {
            return null;
        }

        for (ReturnCodeEnum c : ReturnCodeEnum.values()) {
            if (c.code() == code.intValue()) {
                return c;
            }
        }
        return null;
    }

    public static boolean isValid(Integer code) {
        return null != getEnum(code);
    }


}
