package vip.dcpay.validate.sdk.check;


import org.apache.commons.lang3.StringUtils;
import vip.dcpay.notify.sdk.validate.InterfaceValidator;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;

/**
 * @Auther: liq
 * @Date: 2019/6/17 14:27
 * @Description:
 */
public class SmsChecker extends NeedSendChecker {

    public SmsChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        super(validateItemType, systemConfig, userConfig);
    }

    @Override
    protected boolean checkReceiver(String receiver) {
        if (StringUtils.isBlank(receiver)) {
            return false;
        }

        String[] arr = receiver.split(" ");
        if (arr.length != 2) {
            return false;
        }

        String target = arr[1];
        String targetEx = arr[0];

        return InterfaceValidator.validatePhone(target, targetEx).getSuccess();
    }
}
