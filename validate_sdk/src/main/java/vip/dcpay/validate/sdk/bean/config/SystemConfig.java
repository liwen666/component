package vip.dcpay.validate.sdk.bean.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vip.dcpay.validate.sdk.business.GaActor;
import vip.dcpay.validate.sdk.business.LoginActor;
import vip.dcpay.validate.sdk.business.TradePwdActor;

import java.io.Serializable;
import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/15 17:50
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfig implements Serializable {

    /**
     * BusinessTypeEnum，validateItem
     */
    private Map<String, String> validateItemMap;

    /**
     * 短信默认模板
     */
    private String smsTemplateCode;

    /**
     * 邮件默认模板
     */
    private String mailTemplateCode;

    /**
     * 【登录密码】相关能力actor
     */
    private LoginActor loginActor;

    /**
     * 【交易密码】相关能力actor
     */
    private TradePwdActor tradePwdActor;

    /**
     * 【GA】相关能力Actor
     */
    private GaActor gaActor;

}
