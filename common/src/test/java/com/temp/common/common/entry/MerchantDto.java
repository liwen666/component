package com.temp.common.common.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家基础信息表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
     * 主键ID
     */
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 商户显示ID
     */
    private String uid;
    /**
     * 登录密码更新时间
     */
    private Date pwdTime;
    /**
     * 交易密码更新时间
     */
    private Date tradePwdTime;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 商家实名
     */
    private String realname;
    /**
     * 手机认证状态 0-未认证 1-认证
     */
    private Integer phoneStatus;
    /**
     * 手机号国码
     */
    private String phoneArea;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 激活状态
     */
    private Integer activateStatus;
    /**
     * 激活费用
     */
    private BigDecimal activateFee;
    /**
     * 激活费用币种单位
     */
    private String activateFeeUnit;
    /**
     * 激活时间
     */
    private Date activateTime;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

}
