package vip.dcpay.validate.sdk.demand;

import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * 通用校验
 *
 * @Auther: liq
 * @Date: 2019/6/24 19:18
 * @Description:
 */
public class CommonDemand extends BaseDemand {

    public CommonDemand(BusinessTypeEnum businessType, SystemConfig systemConfig, UserConfig userConfig) {
        super(businessType, systemConfig, userConfig);
    }

    @Override
    protected Result checkForGetToken() {
        return Result.success();
    }

    @Override
    protected Result checkForGetSmsVerifyCode() {
        return Result.success();
    }

    @Override
    protected Result checkForGetMailVerifyCode() {
        return Result.success();
    }

}
