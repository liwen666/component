package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.infrastructure.repository;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vip.dcpay.cache.domain.repository.IAgentRepository;
import vip.dcpay.cache.infrastructure.dao.ExDigitalMoneyAssetDao;
import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;
import vip.dcpay.dao.db.DBTypeEnum;
import vip.dcpay.dao.db.Switch;

import java.util.List;

@Repository
public class AgentRepository implements IAgentRepository {

    @Autowired
    private ExDigitalMoneyAssetDao exDigitalMoneyAssetDao;
    @Switch(DBTypeEnum.BASE)
    @Override
    public List<ExDigitalMoneyAsset> listMerchantAssets(Long merchantId, int merchantCode) {
       return exDigitalMoneyAssetDao.selectList(Wrappers.<ExDigitalMoneyAsset>lambdaQuery().eq(ExDigitalMoneyAsset::getAccountId,merchantId).eq(ExDigitalMoneyAsset::getAccountType,merchantCode));
    }

    @Switch(DBTypeEnum.BASE)
    public List<ExDigitalMoneyAsset> listMerchantAssetsByMapper() {
        return exDigitalMoneyAssetDao.cxcache();
    }


//    @Switch(DBTypeEnum.BASE_SLAVE)
//    @Override
//    public List<MerchantDayTradePojo> getMerchantDayTradeByParams(String startTime, String endTime, Integer orderType, String payWay){
//
//        return orderRecordDao.getMerchantDayTradeByParams(startTime,endTime,orderType,payWay);
//
//    }


}
