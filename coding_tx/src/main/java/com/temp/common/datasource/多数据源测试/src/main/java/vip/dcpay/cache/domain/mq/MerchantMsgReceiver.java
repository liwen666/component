package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.dcpay.alert.sdk.enums.AlertLevelEnum;
import vip.dcpay.cache.domain.config.AssetRabbitConfig;
import vip.dcpay.cache.domain.service.MerchantAlterService;
import vip.dcpay.cache.util.AlertUtil;
import vip.dcpay.dto.mq.AssetChangeMsgDto;
import vip.dcpay.dto.mq.MessageBasic;
import vip.dcpay.dto.mq.mechant.MerchantAlterMsgDto;
import vip.dcpay.enums.mq.MessageType;
import vip.dcpay.log.sdk.MyLogManager;

import java.util.List;

//@Slf4j
@Component
class MerchantMsgReceiver {
    @Autowired
    private MerchantAlterService merchantAlterService;


    /**
     * 更新商户基本信息
     * @param msg
     */
    @RabbitListener(queues = "${merchant.dynamic.queue}")
    public void receiveMsg(String msg) {
        try {
            MyLogManager.info("======接收到商户更新消息: " + msg);

            MessageBasic messageBasic = JSON.parseObject(msg, MessageBasic.class);
            MerchantAlterMsgDto merchantAlterMsgDto = JSON.parseObject(messageBasic.getBody(), MerchantAlterMsgDto.class);
            //更新内存商户信息
            merchantAlterService.modifiMerchant(merchantAlterMsgDto);
        }catch (Exception e){
            e.printStackTrace();
            MyLogManager.error("商户更新消息异常：" + e.getMessage() + "," +msg);
            AlertUtil.sendAlertMsg("商户更新消息异常",e.getMessage() + "," + msg, AlertLevelEnum.ERROR);
        }
    }

    /**
     * 更新商户资金信息
     * @param msg
     */
    @RabbitListener(queues = AssetRabbitConfig.QUEUE_ALTER_ASSET)
    public void receiveAssetMsg(String msg) {
        try {
            MyLogManager.develop("======接收到商户资金更新消息: " + msg);
            MessageBasic messageBasic = JSON.parseObject(msg, MessageBasic.class);
            if(MessageType.AlTER_ASSET_NOTIFY.equals(messageBasic.getType())){
                List<AssetChangeMsgDto> assetChangeMsgDtos = JSON.parseArray(messageBasic.getBody(), AssetChangeMsgDto.class);
                //更新内存商户资金信息
//                merchantAlterService.modifiMerchant(assetChangeMsgDtos);
                merchantAlterService.modifiMerchantByMsg(assetChangeMsgDtos);
            }

        }catch (Exception e){
            e.printStackTrace();
            MyLogManager.error("商户资金更新消息异常：" + e.getMessage() + "," +msg);
            AlertUtil.sendAlertMsg("商户资金更新消息异常",e.getMessage() + "," + msg, AlertLevelEnum.ERROR);
        }
    }
}
