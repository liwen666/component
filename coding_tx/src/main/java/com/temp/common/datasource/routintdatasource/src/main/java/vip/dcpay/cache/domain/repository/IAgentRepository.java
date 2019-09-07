package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.repository;

import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;
import vip.dcpay.dao.db.DBTypeEnum;
import vip.dcpay.dao.db.Switch;

import java.util.List;

public interface IAgentRepository {
    @Switch(DBTypeEnum.BASE)
    List<ExDigitalMoneyAsset> listMerchantAssets(Long merchantId, int merchantCode);
}
