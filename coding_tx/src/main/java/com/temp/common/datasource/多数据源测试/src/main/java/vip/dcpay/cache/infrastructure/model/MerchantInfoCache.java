package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.infrastructure.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * author lw
 * date 2019/8/22  17:49
 * discribe  商户信息缓存数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("merchant_info_cache")
public class MerchantInfoCache {
  @TableId(value = "id", type = IdType.INPUT)
  private Long id;
  @TableField("uid")
  private String uid;
  //普通，批发商
  @TableField("type")
  private Integer type;
  @TableField("realname")
  private String realname;
  @TableField("activate_status")
  private Integer activateStatus;
  @TableField("payment_choices")
  private String paymentChoices;
  @TableField("payments")
  private String payments;
  @TableField("area_code")
  private String areaCode;
  @TableField("area_name")
  private String areaName;
  @TableField("assets")
  private String assets;
  @TableField("day_mount_sum")
  private BigDecimal dayMountSum;
  @TableField("day_order_count")
  private Long dayOrderCount;
  //商家设置信息
  /**
   * 抢单总开关
   */
  @TableField("grab")
  private Integer grab;
  /**
   * 玩家充值 0-关 1-开
   */
  @TableField("player_deposit")
  private Integer playerDeposit;
  /**
   * 平台提现 0-关 1-开
   */
  @TableField("platform_withdraw")
  private Integer platformWithdraw;
  /**
   * 商家充值 0-关 1-开
   */
  @TableField("merchant_deposit")
  private Integer merchantDeposit;
  /**
   * 商家提币 0-关 1-开
   */
  @TableField("merchant_withdraw")
  private Integer merchantWithdraw;
}
