package com.temp.common.datasource.多数据源测试.src.main.test.vip.dcpay.cache.domain.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vip.dcpay.cache.RunApplication;
import vip.dcpay.cache.domain.repository.IH2Repository;
import vip.dcpay.cache.util.AssertUtils;
import vip.dcpay.dto.assets.AssetInfo;
import vip.dcpay.dto.payment.PaymentDto;
import vip.dcpay.enums.commons.AccountTypeEnum;
import vip.dcpay.enums.merchant.MerchantActivateStatusEnum;
import vip.dcpay.enums.merchant.MerchantAlterItemEnum;
import vip.dcpay.enums.payment.PaymentStatusEnum;
import vip.dcpay.fund.FundSearchService;
import vip.dcpay.fund.dto.CoinAccount;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.merchant.application.dto.FetcherDto;
import vip.dcpay.merchant.application.dto.MerchantFullDto;
import vip.dcpay.merchant.application.dto.MerchantPaymentChoiceDto;
import vip.dcpay.merchant.application.dto.grab.MerchantGrabSwitchDto;
import vip.dcpay.merchant.application.inner.MerchantInnerService;
import vip.dcpay.merchant.application.param.business.FetcherFilterCondition;
import vip.dcpay.merchant.application.service.merchant.MerchantService;
import vip.dcpay.vo.basic.Page;
import vip.dcpay.vo.basic.Pagination;
import vip.dcpay.vo.basic.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class RemoteServiceTest {
    @Reference
    private MerchantService merchantService;
    @Reference
    private FundSearchService fundSearchService;


    @Reference
    MerchantInnerService merchantInnerService ;

    @Autowired
    private IH2Repository ih2Repository;

    @Test
    public void getMerchant() {
        String data ="{\"currency\":\"CNY\",\"maxBalance\":1000000000,\"maxLoseLawRate\":1,\"maxTransQuantityInWeek\":1000000000,\"maxTurnoverInToday\":1000000000,\"merchantType\":3,\"minBalance\":0,\"minLoseLawRate\":0,\"minTransQuantityInWeek\":0,\"minTurnoverInToday\":0,\"orderType\":2}";
        FetcherFilterCondition fetcherFilterCondition = JSON.parseObject(data, FetcherFilterCondition.class);
        fetcherFilterCondition.setMerchantTypeList(new ArrayList<Integer>(){{add(1);add(2);add(3);}});
        Result<List<FetcherDto>> fetcher = merchantInnerService.findFetcher(fetcherFilterCondition);
        System.out.println(JSON.toJSONString(fetcher));
        MerchantFullDto merchantFullDto = new MerchantFullDto();
        //商家uid
        Long id = merchantFullDto.getBasic().getId();//商家id
        String uid = merchantFullDto.getBasic().getUid();//商家uid
        String realname1 = merchantFullDto.getBasic().getRealname();  //商家实名
        Integer activateStatus = merchantFullDto.getBasic().getActivateStatus() == null ? MerchantActivateStatusEnum.NOT_APPLY.code()
                : merchantFullDto.getBasic().getActivateStatus();//激活状态
        Integer type1 = merchantFullDto.getPower().getType();//商家类型
//        Integer canFetch = merchantFullDto.getPower().getCanFetch();// 领取权限开关
        MerchantGrabSwitchDto grabSwitch = merchantFullDto.getGrabSwitch();  //商家设置信息   ？？？？
        //  商家当前余额           从钱包拿
        //  商家实时交易总金额   TODO 数据不能缓存
        //  商家7日交易数量
        //  当日成交金额  范围
        //  败诉率

        //  商家资产信息    此消息不能缓存

        List<MerchantPaymentChoiceDto> paymentChoices = merchantFullDto.getPaymentChoices();
        List<PaymentDto> payments = merchantFullDto.getPayments();
        List<String> enabledChosenRecvPayway = getEnabledChosenRecvPayway(paymentChoices, payments);  //  商家设置的收款方式

        /**
         * 提供数据
         * 1.商家设置的收款方式
         * 2.商家每日的时时交易金额
         * 3.商家的资产信息
         * 4.商家7日交易数量
         */






    }


  @Test
    public void getFullDto() {
        Result<Page<MerchantFullDto>> pageFullMerchant = merchantService.getPageFetch(1, 100);
        Page<MerchantFullDto> data = pageFullMerchant.getData();
        List<MerchantFullDto> content = data.getContent();
        Pagination pagination = data.getPagination();

        System.out.println("=========================================================================================");
        System.out.println(JSON.toJSONString(pageFullMerchant));


    }

    @Test
    public void getFullDtoById() {
        Result<MerchantFullDto> fullMerchant = merchantService.getFullMerchant(111111l);
        System.out.println(JSON.toJSONString(fullMerchant));


    }




    @Test
    public void getFundSearch() {
       Long merchantId = 1l;
        Result<List<CoinAccount>> list = fundSearchService.getCoinAccontList(merchantId, AccountTypeEnum.MERCHANT.code());
        List<AssetInfo> assetInfos = buildAssetInfoList(list);
    }




    private List<AssetInfo> buildAssetInfoList(Result<List<CoinAccount>> assetsList) {
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
    public List<String> getEnabledChosenRecvPayway(List<MerchantPaymentChoiceDto> paymentChoices, List<PaymentDto> payments) {
        Map<Long, PaymentDto> paymentDtoMap = payments.stream().collect(Collectors.toMap(PaymentDto::getId, Function.identity(), (k1, k2) -> k1));
        List<String> payways = new ArrayList<>();
            Collection<MerchantPaymentChoiceDto> vlaues = paymentChoices;
            for (MerchantPaymentChoiceDto e : vlaues) {
                PaymentDto paymentDto = paymentDtoMap.get(e.getPaymentId());
                if (paymentDto.getStatus().intValue() == PaymentStatusEnum.ENBALED.code()) {
                    payways.add(paymentDto.getPayWay());
                }
            }
            return payways;
    }
    @Test
    public void getFetcherDto() {
        String data ="{\"currency\":\"CNY\",\"maxBalance\":1000000000,\"maxLoseLawRate\":1,\"maxTransQuantityInWeek\":1000000000,\"maxTurnoverInToday\":1000000000,\"merchantType\":3,\"minBalance\":0,\"minLoseLawRate\":0,\"minTransQuantityInWeek\":0,\"minTurnoverInToday\":0,\"orderType\":2}";
        FetcherFilterCondition fetcherFilterCondition = JSON.parseObject(data, FetcherFilterCondition.class);
        fetcherFilterCondition.setMerchantTypeList(new ArrayList<Integer>(){{add(1);add(2);add(3);}});
        Result<List<FetcherDto>> fetcher = merchantInnerService.findFetcher(fetcherFilterCondition);
        System.out.println(JSON.toJSONString(fetcher));

    }





    //分页查询所有商户信息
    @Test
    public void name() {
        int pageSize = 10000;
        //查询数据
        Pagination build = Pagination.builder().num(1).size(pageSize).total(10001).build();
        int currentPage = getCurrentPage(build);
        while (currentPage!=0){
            System.out.println("============> 查询第  "+currentPage);
            build.setNum(currentPage);
            currentPage = getCurrentPage(build);
            //查询数据
//            查询商户信息
        }
        System.out.println("=========================================================================================");

    }

    public int getCurrentPage(Pagination pageInfo) {
        AssertUtils.expect(pageInfo.getSize()!=0," Pagination 分页参数的 size 不能为0 ");
       int totalPage = ((pageInfo.getTotal()-1)+pageInfo.getSize())/pageInfo.getSize();
       if(totalPage>pageInfo.getNum()){
           return pageInfo.getNum()+1;
       }
        return 0;
    }

    @Test
    public void alertNotify() {
//        Result<Map<String, Object>> mapResult = merchantService.getAlertMerchant(1l, MerchantAlterItemEnum.REALNAME);
        Result<MerchantFullDto> mapResult = merchantService.getAlertMerchant(1l, MerchantAlterItemEnum.PAYMENT_WAY);
        if(!mapResult.getSuccess()||null==mapResult.getData()){
                MyLogManager.error("====merchantService 获取到的获取商户更新信息失败 "+JSON.toJSONString(mapResult));
                return;
        }
        System.out.println("=========================================================================================");
        System.out.println(JSON.toJSONString(mapResult));
//       MerchantInfoCache merchantInfoCache = MerchantCacheAssembler.bulidAlterMerchant(mapResult.getData(),true);
//        System.out.println(JSON.toJSONString(merchantInfoCache));
    }
}