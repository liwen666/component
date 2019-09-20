package vip.dcpay.validate.sdk.demand;


import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;

/**
 * @Auther: liq
 * @Date: 2019/6/17 01:22
 * @Description:
 */
public class DemandFactory {

    public static BaseDemand getDemand(BusinessTypeEnum businessType, SystemConfig systemConfig, UserConfig userConfig) {

        if (null == businessType) {
            return null;
        }

        switch (businessType) {
            case LOGIN:
                return new LoginDemand(businessType, systemConfig, userConfig);
            case REGISTER:
                return new RegisterDemand(businessType, systemConfig, userConfig);
            default:
                return new CommonDemand(businessType, systemConfig, userConfig);
        }

    }

}
