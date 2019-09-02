package com.temp.common.java8.stream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 订单领取人的信息
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetcherDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商家ID
	 */
	private Long id;
	/**
	 * 商家UID
	 */
	private String uid;
	/**
	 * 商家类型 1-普通商家 2-批发商
	 * 
	 */
	private Integer type;

	/**
	 * 商家的实名
	 */
	private String realname;

	/**
	 * 商家的激活状态
	 * 
	 */
	private Integer activateStatus;

	/**
	 * 商家资产,可能null
	 */

	/**
	 * 商家设置的收款方式,可能null
	 */
	private List<String> recvPayways;

	private String areaCode; //城市code
	private String areaName;  //城市Name

}
