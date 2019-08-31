package com.temp.common.common.entry;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家选择的支付信息
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantPaymentChoiceDto implements Serializable {
	
    private static final long serialVersionUID = 1L;

	private Long paymentId;
	
	private Long merchantId;
	
	private Integer timesCount;
	
	private String payWay;
	
	private Date createTime;

	private Date modifyTime;
}
