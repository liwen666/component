package vip.dcpay.validate.sdk.check;

import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.business.TradePwdActor;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/6/24 09:11
 * @Description:
 */
public class TradePwdChecker extends WithoutSendChecker {

    public TradePwdChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        super(validateItemType, systemConfig, userConfig);
    }

    @Override
    public Result checkVerifyCode(CheckParam checkParam) {

        TradePwdActor tradePwdActor = systemConfig.getTradePwdActor();
        if (null == tradePwdActor) {
            MyLogManager.error("没有配置资金密码验证能力systemConfig.TradePwdActor");
            return Result.error("校验失败");
        }

        // 检查试错次数
        Result checkResult = checkErrorNum(tradePwdActor);
        if (!checkResult.getSuccess()) {
            return checkResult;
        }

        Result result = check(checkParam);
        if (result.getSuccess()) {
            // 校验通过

            // 清空试错次数记录
            tradePwdActor.cleanCheckErrorNum(userConfig.getUserId(), userConfig.getUserType());

            return Result.success();
        } else {
            // 校验未通过

            // 试错次数+1
            tradePwdActor.addCheckErrorNum(userConfig.getUserId(), userConfig.getUserType());

            return result;
        }
    }

    @Override
    public Result check(CheckParam checkParam) {

        TradePwdActor tradePwdActor = systemConfig.getTradePwdActor();

        // 检查试错次数
        Result checkResult = checkErrorNum(tradePwdActor);
        if (!checkResult.getSuccess()) {
            return checkResult;
        }

        // 校验密码验证
        checkResult = tradePwdActor.authIdentity(userConfig.getUserId(), userConfig.getUserType(), checkParam.getVerifyCode());
        if (checkResult.getSuccess()) {
            return Result.success();
        } else {
            MyLogManager.warn("账户id" + userConfig.getUserId() + ",type:" + userConfig.getUserType() + "校验未通过。" + checkResult.getMessage());
            return Result.error("校验未通过");
        }
    }

    /**
     * 校验试错次数
     *
     * @param tradePwdActor
     * @return
     */
    private Result checkErrorNum(TradePwdActor tradePwdActor) {
        // 获取系统配置错误次数
        Result<Integer> errorResult = tradePwdActor.getErrorNum();
        if (null == errorResult || !errorResult.getSuccess()) {
            String msg = "账户id" + userConfig.getUserId() + ",type:" + userConfig.getUserType() + "校验资金密码获取系统配置错误次数失败。";
            if (null != errorResult) {
                msg = msg + errorResult.getMessage();
            }

            MyLogManager.error(msg);
            return Result.error("校验未通过");
        }
        int errorNum = errorResult.getData();
        MyLogManager.develop(">>>系统配置最大试错次数：" + errorNum);

        // 获取用户试错次数
        Result<Integer> checkErrorResult = tradePwdActor.getCheckErrorNum(userConfig.getUserId(), userConfig.getUserType());
        if (null == checkErrorResult || !checkErrorResult.getSuccess()) {
            String msg = "账户id" + userConfig.getUserId() + ",type:" + userConfig.getUserType() + "校验资金密码获取用户试错次数失败。";
            if (null != checkErrorResult) {
                msg = msg + checkErrorResult.getMessage();
            }

            MyLogManager.warn(msg);
            return Result.error("校验未通过");
        }
        int checkErrorNum = checkErrorResult.getData();
        MyLogManager.develop(">>>账户id" + userConfig.getUserId() + ",type:" + userConfig.getUserType() + "试错次数：" + checkErrorNum);

        // 超出试错次数
        if (checkErrorNum >= errorNum) {
            // 冻结账户
            Result freezeResult = tradePwdActor.freezeAccount(userConfig.getUserId(), userConfig.getUserType());
            if (null == freezeResult || !freezeResult.getSuccess()) {
                String msg = "账户id" + userConfig.getUserId() + ",type:" + userConfig.getUserType() + "校验资金密码试错次数超限，冻结账户操作失败。";
                if (null != freezeResult) {
                    msg = msg + freezeResult.getMessage();
                }
                MyLogManager.warn(msg);
                return Result.error("校验未通过");
            }

            // 清空试错次数
            tradePwdActor.cleanCheckErrorNum(userConfig.getUserId(), userConfig.getUserType());

            String msg = "账户id" + userConfig.getUserId() + ",type:" + userConfig.getUserType() + "尝试验证密码错误次数超过最大" + errorNum + "次";
            MyLogManager.warn(msg);
            return Result.error("校验未通过");
        }

        return Result.success();
    }

}
