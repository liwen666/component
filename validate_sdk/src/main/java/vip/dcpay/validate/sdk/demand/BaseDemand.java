package vip.dcpay.validate.sdk.demand;

import org.apache.commons.lang3.StringUtils;
import vip.dcpay.dto.account.AccountInfo;
import vip.dcpay.enums.commons.AccountTypeEnum;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.util.date.DateUtils;
import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.ValidateInfo;
import vip.dcpay.validate.sdk.bean.VerifyTypeResponse;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.check.BaseChecker;
import vip.dcpay.validate.sdk.check.CheckerCollection;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;
import vip.dcpay.validate.sdk.enums.ReturnCodeEnum;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.validate.sdk.util.SnowflakeIdWorker;
import vip.dcpay.validate.sdk.util.ValidateItemUtil;
import vip.dcpay.vo.basic.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/15 17:52
 * @Description:
 */
public abstract class BaseDemand {

    private SnowflakeIdWorker snowflakeIdWorker = SnowflakeIdWorker.instance();

    /**
     * 创建时间
     */
    protected Date CREATE_TIME = new Date();
    /**
     * token有效期（默认 5 分钟）
     */
    protected Long TOKEN_EXPIRE = 60 * 5L;

    /**
     * 系统配置
     */
    protected SystemConfig systemConfig;
    /**
     * 用户配置
     */
    protected UserConfig userConfig;
    /**
     * 业务类型
     */
    protected BusinessTypeEnum businessType;

    /**
     * 校验相关信息
     */
    protected ValidateInfo validateInfo = null;
    /**
     * 检查者缓存
     */
    protected CheckerCollection checkerCollection = null;


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    /**
     * 构造
     *
     * @param systemConfig
     * @param userConfig
     */
    public BaseDemand(BusinessTypeEnum businessType, SystemConfig systemConfig, UserConfig userConfig) {
        this.businessType = businessType;
        this.systemConfig = systemConfig;
        this.userConfig = userConfig;

        checkerCollection = new CheckerCollection(this.systemConfig, this.userConfig);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    /**
     * 获取校验项
     *
     * @return
     */
    public Result<VerifyTypeResponse> getValidateItems() {
        if (null != validateInfo) {
            if (!checkExpire()) {
                // 进行中，直接返回缓存结果
                return Result.success(assemble(validateInfo));
            } else {
                return Result.error(ReturnCodeEnum.TIMEOUT.code(), ReturnCodeEnum.TIMEOUT.desc());
            }
        }

        Result result = checkForGetToken();
        if (!result.getSuccess()) {
            return result;
        }

        result = getValidateInfo();
        if (!result.getSuccess()) {
            return result;
        }

        // 缓存 ValidateInfo
        validateInfo = (ValidateInfo) result.getData();

        return Result.success(assemble(validateInfo));
    }

    /**
     * 设置校验结果
     *
     * @param verifyCodes
     * @return
     */
    public synchronized Result<Map<String, String>> setValidateItems(Map<String, String> verifyCodes) {
        if (null == validateInfo) {
            return Result.error(ReturnCodeEnum.INVALID.code(), ReturnCodeEnum.INVALID.desc());
        } else if (checkExpire()) {
            return Result.error(ReturnCodeEnum.TIMEOUT.code(), ReturnCodeEnum.TIMEOUT.desc());
        }

        Result checkResult;

        // 按照设置内容，调用相应校验方法，获得单项校验结果
        Map<String, String> checkErrorMap = new HashMap<>();
        for (Map.Entry<String, String> entry : verifyCodes.entrySet()) {

            ValidateItemTypeEnum validateType = ValidateItemTypeEnum.getEnumByName(entry.getKey());
            BaseChecker checker = checkerCollection.getChecker(validateType);
            if (null != checker) {
                // 已知类型校验
                checkResult = checker.checkVerifyCode(CheckParam.builder()
                        .verifyCode(entry.getValue())
                        .param(userConfig.getParam())
                        .build());
                if (!checkResult.getSuccess()) {
                    // 检查未通过
                    checkErrorMap.put(entry.getKey(), checkResult.getMessage());
                }

                validateInfo.getValidateItemCheckedFlags().put(validateType.code(), checkResult.getSuccess() ? 1 : 0);
            } else {

                String errorMsg = "未配置相应类型的检查类。validateType：" + validateType;
                MyLogManager.error(errorMsg);
                // 检查未通过
                checkErrorMap.put(entry.getKey(), errorMsg);

                validateInfo.getValidateItemCheckedFlags().put(validateType.code(), 0);
            }

        }

        if (checkErrorMap.size() > 0) {
            return Result.error(ReturnCodeEnum.FAILED.code(), "存在未校验通过项", checkErrorMap);
        } else {
            // 获取未校验通过项
            Map<String, String> unCheck = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry : validateInfo.getValidateItemCheckedFlags().entrySet()) {
                if (entry.getValue().equals(0)) {
                    unCheck.put(ValidateItemTypeEnum.getEnum(entry.getKey()).name(), String.valueOf(entry.getValue()));
                }
            }

            return Result.success(unCheck);
        }
    }

    /**
     * 提交校验结果
     *
     * @return
     */
    public Result submitValidate() {
        if (null == validateInfo) {
            return Result.error(ReturnCodeEnum.INVALID.code(), ReturnCodeEnum.INVALID.desc());
        } else if (checkExpire()) {
            return Result.error(ReturnCodeEnum.TIMEOUT.code(), ReturnCodeEnum.TIMEOUT.desc());
        }

        // 比对校验结果，如果全部完成并通过，返回true
        boolean flag = ValidateItemUtil.checkValidateItems(validateInfo.getValidateItems(), validateInfo.getValidateItemCheckedFlags());

        // 验证成功后清理
        if (flag) {
            validateInfo = null;
        }

        return flag ? Result.success() : Result.error("校验未通过");
    }

    /**
     * 发送验证码
     *
     * @param receiver
     * @return
     */
    public Result sendVerifyCode(ValidateItemTypeEnum validateItemType, String receiver, String templateCode) {
        if (null == validateInfo) {
            return Result.error(ReturnCodeEnum.INVALID.code(), ReturnCodeEnum.INVALID.desc());
        } else if (checkExpire()) {
            return Result.error(ReturnCodeEnum.TIMEOUT.code(), ReturnCodeEnum.TIMEOUT.desc());
        }

        if (!needSendCode(validateItemType)) {
            return Result.error("本次验证，不需要获取" + validateItemType.name() + "验证码");
        }

        Result result = null;
        if (ValidateItemTypeEnum.SMS.equals(validateItemType)) {
            result = checkForGetSmsVerifyCode();
        } else if (ValidateItemTypeEnum.MAIL.equals(validateItemType)) {
            result = checkForGetMailVerifyCode();
        }

        if (null == result) {
            return Result.error("不支持该类型的验证码发送。validateItemType：" + validateItemType);
        } else if (!result.getSuccess()) {
            return result;
        }

        BaseChecker checker = checkerCollection.getChecker(validateItemType);
        if (null == checker) {
            String errorMsg = "未配置相应类型的检查类。validateItemType：" + validateItemType;
            MyLogManager.error(errorMsg);

            return Result.error(errorMsg);
        }

        // 发送验证码
        return checker.sendVerifyCode(receiver, templateCode);
    }

    /**
     * 检查token是否失效
     *
     * @return
     */
    public boolean checkExpire() {
        Date curDate = new Date();
        long intervalTime = curDate.getTime() - CREATE_TIME.getTime() - TOKEN_EXPIRE * 1000;

        return intervalTime > 0;
    }

    /**
     * 获取token关联账户
     *
     * @return
     */
    public AccountInfo getAccountInfo() {
        if (null == userConfig) {
            return null;
        }

        return AccountInfo.builder().id(userConfig.getUserId()).type(AccountTypeEnum.getEnum(userConfig.getUserType())).build();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    /**
     * 获取token前，风控校验
     *
     * @return
     */
    protected abstract Result checkForGetToken();

    /**
     * 获取短信验证码前，风控校验
     *
     * @return
     */
    protected abstract Result checkForGetSmsVerifyCode();

    /**
     * 获取邮件验证码前，风控校验
     *
     * @return
     */
    protected abstract Result checkForGetMailVerifyCode();

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    private Map<Integer, String> getCustomerItemStates() {
        Map<Integer, String> itemStateMap = new HashMap<>();

        if (null == userConfig) {
            return itemStateMap;
        }

        for (ValidateItemTypeEnum c : ValidateItemTypeEnum.values()) {
            switch (c) {
                case SMS:
                    if (null != userConfig.getIsOpenSmsValidate() && userConfig.getIsOpenSmsValidate()
                            && StringUtils.isNoneBlank(userConfig.getPhoneNumber())) {
                        itemStateMap.put(c.code(), "1");
                    }
                    break;
                case MAIL:
                    if (null != userConfig.getIsOpenMailValidate() && userConfig.getIsOpenMailValidate()
                            && StringUtils.isNoneBlank(userConfig.getMail())) {
                        itemStateMap.put(c.code(), "1");
                    }
                    break;
                case GA:
                    if (null != userConfig.getIsOpenGaValidate() && userConfig.getIsOpenGaValidate()) {
                        itemStateMap.put(c.code(), "1");
                    }
                    break;
                case REAL_NAME:
                    if (null != userConfig.getIsRealName() && userConfig.getIsRealName()) {
                        itemStateMap.put(c.code(), "1");
                    }
                    break;
                case TRADE_PWD:
                    if (null != userConfig.getIsSetTradePwd() && userConfig.getIsSetTradePwd()) {
                        itemStateMap.put(c.code(), "1");
                    }
                    break;
            }
        }

        return itemStateMap;
    }

    /**
     * 获取 ValidateInfo
     *
     * @return
     */
    private Result<ValidateInfo> getValidateInfo() {

        Map<Integer, String> selfItemMap = getCustomerItemStates();
//        if (null == selfItemMap || selfItemMap.size() < 1) {
//            return Result.error(ReturnCodeEnum.GET_USER_CONFIG_FAILED.code(), ReturnCodeEnum.GET_USER_CONFIG_FAILED.desc());
//        }

        if (null == systemConfig.getValidateItemMap()) {
            return Result.error(ReturnCodeEnum.GET_SYSTEM_CONFIG_FAILED.code(), ReturnCodeEnum.GET_SYSTEM_CONFIG_FAILED.desc());
        }

        String validateItems = systemConfig.getValidateItemMap().get(businessType.code());
        if (StringUtils.isBlank(validateItems)) {
            return Result.error(ReturnCodeEnum.GET_SYSTEM_CONFIG_FAILED.code(), ReturnCodeEnum.GET_SYSTEM_CONFIG_FAILED.desc());
        }

        String selfItem = ValidateItemUtil.builderValidateItem(selfItemMap);

        String items;
        if (null == selfItemMap || selfItemMap.size() < 1) {
            items = validateItems;
        } else {
            Result<String> result = ValidateItemUtil.getValidateItems(validateItems, selfItem);
            if (!result.getSuccess()) {
                return Result.convert(result);
            }
            items = result.getData();
        }

        // 生成token
        String token = String.valueOf(snowflakeIdWorker.nextId());

        ValidateInfo validateInfo = ValidateInfo.builder()
                .token(token)
                .validateItems(items)
                .businessType(businessType)
                .createTime(CREATE_TIME)
                .deadline(DateUtils.addSecond(CREATE_TIME, TOKEN_EXPIRE.intValue()))
                .build();

        if (null != userConfig) {
            validateInfo.setUserConfig(userConfig);
        }

        return Result.success(validateInfo);
    }

    /**
     * 组装VerifyTypeResponse
     *
     * @param validateInfo
     * @return
     */
    private VerifyTypeResponse assemble(ValidateInfo validateInfo) {
        if (null == validateInfo) {
            return null;
        }

        return VerifyTypeResponse.builder()
                .validateToken(this.validateInfo.getToken())
                .validateItems(ValidateItemUtil.convertItems(this.validateInfo.getValidateItems()))
                .createTime(validateInfo.getCreateTime())
                .deadline(validateInfo.getDeadline())
                .build();
    }

    private boolean needSendCode(ValidateItemTypeEnum validateItemType) {
        boolean flag = false;

        int size = ValidateItemTypeEnum.values().length;
        int index = size - 1 - validateItemType.code();

        String[] items = validateInfo.getValidateItems().split(",");
        for (String item : items) {
            flag = ValidateItemUtil.addZeroForNum(item, size).charAt(index) == '1';

            if (flag) {
                break;
            }
        }

        return flag;
    }

}
