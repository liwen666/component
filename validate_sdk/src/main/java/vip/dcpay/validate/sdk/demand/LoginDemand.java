package vip.dcpay.validate.sdk.demand;

import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.check.BaseChecker;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/6/15 17:53
 * @Description:
 */
public class LoginDemand extends BaseDemand {

    public LoginDemand(BusinessTypeEnum businessType, SystemConfig systemConfig, UserConfig userConfig) {
        super(businessType, systemConfig, userConfig);
    }

    @Override
    protected Result checkForGetToken() {

        // 登录密码校验
        ValidateItemTypeEnum validateItemType = ValidateItemTypeEnum.LOGIN_PWD;
        BaseChecker checker = checkerCollection.getChecker(validateItemType);
        if (null == checker) {
            String errorMsg = "未配置相应类型的检查类。validateItemType：" + validateItemType;
            MyLogManager.error(errorMsg);

            return Result.error(errorMsg);
        }

        Result checkResult = checker.checkVerifyCode(CheckParam.builder()
                .param(userConfig.getParam())
                .build());

        return checkResult;


        // 判断是否开启商家登录

        // 检查用户名是否存在

        // 用户名、密码是否正确

        // 密码如果错误，今日错误次数是否超限

        // 判断此用户是否被禁用

    }

    @Override
    protected Result checkForGetSmsVerifyCode() {
        // TODO checkForGetSmsVerifyCode

        return Result.success();
    }

    @Override
    protected Result checkForGetMailVerifyCode() {
        // TODO checkForGetMailVerifyCode

        return Result.success();
    }

}
