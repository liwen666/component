package vip.dcpay.validate.sdk.bean.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther: liq
 * @Date: 2019/6/19 21:20
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserConfig extends UserConfig {

    /**
     * 邀请人信息
     */
    private InviterInfo inviter;

}
