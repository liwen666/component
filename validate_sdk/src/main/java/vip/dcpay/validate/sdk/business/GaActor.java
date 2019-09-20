package vip.dcpay.validate.sdk.business;

import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/7/27 19:23
 * @Description:
 */
public interface GaActor extends IActor {

    /**
     * 校验GA
     *
     * @param accountId   账号Id
     * @param accountType 账号类型
     * @param verifyCode
     * @return
     */
    Result check(Long accountId, Integer accountType, String verifyCode);

}
