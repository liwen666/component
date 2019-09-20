package vip.dcpay.validate.sdk.bean.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/17 02:58
 * @Description:
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConfig implements Serializable {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户Uid
     */
    private String userUid;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 是否开启短信验证
     */
    private Boolean isOpenSmsValidate;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 是否开启邮件验证
     */
    private Boolean isOpenMailValidate;

    /**
     * 是否开启GA验证
     */
    private Boolean isOpenGaValidate;

    /**
     * 是否实名
     */
    private Boolean isRealName;

    /**
     * 是否设置交易密码
     */
    private Boolean isSetTradePwd;

    /**
     * 业务校验传递参数
     */
    Map<String, String> param;

}
