package vip.dcpay.validate.sdk.business;

import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/7/23 22:14
 * @Description:
 */
public interface LoginActor extends IActor {

    /**
     * 校验用户名密码
     *
     * @param account       账号
     * @param accountExtend 如果账号是手机，字段为国码
     * @param passwd
     * @return
     */
    Result authIdentity(String account, String accountExtend, String passwd);

    /**
     * 获取允许错误次数
     *
     * @return
     */
    Result<Integer> getErrorNum();

    /**
     * 获取校验错误次数
     *
     * @param account
     * @param accountExtend
     * @return
     */
    Result<Integer> getCheckErrorNum(String account, String accountExtend);

    /**
     * 增加校验错误次数
     *
     * @param account
     * @param accountExtend
     * @return
     */
    Result addCheckErrorNum(String account, String accountExtend);

    /**
     * 清空校验错误次数
     *
     * @param account
     * @param accountExtend
     * @return
     */
    Result cleanCheckErrorNum(String account, String accountExtend);

    /**
     * 冻结账户
     *
     * @param account
     * @param accountExtend
     * @return
     */
    Result freezeAccount(String account, String accountExtend);

}
