package com.temp.common.common.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MerchantDimensionDataDto
 * @date Aug 22, 2019 4:43:29 PM
 * @author ToniR
 * @Description: 商户每日订单统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDimensionDataDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 商户id
	 */
	private Long merchantId;

	/**
	 * 每日成交订单数量
	 */
	private Long dayOrderCount;

	/**
	 * 标记位
	 */
	private Long remark;

	/**
	 * 商家每日订单实时金额
	 */
	private BigDecimal dayAmountSum;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 维度日期
	 */
	private String dimensionDate;

}
