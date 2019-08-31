package com.temp.common.common.entry;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家层级结构表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAgentDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 层级结构主键id
	 */
	private Long id;

	/**
	 * 商家ID
	 */
	private Long merchantId;

	/**
	 * 商户上级ID
	 */
	private Long parentId;

	/**
	 * 状态 0-无效 1-有效
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

}
