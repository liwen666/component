package vip.dcpay.validate.sdk.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: liq
 * @Date: 2019/6/18 17:24
 * @Description:
 */
public enum BusinessTypeEnum {

    REGISTER("register", "注册"),
    LOGIN("login", "登录"),
    APPLY_API_KEY("applyApiKey", "申请apiKey"),
    MODIFY_API_KEY("modifyApiKey", "修改ApiKey"),
    APPLY_PUBLIC_KEY("applyPublicKey", "设置第三方公钥"),
    WITHDRAW("withdraw", "提现"),
    FORGET_PWD("forgetPwd", "忘记密码"),
    SET_TRADE_PWD("setTradePwd", "设置资金密码"),
    UNBIND_MAIL("unBindEmail", "解绑邮箱"),
    UNBIND_GOOGLE("unbindGoogle", "解绑谷歌"),
    BIND_GOOGLE("bindGoogle", "绑定谷歌"),
    MODIFY_PWD("modifyPwd", "修改登录密码"),
    BIND_MAIL("bindEmail", "绑定邮箱"),
    SAVE_BANK_CARD("saveBankcard", "添加支付信息"),
    TRANSFER_TO_SUB_MERCHANT("transferToSubMerchant","商家下级转账"),
    BIND_PHONE("bindPhone","绑定手机号"),
    UN_BIND_PHONE("unBindPhone","解绑手机号")
    ;



    private String code;
    private String desc;

    private BusinessTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public String getDesc(String code) {
        BusinessTypeEnum c = getEnum(code);
        if (null == c) {
            return code + "";
        } else {
            return c.desc;
        }
    }

    public static BusinessTypeEnum getEnum(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (BusinessTypeEnum c : BusinessTypeEnum.values()) {
            if (c.code().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    public static boolean isValid(String code) {
        return null != getEnum(code);
    }

}
