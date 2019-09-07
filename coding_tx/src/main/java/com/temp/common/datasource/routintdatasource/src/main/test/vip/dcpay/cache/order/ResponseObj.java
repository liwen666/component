package com.temp.common.datasource.routintdatasource.src.main.test.vip.dcpay.cache.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * author lw
 * date 2019/8/30  13:54
 * discribe 测试下单接口
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObj {
      private Integer code;
      private String message;
      private OrderData data;
}
