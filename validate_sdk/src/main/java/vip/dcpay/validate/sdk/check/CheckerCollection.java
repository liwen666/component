package vip.dcpay.validate.sdk.check;


import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: liq
 * @Date: 2019/6/18 20:10
 * @Description:
 */
public class CheckerCollection {

    /**
     * 系统配置
     */
    private SystemConfig systemConfig;
    /**
     * 用户配置
     */
    private UserConfig userConfig;

    private Map<String, BaseChecker> checkerMap = new ConcurrentHashMap<>();

    public CheckerCollection(SystemConfig systemConfig, UserConfig userConfig) {
        this.systemConfig = systemConfig;
        this.userConfig = userConfig;
    }

    public BaseChecker getChecker(ValidateItemTypeEnum validateType) {

        BaseChecker baseChecker = checkerMap.get(validateType.name());
        if (null == baseChecker) {
            synchronized (this) {

                baseChecker = checkerMap.get(validateType.name());
                if (null == baseChecker) {

                    baseChecker = CheckerFactory.getChecker(validateType, systemConfig, userConfig);
                    if (null != baseChecker) {
                        checkerMap.put(validateType.name(), baseChecker);
                    }
                }
            }
        }

        return baseChecker;
    }

}
