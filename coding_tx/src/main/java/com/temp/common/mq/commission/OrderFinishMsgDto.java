package com.temp.common.mq.commission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinishMsgDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private Long recordId;

	/**
	 * 发起人id
	 */
	private Long customerId;

	/**
	 * 接单商户id
	 */
	private Long merchantId;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单交易额
	 */
	private BigDecimal transactionAmount;

	/**
	 * 订单收益
	 */
	private BigDecimal income;

	/**
	 * 订单类型
	 */
	private Integer orderType;

	/**
	 * 支付方式
	 */
	private String payWay;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 时间
	 */
	private Date createTime;

}
