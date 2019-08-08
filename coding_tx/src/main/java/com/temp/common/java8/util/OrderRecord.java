package com.temp.common.java8.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单记录
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_record")
public class OrderRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	private Date createTime;

	// 基础信息
	// ////////////////////////////////////////////////////////////////////////
	private String orderId;

	private Long customerId;

	private Integer customerType;

	private Long merchantId;

	private Integer orderType;

	private Integer orderStatus;
	
	private Integer complaintStatus;

	// 金额信息 ///////////////////////////////////////////////////////////////////
	private String currency;

	private Integer accuracy;

	private BigDecimal tradeAmount;// 交易金额

	private BigDecimal enoughPaymentAmount;// 足额支付金额

	private BigDecimal realPaymentAmount;// 实际支付金额

	private BigDecimal gotAmount;// 给支付方\购买方的金额

	private BigDecimal returnedAmount;// 给收款方\出售方退回的金额

	// 资金信息 ///////////////////////////////////////////////////////////////////
	private Long assetApplyId;

	// 手续费率 ///////////////////////////////////////////////////////////////////
	private BigDecimal totalFeeRate;

	private BigDecimal minFee;

	private BigDecimal baseFeeRate;

	private BigDecimal merchantRebateRate;

	private BigDecimal platformAgentFeeRate;

	// 手续费 ///////////////////////////////////////////////////////////////////
	private BigDecimal totalFee;

	private BigDecimal baseFee;

	private BigDecimal platformAgentFee;

	private BigDecimal companyIncome;

	private BigDecimal merchantIncome;

	// 支付信息 ///////////////////////////////////////////////////////////////////
	private String payPaymentAccount;

	private String payPaymentOwner;

	private String payPaymentWay;

	// 收款信息 ///////////////////////////////////////////////////////////////////
	private String revcPaymentAccount;

	private String revcPaymentOwner;

	private String revcPaymentWay;

	private String revcPaymentBelong;

	private String revcPaymentSubBelong;

	private String revcPaymentQrImg;

	private String revcPaymentQrLink;

	private String revcPaymentQrSign;

	// 第三方平台信息 /////////////////////////////////////////////////////////
	private String outerUserId;

	private String outerOrderId;

	private String outerExtra;

	// 时间信息 //////////////////////////////////////////////////////////////

	private Date orderCreateTime;

	private Date orderMatchTime;

	private Date orderPayMoneyTime;

	private Date orderRecvMoneyTime;

	private Date orderFinishTime;

	private Date orderComplaintTime;

	// 订单备注信息 ///////////////////////////////////////////////////////////////
	private String orderRemark;

}
