package vip.dcpay.validate.sdk.check;

import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

/**
 * @Auther: liq
 * @Date: 2019/6/17 14:23
 * @Description:
 */
public abstract class BaseChecker {

    /**
     * 验证码试错次数
     */
    protected int CHECK_ERROR_NUM = 3;

    /**
     * 已试错次数
     */
    protected int checkedNum = 0;

    /**
     * 校验项类型
     */
    protected ValidateItemTypeEnum validateItemType;

    /**
     * 系统配置
     */
    protected SystemConfig systemConfig;
    /**
     * 用户配置
     */
    protected UserConfig userConfig;

    /**
     * 发送记录缓存
     */
    protected SendRecordCollection sendRecordCollection = new SendRecordCollection();
    /**
     * 检查记录缓存
     */
    protected CheckRecordCollection checkRecordCollection = new CheckRecordCollection();

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    public BaseChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        this.validateItemType = validateItemType;
        this.systemConfig = systemConfig;
        this.userConfig = userConfig;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    public abstract Result sendVerifyCode(String receiver, String templateCode);

    public abstract Result checkVerifyCode(CheckParam param);

}
