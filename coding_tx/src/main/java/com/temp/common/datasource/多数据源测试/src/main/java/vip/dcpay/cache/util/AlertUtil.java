package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.util;

import com.alibaba.fastjson.JSON;
import vip.dcpay.alert.sdk.MyAlertManager;
import vip.dcpay.alert.sdk.bean.AlertParam;
import vip.dcpay.alert.sdk.enums.AlertLevelEnum;
import vip.dcpay.log.sdk.MyLogManager;

public class AlertUtil {

    public static void sendAlertMsg(String eventName, String content, AlertLevelEnum level){
        try {

            AlertParam alertParam = AlertParam.builder()
                    .appName("调度缓存控制")
                    .moduleName("调度缓存控制")
                    .eventName(eventName)
                    .content(content)
                    .alertLevel(level)
                    .build();
            MyLogManager.info("======调度缓存服务 发送报警：" + JSON.toJSONString(MyAlertManager.alert(alertParam)));
        } catch ( Exception e){
            System.out.print("发送报警信息异常:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
