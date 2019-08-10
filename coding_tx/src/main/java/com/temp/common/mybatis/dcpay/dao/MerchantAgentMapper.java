package com.temp.common.mybatis.dcpay.dao;

import com.temp.common.mybatis.dcpay.domain.MerchantAgent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商家层级结构表 Mapper 接口
 * </p>
 *
 * @author tx
 * @since 2019-08-10
 */
public interface MerchantAgentMapper extends BaseMapper<MerchantAgent> {

    MerchantAgent selectMy();
}
