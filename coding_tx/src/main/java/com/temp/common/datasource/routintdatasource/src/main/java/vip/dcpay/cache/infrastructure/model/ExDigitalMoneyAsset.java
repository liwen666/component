package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.infrastructure.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 资产表
 * </p>
 *
 * @author zys
 * @since 2019-05-18
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ex_digitalmoney_asset")
public class ExDigitalMoneyAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商户Id
     */
    @TableField("accountId")
    private Long accountId;
    /**
     * 平台账户类型（1：平台；2：商家）
     */
    @TableField("accountType")
    private Integer accountType;
    /**
     * 币种名称
     */
    @TableField("coinCode")
    private String coinCode;
    /**
     * 热钱
     */
    @TableField("hotMoney")
    private BigDecimal hotMoney;
    /**
     * 冷钱
     */
    @TableField("coldMoney")
    private BigDecimal coldMoney;
    /**
     * 状态
     */
    @TableField("status")
    private Integer status;
    @TableField("createTime")
    private Date createTime;
    @TableField("modifyTime")
    private Date modifyTime;

}
