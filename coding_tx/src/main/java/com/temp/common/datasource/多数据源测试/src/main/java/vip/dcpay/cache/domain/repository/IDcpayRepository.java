package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.repository;

import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;

import java.util.List;

public interface IDcpayRepository {

        List<ExDigitalMoneyAsset> listAssets(Long id, int code);
}
