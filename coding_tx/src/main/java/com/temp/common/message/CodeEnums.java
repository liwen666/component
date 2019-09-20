package com.temp.common.message;

public enum CodeEnums {
    EXCEPTION(999, "exception", "异常"),
    PAGE_NOT_FOUND(404, "page_not_found", "页面不存在"),
    ILLEGALARGUMENT(400, "illegalargument", "参数校验异常"),
    PARAM_EMPTY(1001, "param_empty", "参数为空"),
    PARAM_VALUE_ERROR(1016, "param_value_error", "参数设置错误"),
    USER_NOT_EXIST(1018, "USER_NOT_EXIST", "用户不存在"),
    SUCCESS(1000, "SUCCESS", "成功"),
    ACCOUNT_HAS_REGISTED(1002, "account_has_registed", "账户已注册"),
    ACCOUNT_PASS_ERROR(1003, "account_pass_error", "账户或密码错误"),
    ACCOUNT_FORBIDDENED(1004, "account_forbiddened", "账户已禁用"),
    ACCOUNT_NOT_ACTIVE(1005, "account_not_active", "账户未激活"),
    ACCOUNT_HAS_LOCKED(1006, "account_has_locked", "账户已锁定"),
    CARD_ID_REPEATED(1007, "cardid_repeated", "证件号重复"),
    MOBILE_ALREADY_BINDED(1008, "mobile_already_binded", "手机号已绑定"),
    MOBILE_NOT_SELF(1009, "mobile_not_self", "手机号非本人"),
    EMAIL_ALREADY_BINDED(1010, "email_already_binded", "邮箱已绑定"),
    NOT_BIND_EMAIL(1011, "not_bind_email", "邮箱未绑定"),
    NOT_BIND_PHONE(1012, "not_bind_phone", "手机未绑定"),
    LOGIN_PASS_ERROR(1013, "login_pass_error", "登陆密码错误"),
    LOGINPASS_SAME_TRADEPASS(1014, "loginpass_same_tradepass", "登陆密码与交易密码一致"),
    ACCOUNT_NOT_EXIST(1015, "account_not_exist", "用户不存在"),
    EMAIL_NOT_SELF(1017, "email_not_self", "邮箱非本人"),
    GOOGLE_NOT_SELF(1018, "google_not_self", "googlekey非本人"),
    MOBILE_PATTERN_ERROR(1019, "mobile_pattern_error", "手机格式不正确"),
    EMAIL_PATTERN_ERROR(1020, "email_pattern_error", "邮箱格式不正确"),
    DEVICE_TYPE_ERROR(1021, "device_type_error", "设备类型错误"),
    IP_PATTERN_ERROR(1022, "ip_pattern_error", "ip格式不正确"),
    COUNTRY_PATTERN_ERROR(1023, "country_pattern_error", "country格式不正确"),
    PARAM_VALUE_LENGTH_ERROR(1024, "param_value_length_error", "参数值长度错误"),
    GOOGLEKEY_NOT_BIND(1025, "googlekey_not_bind", "googlekey未绑定"),
    PASSWORD_REPEAT(1026, "password_repeat", "密码重复"),
    PASSPORT_PATTERN_ERROR(1027, "passport_pattern_error", "护照号不正确"),
    ID_CARD_PATTERN_ERROR(1028, "id_card_pattern_error", "身份证号不正确"),
    IMAGE_PATTERN_ERROR(1029, "image_pattern_error", "图片格式不正确"),
    REAL_NAME_REPEAT_ERROR(1030, "realname_repeat_error", "用户已实名认证"),
    BIND_GOOGLE_REPEAT_ERROR(1031, "bind_google_repeat_error", "用户已绑定谷歌"),
    COIN_ACCOUNT_NOT_EXIST(2000, "coin_accoint_not_exist", "币账户不存在"),
    COIN_NOT_ISSUED(2001, "coin_not_issued", "币种未发布"),
    CUSTOMER_NOT_EXIST(2002, "customer_not_exist", "用户不存在"),
    REDIS_COIN_ACCOUNT_NOT_EXIST(2003, "redis_coin_account_not_exist", "redis币账户不存在"),
    COIN_ACCOUNT_LOAD_TO_REDIS_ERROR(2004, "coin_account_load_to_redis_error", "币账户加载到Redis失败"),
    COIN_ACCOUNT_CREATE_ERROR(2005, "coin_accoint_create_error", "币账户创建失败"),
    COIN_ACCOUNT_FUND_NOT_ENOUGH(2006, "coin_accoint_fund_not_enough", "资金不足"),
    APPLY_MONEY_LOCK_FAILED(2007, "apply_money_lock_failed", "申请资金失败,已在申请中"),
    COIN_ACCOUNT_LOCK_FAILED(2008, "coin_account_lock_failed", "资金锁定失败，已被锁定"),
    APPLYID_CREATE_FAILED(2009, "applyid_create_failed", "applyId 创建失败"),
    FUND_APPLY_LOCK_FAILED(2010, "fund_apply_lock_failed", "资源读取失败，已被锁定"),
    FUND_APPLY_CANNOT_FREEZE(2011, "fund_apply_can_not_freeze", "资源冻结失败，未处于可冻结状态"),
    BUSINESS_TYPE_INVALID(2012, "fund_apply_has_frozen", "无效的业务类型"),
    APPLY_MONEY_FAILED(2013, "apply_money_failed", "申请资金失败"),
    FREEZE_MONEY_FAILED(2014, "freeze_money_failed", "冻结资金失败"),
    FUND_APPLY_NOT_EXIST(2015, "fund_apply_not_exist", "资金申请id不存在"),
    FUND_APPLY_CANNOT_UNFREEZE(2016, "fund_apply_can_not_unfreeze", "资源解冻失败，未处于可解冻状态"),
    PARAM_VALUE_NOT_IN_RANGE(2017, "param_value_not_in_range", "参数值不在范围内"),
    FUND_MONEY_EXCEPTION(2018, "fund_money_exception", "资金使用异常"),
    UNFREEZE_MONEY_FAILED(2019, "unfreeze_money_failed", "解冻资金失败"),
    USE_MONEY_FAILED(2020, "use_money_failed", "使用资金失败"),
    APPLYID_BUSINESSID_MISMATCHING(2021, "applyId_businessId_mismatching", "申请Id与业务Id不匹配"),
    FUND_MONEY_OUT_IN_MISMATCHING(2022, "fund_money_out_in", "资金出入不一致"),
    INTERFACE_NOT_IMPLEMENTED(2023, "interface_not_implemented", "接口未实现"),
    ADD_MONEY_PROCESSING(2024, "add_money_processing", "业务处理中，请不要重复提交"),
    ADD_MONEY_PROCESSED(2025, "add_money_processed", "业务已处理，请不要重复提交"),
    ADD_MONEY_FAILED(2026, "add_money_failed", "使用资金失败"),
    BUSINESS_CANNOT_REUSE(2027, "business_cannot_reuse", "业务不能重复消费"),
    COIN_MONEY_IS_NEGATIVE(2028, "coin_money_is_negative", "资金有负值"),
    COIN_ACCOUNT_IS_INCONSISTENT(2029, "coin_money_is_inconsistent", "消费币账户与申请币账户不一致"),
    OLD_NEW_PASSWORD_SAME(2030, "old_new_password_same", "新旧密码一样"),
    NEED_LOGIN(2031, "need_login", "请先登录"),
    GOOGLE_VALIDATE_ERROR(2032, "google_validate_error", "谷歌认证失败"),
    EMAIL_CODE_ERROR(2033, "email_code_error", "邮箱验证码错误"),
    PHONE_CODE_ERROR(2034, "phone_code_error", "手机验证码错误"),
    DATA_NOT_EXIST(2035, "data_not_exist", "数据不存在"),
    USERAUTH_NOT_EXIST(2036, "userauth_not_exist", "用户认证不存在"),
    PHONECODE_ALREADY_EXIST(2037, "phonecode_already_exist", "验证码已发送"),
    INPUT_ERROR_OUT_SCOPE(2038, "input_error_out_scope", "输错次数超过上限"),
    PICTURE_TYPE_ERROR(2039, "picture_type_error", "图片格式不正确"),
    UPLOAD_ERROR(2040, "upload_error", "上传图片失败"),
    VERIFY_ERROR(2041, "verify_error", "验证失败"),
    NOTIFY_SEND_TYPE_ERROR(9000, "notify_send_type_error", "发送提醒消息类型不正确"),
    PHONECODE_ALREADY_EXPIRE(2042, "code_already_expire", "验证码已失效");

    private int code;
    private String desc;
    private String cnDesc;

    private CodeEnums(int code, String desc, String cnDesc) {
        this.code = code;
        this.desc = desc;
        this.cnDesc = cnDesc;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCnDesc() {
        return this.cnDesc;
    }

    public void setCnDesc(String cnDesc) {
        this.cnDesc = cnDesc;
    }
}
