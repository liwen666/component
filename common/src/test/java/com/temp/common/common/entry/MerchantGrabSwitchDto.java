package com.temp.common.common.entry;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家抢单开关信息表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantGrabSwitchDto implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * 商户ID
     */
    private Long merchantId;
    /**
     * 商家UID
     */
    private String uid;
    /**
     * 抢单总开关
     */
    private Integer grab;
    /**
     * 玩家充值 0-关 1-开
     */
    private Integer playerDeposit;
    /**
     * 平台提现 0-关 1-开
     */
    private Integer platformWithdraw;
    /**
     * 商家充值 0-关 1-开
     */
    private Integer merchantDeposit;
    /**
     * 商家提币 0-关 1-开
     */
    private Integer merchantWithdraw;

}
