package vip.dcpay.validate.sdk.check;

import com.alibaba.fastjson.JSON;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.business.LoginActor;
import vip.dcpay.validate.sdk.business.ParamConstant;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/17 21:29
 * @Description:
 */
public class LoginPwdChecker extends WithoutSendChecker {


    public LoginPwdChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        super(validateItemType, systemConfig, userConfig);
    }

    @Override
    public Result checkVerifyCode(CheckParam checkParam) {

        LoginActor loginActor = systemConfig.getLoginActor();
        if (null == loginActor) {
            MyLogManager.error("没有配置登录密码验证能力systemConfig.LoginActor");
            return Result.error("校验失败");
        }

        Map<String, String> param = userConfig.getParam();
        if (param.isEmpty()) {
            MyLogManager.error("未配置业务参数userConfig.Param");
            return Result.error("校验失败");
        }

        String account = param.get(ParamConstant.ACCOUNT);
        String accountExtend = param.get(ParamConstant.ACCOUNT_EXTEND);

        Result checkResult = checkErrorNum(loginActor, account, accountExtend);
        if (!checkResult.getSuccess()) {
            return checkResult;
        }

        if (!checkResult.getSuccess()) {
            return checkResult;
        }

        Result result = check(checkParam);
        if (result.getSuccess()) {
            // 校验通过

            // 清空试错次数记录
            loginActor.cleanCheckErrorNum(account, accountExtend);

            return Result.success();
        } else {
            // 校验未通过

            // 试错次数+1
            loginActor.addCheckErrorNum(account, accountExtend);

            return result;
        }
    }

    @Override
    public Result check(CheckParam checkParam) {

        LoginActor loginActor = systemConfig.getLoginActor();

        Map<String, String> param = userConfig.getParam();
        String account = param.get(ParamConstant.ACCOUNT);
        String accountExtend = param.get(ParamConstant.ACCOUNT_EXTEND);
        String passwd = param.get(ParamConstant.PASSWORD);

        MyLogManager.develop("登录校验账户：account -> " + account + "| accountExtend -> " + accountExtend);
        MyLogManager.develop("userConfig：" + JSON.toJSONString(userConfig));

        // 校验密码验证
        Result checkResult = loginActor.authIdentity(account, accountExtend, passwd);
        if (checkResult.getSuccess()) {
            return Result.success();
        } else {
            MyLogManager.warn("账户" + account + "校验未通过。" + checkResult.getMessage());
            return Result.error("账户" + account + "校验未通过。");
        }
    }

    /**
     * 校验试错次数
     *
     * @param loginActor
     * @param account
     * @param accountExtend
     * @return
     */
    private Result checkErrorNum(LoginActor loginActor, String account, String accountExtend) {
        // 获取系统配置错误次数
        Result<Integer> errorResult = loginActor.getErrorNum();
        if (null == errorResult || !errorResult.getSuccess()) {
            String msg = "登录验证获取系统配置错误次数失败。";
            if (null != errorResult) {
                msg = msg + errorResult.getMessage();
            }

            MyLogManager.error("账户" + account + msg);
            return Result.error("账户" + account + "校验失败");
        }
        int errorNum = errorResult.getData();
        MyLogManager.develop(">>>系统配置最大试错次数：" + errorNum);

        // 获取用户试错次数
        Result<Integer> checkErrorResult = loginActor.getCheckErrorNum(account, accountExtend);
        if (null == checkErrorResult || !checkErrorResult.getSuccess()) {
            String msg = "登录验证获取用户试错次数失败。";
            if (null != checkErrorResult) {
                msg = msg + checkErrorResult.getMessage();
            }

            MyLogManager.warn("账户" + account + msg);
            return Result.error("账户" + account + "校验失败");
        }
        int checkErrorNum = checkErrorResult.getData();
        MyLogManager.develop(">>>用户" + account + "试错次数：" + checkErrorNum);

        // 超出试错次数
        if (checkErrorNum >= errorNum) {
            // 冻结账户
            Result freezeResult = loginActor.freezeAccount(account, accountExtend);
            if (null == freezeResult || !freezeResult.getSuccess()) {
                String msg = "账户" + account + "登录验证试错次数超限，冻结账户操作失败。";
                if (null != freezeResult) {
                    msg = msg + freezeResult.getMessage();
                }
                MyLogManager.error(msg);
                return Result.error("账户" + account + "校验失败");
            }

            // 清空试错次数
            loginActor.cleanCheckErrorNum(account, accountExtend);

            String msg = "账户" + account + "尝试验证密码错误次数超过最大" + errorNum + "次";
            MyLogManager.warn(msg);
            return Result.error("账户" + account + "校验失败");
        }

        return Result.success();
    }

}
