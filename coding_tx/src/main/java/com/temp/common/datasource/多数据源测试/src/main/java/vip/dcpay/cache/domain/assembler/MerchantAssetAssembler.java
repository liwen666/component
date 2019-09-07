package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.assembler;

import com.alibaba.fastjson.JSON;
import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;
import vip.dcpay.dto.assets.AssetInfo;
import vip.dcpay.dto.mq.AssetChangeMsgDto;
import vip.dcpay.fund.dto.CoinAccount;
import vip.dcpay.vo.basic.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author lw
 * date 2019/8/28 11:30
 */
public class MerchantAssetAssembler {

    public static List<AssetInfo> buildAssetInfoList(Result<List<CoinAccount>> assetsList) {
        List<AssetInfo> list = null;
        if (assetsList != null && assetsList.getSuccess() && assetsList.getData() != null
                && !assetsList.getData().isEmpty()) {
            list = new ArrayList<>();
            List<CoinAccount> data = assetsList.getData();
            for (CoinAccount ca : data) {
                AssetInfo assetInfo = AssetInfo.builder().amount(ca.getHotMoney()).currency(ca.getCoinCode()).build();
                list.add(assetInfo);
            }
        }
        return list;
    }


    public static List<AssetInfo> buildAssetInfoList(List<ExDigitalMoneyAsset> exDigitalMoneyAssets) {
        List<AssetInfo> list = new ArrayList<>();
            for (ExDigitalMoneyAsset ca : exDigitalMoneyAssets) {
                AssetInfo assetInfo = AssetInfo.builder().amount(ca.getHotMoney()).currency(ca.getCoinCode()).build();
                list.add(assetInfo);
            }
        return list;
    }

    public static String conversionMsgDto(AssetChangeMsgDto assetChangeMsgDto) {
        AssetInfo build = AssetInfo.builder().amount(assetChangeMsgDto.getHotMoney()).currency(assetChangeMsgDto.getCurrency()).build();
        return JSON.toJSONString(Arrays.asList(build));
    }
}
