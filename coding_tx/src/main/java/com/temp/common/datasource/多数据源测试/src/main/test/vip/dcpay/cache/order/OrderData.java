package com.temp.common.datasource.多数据源测试.src.main.test.vip.dcpay.cache.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * author lw
 * date 2019/8/30  13:57
 * discribe 分装返回结果
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderData {
    private String orderId;
    private Integer orderType;
    private String paymentUrl;
    private String signature;

    //查询参数
    private BigDecimal amount;
    private Long expireTime;
    private String orderNo;
    private Object qrLink;
    private String payWay;
    private String payUrl;
    private int payStatus;
    private String bizOrderNo;
    private Object qrImg;
}
