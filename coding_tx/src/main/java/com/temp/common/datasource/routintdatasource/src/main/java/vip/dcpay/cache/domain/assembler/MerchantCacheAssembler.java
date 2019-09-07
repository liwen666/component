package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.assembler;

import com.alibaba.fastjson.JSON;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;
import vip.dcpay.dto.payment.PaymentDto;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.merchant.application.dto.MerchantFullDto;
import vip.dcpay.merchant.application.dto.MerchantPaymentChoiceDto;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author lw
 * date 2019/8/28 11:30
 */
public class MerchantCacheAssembler {


    public static MerchantInfoCache buildMerchant(MerchantFullDto merchantFullDto) {
        MerchantInfoCache merchantInfoCache = null;
        try {
            merchantInfoCache = MerchantInfoCache.builder()
                    .activateStatus(merchantFullDto.getBasic().getActivateStatus())
                    .realname(merchantFullDto.getBasic().getRealname())
    //                .paymentChoices(JSON.toJSONString(merchantFullDto.getPaymentChoices()))
    //                .payments(JSON.toJSONString(merchantFullDto.getPayments()))
                    .paymentChoices(merchantFullDto.getPaymentChoices()==null||merchantFullDto.getPaymentChoices().size()==0?null: JSON.toJSONString(
                            new HashSet<>( merchantFullDto.getPaymentChoices().stream().map(MerchantPaymentChoiceDto::getPayWay).collect(Collectors.toList()))))
                    .payments(merchantFullDto.getPayments()==null||merchantFullDto.getPayments().size()==0?null:JSON.toJSONString(new HashSet<>(
                            merchantFullDto.getPayments().stream().filter(e->1==e.getStatus()).map(PaymentDto::getPayWay).collect(Collectors.toList()))))
                    .type(merchantFullDto.getPower().getType())
                    .uid(merchantFullDto.getBasic().getUid())
                    .id(merchantFullDto.getBasic().getId())
                    .grab(merchantFullDto.getGrabSwitch().getGrab())
                    .merchantDeposit(merchantFullDto.getGrabSwitch().getMerchantDeposit())
                    .playerDeposit(merchantFullDto.getGrabSwitch().getPlayerDeposit())
                    .merchantWithdraw(merchantFullDto.getGrabSwitch().getMerchantWithdraw())
                    .platformWithdraw(merchantFullDto.getGrabSwitch().getPlatformWithdraw())
                    .areaName(null==merchantFullDto.getArea()?null:merchantFullDto.getArea().getServiceCity())
                    .areaCode(null==merchantFullDto.getArea()?null:merchantFullDto.getArea().getServiceCityCode())
                    .build();
            if (null != merchantFullDto.getDimension()) {
                merchantInfoCache.setDayMountSum(merchantFullDto.getDimension().getDayAmountSum());
                merchantInfoCache.setDayOrderCount(merchantFullDto.getDimension().getDayOrderCount());
            }
        } catch (NumberFormatException e) {
            MyLogManager.error("====初始化商户信息有误 商户信息： "+JSON.toJSONString(merchantFullDto));
            e.printStackTrace();
        }
        return merchantInfoCache;
    }



    public static MerchantInfoCache bulidAlterMerchant(Map<String, Object> data) {
        MerchantFullDto merchantFullDto = new MerchantFullDto();
        Field[] declaredFields = merchantFullDto.getClass().getDeclaredFields();
        String fieldName = data.keySet().iterator().next();
        for(Field f:declaredFields){
            if(f.getName().equals(fieldName)){
                f.setAccessible(true);
                try {
                    f.set(merchantFullDto,data.get(fieldName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        MerchantInfoCache merchantInfoCache = bulidAlterMerchant(merchantFullDto);
        return merchantInfoCache;
    }

    public static MerchantInfoCache bulidAlterMerchant(MerchantFullDto merchantFullDto) {
        MerchantInfoCache merchantInfoCache = MerchantInfoCache.builder()
                .activateStatus(merchantFullDto.getBasic()!=null?merchantFullDto.getBasic().getActivateStatus():null)
                .realname(merchantFullDto.getBasic()!=null?merchantFullDto.getBasic().getRealname():null)
//                .paymentChoices(JSON.toJSONString(merchantFullDto.getPaymentChoices()))
//                .payments(JSON.toJSONString(merchantFullDto.getPayments()))
                .paymentChoices(merchantFullDto.getPaymentChoices()==null||merchantFullDto.getPaymentChoices().size()==0?null:JSON.toJSONString(new HashSet<>(
                        merchantFullDto.getPaymentChoices().stream().map(MerchantPaymentChoiceDto::getPayWay).collect(Collectors.toList()))))
                .payments(merchantFullDto.getPayments()==null||merchantFullDto.getPayments().size()==0?null:JSON.toJSONString(new HashSet<>(
                        merchantFullDto.getPayments().stream().filter(e->1==e.getStatus()).map(PaymentDto::getPayWay).collect(Collectors.toList()))))
                .type(merchantFullDto.getPower()!=null?merchantFullDto.getPower().getType():null)
//                .uid(merchantFullDto.getBasic().getUid()==null?null:merchantFullDto.getBasic().getUid())
                .id(merchantFullDto.getBasic()!=null?merchantFullDto.getBasic().getId():null)
                .grab(merchantFullDto.getGrabSwitch()!=null?merchantFullDto.getGrabSwitch().getGrab():null)
                .merchantDeposit(merchantFullDto.getGrabSwitch()!=null?merchantFullDto.getGrabSwitch().getMerchantDeposit():null)
                .playerDeposit(merchantFullDto.getGrabSwitch()!=null?merchantFullDto.getGrabSwitch().getPlayerDeposit():null)
                .merchantWithdraw(merchantFullDto.getGrabSwitch()!=null?merchantFullDto.getGrabSwitch().getMerchantWithdraw():null)
                .platformWithdraw(merchantFullDto.getGrabSwitch()!=null?merchantFullDto.getGrabSwitch().getPlatformWithdraw():null)
                .areaCode(null==merchantFullDto.getArea()?null:merchantFullDto.getArea().getServiceCityCode())
                .areaName(null==merchantFullDto.getArea()?null:merchantFullDto.getArea().getServiceCity())
                .build();
        if (null != merchantFullDto.getDimension()) {
            merchantInfoCache.setDayMountSum(merchantFullDto.getDimension().getDayAmountSum());
            merchantInfoCache.setDayOrderCount(merchantFullDto.getDimension().getDayOrderCount());
        }
        return merchantInfoCache;

    }

}
