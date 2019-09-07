package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;

import java.util.List;

public interface ExDigitalMoneyAssetDao extends BaseMapper<ExDigitalMoneyAsset> {
   List<ExDigitalMoneyAsset> cxcache();

}
