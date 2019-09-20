package vip.dcpay.validate.sdk.demand;

import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/6/15 17:53
 * @Description:
 */
public class RegisterDemand extends BaseDemand {

    public RegisterDemand(BusinessTypeEnum businessType, SystemConfig systemConfig, UserConfig userConfig) {
        super(businessType, systemConfig, userConfig);
    }

    @Override
    protected Result checkForGetToken() {
        // TODO checkForGetToken


        return Result.success();
    }

    @Override
    protected Result checkForGetSmsVerifyCode() {
        // TODO checkForGetSmsVerifyCode

        // 校验手机号格式（是否真实手机号）

        // 是否已注册（？会被人利用套取注册用户手机号）

        // 是否黑产手机号

        // 是否注册黑名单

        // 判断使用此IP注册的用户是否有多个，是不是僵尸用户

        return Result.success();
    }

    @Override
    protected Result checkForGetMailVerifyCode() {
        // TODO checkForGetMailVerifyCode

        return Result.success();
    }

}
