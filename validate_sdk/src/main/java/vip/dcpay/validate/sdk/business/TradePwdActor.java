package vip.dcpay.validate.sdk.business;

import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/7/27 19:24
 * @Description:
 */
public interface TradePwdActor extends IActor {

    /**
     * 校验资金密码
     *
     * @param accountId   账号Id
     * @param accountType 账号类型
     * @param tradePwd    资金密码
     * @return
     */
    Result authIdentity(Long accountId, Integer accountType, String tradePwd);

    /**
     * 获取允许错误次数
     *
     * @return
     */
    Result<Integer> getErrorNum();

    /**
     * 获取校验错误次数
     *
     * @param accountId   账号Id
     * @param accountType 账号类型
     * @return
     */
    Result<Integer> getCheckErrorNum(Long accountId, Integer accountType);

    /**
     * 增加校验错误次数
     *
     * @param accountId   账号Id
     * @param accountType 账号类型
     * @return
     */
    Result addCheckErrorNum(Long accountId, Integer accountType);

    /**
     * 清空校验错误次数
     *
     * @param accountId   账号Id
     * @param accountType 账号类型
     * @return
     */
    Result cleanCheckErrorNum(Long accountId, Integer accountType);

    /**
     * 冻结账户
     *
     * @param accountId   账号Id
     * @param accountType 账号类型
     * @return
     */
    Result freezeAccount(Long accountId, Integer accountType);

}
