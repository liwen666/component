package com.temp.common.common.entry;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家全部信息
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantFullDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商家基础信息
	 */
	private MerchantDto basic;
	/**
	 * 商家权限信息
	 */
	private MerchantPowerDto power;

	/**
	 * 商家支付信息
	 */
	private List<PaymentDto> payments;

	/**
	 * 商家支付信息选择情况信息
	 */
	private List<MerchantPaymentChoiceDto> paymentChoices;

	/**
	 * 用户订单信息
	 */

	/**
	 * 商家设置信息
	 */
	private MerchantGrabSwitchDto grabSwitch;

	/**
	 * 商家订单统计信息
	 */

	/**
	 * 商家代理关系
	 */
	private MerchantAgentDto agent;

	/**
	 * 商家买单限制
	 */
	private MerchantBuyOrderLimitDto buyOrderLimit;

	/**
	 * 商户风控限制
	 */
	private MerchantRiskDto risk;

	/**
	 * 商户每日订单金额
	 */
	private MerchantDimensionDataDto dimension;

}
