package com.temp.common.common.entry;

import java.io.Serializable;
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
public class MerchantRiskDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID，同商家ID
	 */
	private Long id;

	/**
	 * 是否禁止登录  见
	 * @see vip.dcpay.enums.commons.SwitchStatusEnum
	 */
	private Integer disabled;
	
	/**
	 * 禁止登录的时间
	 */
	private Date disabledTime;
	
	/**
	 * 禁止登录的原因
	 */
	private String disabledReason;
	
}