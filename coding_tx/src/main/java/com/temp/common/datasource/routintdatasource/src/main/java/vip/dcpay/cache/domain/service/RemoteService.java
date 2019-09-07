package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.dcpay.cache.aop.annotation.ScheduleCacheLog;
import vip.dcpay.cache.domain.assembler.MerchantAssetAssembler;
import vip.dcpay.cache.domain.assembler.MerchantCacheAssembler;
import vip.dcpay.cache.domain.constant.MerchantCacheConstant;
import vip.dcpay.cache.domain.repository.IDcpayRepository;
import vip.dcpay.cache.domain.repository.IH2Repository;
import vip.dcpay.cache.domain.repository.IMerchantCacheRedisRepository;
import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;
import vip.dcpay.cache.util.AssertUtils;
import vip.dcpay.cache.util.StringUtils;
import vip.dcpay.dto.assets.AssetInfo;
import vip.dcpay.enums.commons.AccountTypeEnum;
import vip.dcpay.enums.merchant.MerchantAlterItemEnum;
import vip.dcpay.fund.FundSearchService;
import vip.dcpay.fund.dto.CoinAccount;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.merchant.application.dto.MerchantFullDto;
import vip.dcpay.merchant.application.service.merchant.MerchantService;
import vip.dcpay.vo.basic.Page;
import vip.dcpay.vo.basic.Pagination;
import vip.dcpay.vo.basic.Result;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * 商户信息更新服务
 */
@Service
public class RemoteService {

    @Reference
    MerchantService merchantService;
    @Autowired
    private IH2Repository ih2Repository;

    @Reference
    private FundSearchService fundSearchService;

    @Autowired
    private IDcpayRepository iDcpayRepository;
    @Autowired
    private IMerchantCacheRedisRepository merchantCacheRedisRepository;



    @ScheduleCacheLog(description = "初始化商户信息")
    public long initMerchant() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(10);
        MyLogManager.develop("=====正在进行商户列表更新=====");
        merchantCacheRedisRepository.deleteMerchantLock();
        int del = ih2Repository.deleteMerchantCacheList();
        long count = 0;
        int pageSize = 100;
//        //  分页查询所有商户信息
        long queryStart = System.currentTimeMillis();
        Result<Page<MerchantFullDto>> pageFullMerchant = merchantService.getPageFetch(1, pageSize);
        if(!pageFullMerchant.getSuccess()){
            MyLogManager.develop("=====merchantService,getPageFetch查询商户失败！"+"分页参数 ： pageNum[" +1+ "] pageSize ["+pageSize+"]");
        }
        if(pageFullMerchant.getData()==null||pageFullMerchant.getData().getContent().size()==0){
            MyLogManager.develop("=====merchantService,getPageFetch未查询到商户信息"+"分页参数 ： pageNum[" +1+ "] pageSize ["+pageSize+"]");
        }
        MyLogManager.develop("=====merchantService,getPageFetch查询商户耗时"+(System.currentTimeMillis()-queryStart) +" 获取到商户："+pageFullMerchant.getData().getContent().size()+"页数:"+ 1);
        Pagination pagination = pageFullMerchant.getData().getPagination();
        MyLogManager.develop("===merchantService返回的分页数据："+JSON.toJSONString(pagination));
        count = pagination.getTotal();
        List<MerchantFullDto> content = pageFullMerchant.getData().getContent();
        //将数据添加到缓存H2中
        cachedThreadPool.execute(()->{
            try {
                semaphore.acquire();
                addMerchantCache(content);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        int nextPage = getNextPage(pagination);
        while (nextPage != 0) {
            long queryStart1 = System.currentTimeMillis();
            Result<Page<MerchantFullDto>> pageFullMerchant1 = merchantService.getPageFetch(nextPage, pageSize);
            if(!pageFullMerchant1.getSuccess()){
                MyLogManager.develop("=====merchantService,getPageFetch查询商户失败！"+"分页参数 ： pageNum[" +nextPage+ "] pageSize ["+pageSize+"]");
            }
            if(pageFullMerchant1.getData()==null||pageFullMerchant1.getData().getContent().size()==0){
                MyLogManager.develop("=====merchantService,getPageFetch未查询到商户信息"+"分页参数 ： pageNum[" +nextPage+ "] pageSize ["+pageSize+"]");
            }
            MyLogManager.develop("=====merchantService,getPageFetch查询商户耗时"+(System.currentTimeMillis()-queryStart1)+"获取到商户："+pageFullMerchant1.getData().getContent().size()+"页数："+nextPage);
            Pagination pagination1 = pageFullMerchant1.getData().getPagination();
            MyLogManager.develop("===merchantService返回的分页数据："+JSON.toJSONString(pagination1));
            count = pagination1.getTotal();
            List<MerchantFullDto> content1 = pageFullMerchant1.getData().getContent();

            cachedThreadPool.execute(()->{
                try {
                    semaphore.acquire();
                    addMerchantCache(content1);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });

            //将商户信息缓存到H2中
            nextPage = getNextPage(pagination1);
        }
        cachedThreadPool.shutdown();
        while(true){
            if(cachedThreadPool.isTerminated()){
                System.out.println("商户信息更新完成！");
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //清除锁
        long lock = merchantCacheRedisRepository.deleteMerchantLock();
        MyLogManager.develop(lock==1?"商户列表更新完成，清除锁成功":"商户列表更新完成，清除锁失败");
        return count;
    }

    private void addMerchantCache(List<MerchantFullDto> content) {
        for (MerchantFullDto merchantFullDto : content) {
            boolean flag = verifyMerchantFullDto(merchantFullDto);
            if (!flag) {
                continue;
            }
            //添加redis锁操作
            if (merchantFullDto.getBasic() != null && merchantCacheRedisRepository.lockMerchant(merchantFullDto.getBasic().getId())) {
                //构建商户缓存基本信息
                MerchantInfoCache merchantInfoCache = MerchantCacheAssembler.buildMerchant(merchantFullDto);
                //添加商户钱包信息
//          String assetInfos=getAssertInfo(merchantInfoCache);
                //添加商户钱包信息从数据库查
                String assetInfos = getAssertInfoFromDb(merchantInfoCache);
                merchantInfoCache.setAssets(assetInfos);
                try {
                    if(StringUtils.isEmpty(merchantInfoCache.getUid())){
                        MyLogManager.develop("======非法的商户信息  "+JSON.toJSONString(merchantInfoCache) );
                        continue;
                    }
                    ih2Repository.insertMerchantInfo(merchantInfoCache);
                } catch (Exception e) {
                    e.printStackTrace();
                    MyLogManager.develop("====初始化缓存信息出错：添加的商户信息是："+JSON.toJSONString(merchantInfoCache));
                }
            }
        }
    }

    private String getAssertInfo(MerchantInfoCache merchantInfoCache) {
        Result<List<CoinAccount>> list = fundSearchService.getCoinAccontList(merchantInfoCache.getId(), AccountTypeEnum.MERCHANT.code());
        List<AssetInfo> assetInfos = MerchantAssetAssembler.buildAssetInfoList(list);
        return JSON.toJSONString(assetInfos);
    }

    private String getAssertInfoFromDb(MerchantInfoCache merchantInfoCache) {
        List<ExDigitalMoneyAsset> exDigitalMoneyAssets = iDcpayRepository.listAssets(merchantInfoCache.getId(), AccountTypeEnum.MERCHANT.code());
        List<AssetInfo> assetInfos = MerchantAssetAssembler.buildAssetInfoList(exDigitalMoneyAssets);
        return JSON.toJSONString(assetInfos);
    }


    private boolean verifyMerchantFullDto(MerchantFullDto merchantFullDto) {
        if (null == merchantFullDto.getBasic()) {
            MyLogManager.warn("====merchantFullDto获取到的商户基础信息不能为null");
            return false;
        }
        if (null == merchantFullDto.getPower()) {
            MyLogManager.warn("====merchantFullDto获取到的商户权限信息不能为null，商家 uid  " + merchantFullDto.getBasic().getUid());
            return false;
        }
        if (null == merchantFullDto.getPayments()) {
            MyLogManager.warn("====merchantFullDto获取到的商户支付信息设置 不能为null，商家 uid  " + merchantFullDto.getBasic().getUid());
            return false;
        }

        if (null == merchantFullDto.getGrabSwitch()) {
            MyLogManager.warn("====merchantFullDto获取到的商户设置信息不能为null，商家 uid  " + merchantFullDto.getBasic().getUid());
            return false;
        }
        return true;
    }

    public int getNextPage(Pagination pageInfo) {
        AssertUtils.expect(pageInfo.getSize() != 0, " Pagination 分页参数的 size 不能为0 ");
        int totalPage = ((pageInfo.getTotal() - 1) + pageInfo.getSize()) / pageInfo.getSize();
        if (totalPage > pageInfo.getNum()) {
            return pageInfo.getNum() + 1;
        }
        return 0;
    }

    public int updateMerchant(long id) {
//        Result<MerchantFullDto> fullMerchant = merchantService.getFullMerchant(id);
        Result<MerchantFullDto> fullMerchant = merchantService.getFetch(id);
        if (null == fullMerchant || !fullMerchant.getSuccess() || null == fullMerchant.getData()) {
            MyLogManager.error("====merchantService 获取到的获取商户信息失败 [商户id]" + id + "返回消息" + JSON.toJSONString(fullMerchant));
            return 0;
        }
        boolean b = verifyMerchantFullDto(fullMerchant.getData());
        if (b) {
            MerchantInfoCache merchantInfoCache = MerchantCacheAssembler.buildMerchant(fullMerchant.getData());
            merchantInfoCache.setAssets(getAssertInfo(merchantInfoCache));
            return ih2Repository.updateMerchantCacheInfo(merchantInfoCache);
        }
        return 0;
    }

    public Result<MerchantFullDto> getAlertMerchant(long merchantId, MerchantAlterItemEnum alterItemEnum) {
        return merchantService.getAlertMerchant(merchantId, alterItemEnum);
    }

    /**
     * 获取商户的资金信息
     *
     * @param merchantId
     * @param code
     * @return
     */
    public String getCoinAccontList(Long merchantId, int code) {
        Result<List<CoinAccount>> list = fundSearchService.getCoinAccontList(merchantId, code);
        List<AssetInfo> assetInfos = MerchantAssetAssembler.buildAssetInfoList(list);
        return JSON.toJSONString(assetInfos);
    }

    /**
     * 收到mq通知查询不到商户，做添加操作
     *
     * @param merchantId
     * @return
     */
    public int addMerchantCache(Long merchantId) {
//            防止初始化表数据与mq消费并发冲突  加锁操作
        if (1==MerchantCacheConstant.MERCHANT_INIT_LOCK&&merchantCacheRedisRepository.lockMerchant(merchantId)||0==MerchantCacheConstant.MERCHANT_INIT_LOCK) {
            Result<MerchantFullDto> fetch = merchantService.getFetch(merchantId);
            if (!fetch.getSuccess()) {
                MyLogManager.error("====merchantService 获取到的获取商户信息失败 [商户id]" + merchantId + "返回消息" + JSON.toJSONString(fetch));
                return 0;
            } else if (null != fetch.getData()) {
                MyLogManager.develop("====RemoteService.addMerchantCache 添加商户，商户id: " + merchantId);
                //构建商户缓存基本信息
                MerchantInfoCache merchantInfoCache = MerchantCacheAssembler.buildMerchant(fetch.getData());
                //添加商户钱包信息
                //          String assetInfos=getAssertInfo(merchantInfoCache);
                //添加商户钱包信息从数据库查
                String assetInfos = getAssertInfoFromDb(merchantInfoCache);
                merchantInfoCache.setAssets(assetInfos);
                return ih2Repository.insertMerchantInfo(merchantInfoCache);
            }
        }
        return 0;

    }

    public int deleteMerchantById(Long merchantId) {

        return ih2Repository.deleteMerchantById(merchantId);
    }

    public List<MerchantInfoCache> listMerchantCache() {

      return   ih2Repository.listMerchantCache();
    }
}
