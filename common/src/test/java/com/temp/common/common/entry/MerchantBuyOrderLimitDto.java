package com.temp.common.common.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MerchantBuyOrderLimitDto
 * @date Jul 27, 2019 2:47:29 PM
 * @author ToniR
 * @Description: 商户买单限制
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantBuyOrderLimitDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 商户id
	 */
	private Long merchantId;

	/**
	 * 卖单增长限制
	 */
	private BigDecimal increaseLimit;

	/**
	 * 订单限制
	 */
	private BigDecimal orderLimit;

	/**
	 * 累计接单交易量
	 */
	private BigDecimal acceptTotal;

	/**
	 * 累计买币量
	 */
	private BigDecimal buyTotal;

	private Date createTime;

	private Date modifyTime;

}
