package com.temp.common.common.entry;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家的权限能力
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantPowerDto implements Serializable{

	
    private static final long serialVersionUID = 1L;
	
    /**
     * 同 商家ID
     */
	private Long id;
    
    /**
     * 商家类型 MerchantTypeEnum
     */
    private Integer type;
    
    /**
     * 领取权限的开关 SwitchStatusEnum
     */
    private Integer canFetch;

    /**
     * 提现权限的开关 SwitchStatusEnum
     */
    private Integer canOutCash;

}
