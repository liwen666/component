package vip.dcpay.validate.sdk.check;

import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/6/18 20:48
 * @Description:
 */
public abstract class WithoutSendChecker extends BaseChecker {

    public WithoutSendChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        super(validateItemType, systemConfig, userConfig);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    @Override
    public Result sendVerifyCode(String receiver, String templateCode) {
        return Result.error("不支持发送验证码");
    }

    @Override
    public Result checkVerifyCode(CheckParam param) {
        if (checkedNum >= CHECK_ERROR_NUM) {
            return Result.error("错误次数超限");
        }

        Result result = check(param);
        if (!result.getSuccess()) {
            checkedNum++;
            return result;
        }

        return result;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    public abstract Result check(CheckParam param);

}
