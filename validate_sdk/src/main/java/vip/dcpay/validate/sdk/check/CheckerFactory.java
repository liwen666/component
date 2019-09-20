package vip.dcpay.validate.sdk.check;


import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;

/**
 * @Auther: liq
 * @Date: 2019/6/17 18:50
 * @Description:
 */
public class CheckerFactory {

    public static BaseChecker getChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        if (null == validateItemType) {
            return null;
        }

        switch (validateItemType) {
            case SMS:
                return new SmsChecker(validateItemType, systemConfig, userConfig);
            case MAIL:
                return new MailChecker(validateItemType, systemConfig, userConfig);
            case GA:
                return new GaChecker(validateItemType, systemConfig, userConfig);
            case REAL_NAME:
                return new RealNameChecker(validateItemType, systemConfig, userConfig);
            case TRADE_PWD:
                return new TradePwdChecker(validateItemType, systemConfig, userConfig);
            case LOGIN_PWD:
                return new LoginPwdChecker(validateItemType, systemConfig, userConfig);
            default:
                return null;
        }
    }

}
