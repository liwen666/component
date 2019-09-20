package vip.dcpay.validate.sdk.check;

import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/6/24 09:12
 * @Description:
 */
public class RealNameChecker extends WithoutSendChecker {

    public RealNameChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        super(validateItemType, systemConfig, userConfig);
    }

    @Override
    public Result check(CheckParam param) {

        if (null == userConfig || null == userConfig.getIsRealName() || !userConfig.getIsRealName()) {
            return Result.error();
        }

        return Result.success();
    }

}
