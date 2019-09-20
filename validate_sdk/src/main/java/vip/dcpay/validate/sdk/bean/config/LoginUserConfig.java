package vip.dcpay.validate.sdk.bean.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther: liq
 * @Date: 2019/6/16 23:38
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserConfig extends UserConfig {

    /**
     * 是否禁用
     */
    private Boolean isDisable;



}
