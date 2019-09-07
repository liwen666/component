package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.dcpay.cache.domain.assembler.MerchantAssetAssembler;
import vip.dcpay.cache.domain.assembler.MerchantCacheAssembler;
import vip.dcpay.cache.domain.asserts.MerchantAlterAsset;
import vip.dcpay.cache.domain.repository.IH2Repository;
import vip.dcpay.cache.infrastructure.dao.MerchantInfoCacheDao;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;
import vip.dcpay.dto.mq.AssetChangeMsgDto;
import vip.dcpay.dto.mq.mechant.MerchantAlterMsgDto;
import vip.dcpay.enums.commons.AccountTypeEnum;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.merchant.application.dto.MerchantFullDto;
import vip.dcpay.vo.basic.Result;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 商户信息更新服务
 */
@Service
public class MerchantAlterService {
    @Autowired(required = false)
    private MerchantInfoCacheDao merchantInfoCacheDao;
    @Autowired
    private IH2Repository ih2Repository;
    @Autowired
    private RemoteService remoteService;


    public void modifiMerchant(MerchantAlterMsgDto merchantAlterMsgDto) {
        //判断H2中是否由商户确定修改商户在线状态 如果存在用户做修改，不存在做添加
        MerchantInfoCache merchantInfo = ih2Repository.getMerchant(merchantAlterMsgDto.getMerchantId());
        if (null == merchantInfo) {
            //h2中没有商户 添加操作
            remoteService.addMerchantCache(merchantAlterMsgDto.getMerchantId());
        } else {
            //修改操作
            //根据消息查询商户信息
            Result<MerchantFullDto> mapResult = remoteService.getAlertMerchant(merchantAlterMsgDto.getMerchantId(), merchantAlterMsgDto.getAlterItem());
            if (!mapResult.getSuccess() || null == mapResult.getData()) {
                MyLogManager.error("====merchantService 获取到的获取商户更新信息失败 " + JSON.toJSONString(mapResult));
                return;
            }
            //判断商户是否是登陆登出操作
            boolean isLoginout = MerchantAlterAsset.isLoginOut(mapResult.getData());
            if(isLoginout){
                remoteService.deleteMerchantById(merchantAlterMsgDto.getMerchantId());
                return;
            }
            MerchantInfoCache merchantInfoCache = MerchantCacheAssembler.bulidAlterMerchant(mapResult.getData());
            //        验证商户是否有需要更新的字段
            boolean updateFlag = MerchantAlterAsset.allFieldIsNull(merchantInfoCache);
//            boolean updateFlag = MerchantAlterAsset.isNull(merchantInfoCache);
            //更新内存商户信息
            if (!updateFlag) {
                merchantInfoCache.setId(merchantAlterMsgDto.getMerchantId());
                ih2Repository.updateMerchantCacheInfo(merchantInfoCache);
            }
        }
    }

    public void modifiMerchant(List<AssetChangeMsgDto> assetChangeMsgDtos) {
        //        获取到需要更新资金的商户
        List<Long> collect = assetChangeMsgDtos.stream().filter(e -> e.getAccountType() != null).filter(e -> e.getAccountType() == AccountTypeEnum.MERCHANT.code())
                .map(AssetChangeMsgDto::getAccountId).collect(Collectors.toList());
        //更新商户缓存
        for (Long merchantId : collect) {
//            String merchantAsset=remoteService.getCoinAccontList(merchantId, AccountTypeEnum.MERCHANT.code());
            //从数据库获取商户资金信息
            String merchantAsset = remoteService.getCoinAccontList(merchantId, AccountTypeEnum.MERCHANT.code());
            MerchantInfoCache merchantInfoCache = MerchantInfoCache.builder().id(merchantId).assets(merchantAsset).build();
            ih2Repository.updateMerchantCacheInfo(merchantInfoCache);
        }
    }
    public void modifiMerchantByMsg(List<AssetChangeMsgDto> assetChangeMsgDtos) {
        //更新商户缓存
        List<AssetChangeMsgDto> collect = assetChangeMsgDtos.stream().filter(e -> e.getAccountType() == AccountTypeEnum.MERCHANT.code()).collect(Collectors.toList());
        for (AssetChangeMsgDto assetChangeMsgDto : collect) {
            String merchantAsset = MerchantAssetAssembler.conversionMsgDto(assetChangeMsgDto);
            MerchantInfoCache merchantInfoCache = MerchantInfoCache.builder().id(assetChangeMsgDto.getAccountId()).assets(merchantAsset).build();
            ih2Repository.updateMerchantCacheInfo(merchantInfoCache);
        }
    }
}
