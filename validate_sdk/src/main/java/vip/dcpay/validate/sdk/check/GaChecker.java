package vip.dcpay.validate.sdk.check;

import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.business.GaActor;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/6/17 14:31
 * @Description:
 */
public class GaChecker extends WithoutSendChecker {

    public GaChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        super(validateItemType, systemConfig, userConfig);
    }

    @Override
    public Result check(CheckParam checkParam) {

        GaActor gaActor = systemConfig.getGaActor();

        // 校验密码验证
        Result result = gaActor.check(userConfig.getUserId(), userConfig.getUserType(), checkParam.getVerifyCode());
        if (result.getSuccess()) {
            return Result.success();
        } else {
            MyLogManager.warn(userConfig.getUserId() + "校验未通过。" + result.getMessage());
            return Result.error(userConfig.getUserId() + "校验未通过");
        }

    }

}
