package com.temp.common.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class PlatformAccountInfoVo implements Serializable {


    private Long id;
    /**
     * 平台ID
     */
    private Long platformId;
    /**
     * 登陆账号
     */
    private String username;
    /**
     * 商户昵称
     */
    private String nickname;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 修改登录密码的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pwdTime;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 手机区号
     */
    private String phoneArea;
    /**
     * 手机认证状态 0未绑定 1 已绑定
     */
    private Integer phoneStatus;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱绑定状态
     */
    private Integer emailStatus;
    /**
     * usbKey
     */
    private String usbKey;

    /**
     * 谷歌认证状态 0 未认证 1 已认证
     */
    private Integer googleStatus;

    /**
     * 修改资金密码的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tradePwdTime;
    /**
     * 交易状态 0禁用 1 可用
     */
    private Integer tradeStatus;
    /**
     * 账号可用状态 0 禁用 1 可用
     */
    private Integer enableStatus;
    /**
     * 区号加手机号
     */
    private String fullPhone;

    /**
     * 是否设置资金密码
     */
    private Integer tradePwdStatus;

    /**
     * 第三方平台公钥
     */
    private String thirdKey;
    /**
     * 是否设置私钥
     */
    private Integer apiKeyStatus;

    private String uid;
    /**
     * 创建时间
     * @return
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getFullPhone() {
        return phoneArea + " " + phone;
    }

    public void setFullPhone(String fullPhone) {
        this.fullPhone = fullPhone;
    }
}
