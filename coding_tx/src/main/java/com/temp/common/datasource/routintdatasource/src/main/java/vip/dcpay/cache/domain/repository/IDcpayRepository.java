package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.repository;

import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;

import java.util.List;

public interface IDcpayRepository {

        List<ExDigitalMoneyAsset> listAssets(Long id, int code);
}
