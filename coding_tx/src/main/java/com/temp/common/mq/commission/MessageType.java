package com.temp.common.mq.commission;

public enum MessageType {
	
	/** 订单创建 */
	ORDER_CREATE("订单已创建"),
	/** 订单已就绪 */
	ORDER_READY("订单已就绪"),
	/** 订单领取 */
	ORDER_FETCH("订单被领取"),
	/** 订单支付 */
	ORDER_PAY("订单已支付"),
	/** 订单投诉 */
	ORDER_COMPLAIN("订单被投诉"),
	/** 订单收款 */
	ORDER_RECV("订单已收款"),
	/** 订单完成 */
	ORDER_COMPLETE("订单已完成"),
	/** 订单取消 */
	ORDER_CANCEL("订单已取消"),
	/** 订单放弃 */
	ORDER_ABANDON("订单已放弃"),
	/** 订单终止 */
	ORDER_TERMINATION("订单已终止"),
	/** 划转资金 */
	TRANSFER_FUNDS("订单资金划转"),
	
	/**商家信息变更*/
	MERCHANT_DATA_ALTER_AFTER("商家信息有变更"),
	
	/**订单最终状态已入库*/
	ORDER_FINISH("订单最终状态已入库")

	;
	
	String desc;
	private MessageType(String desc){
		this.desc = desc;
	}
	
	public String desc(){
		return desc;
	}
}
