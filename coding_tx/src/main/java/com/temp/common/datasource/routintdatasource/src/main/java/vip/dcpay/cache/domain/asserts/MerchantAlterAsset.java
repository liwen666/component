package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.asserts;

import com.alibaba.fastjson.JSON;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.merchant.application.dto.MerchantFullDto;
import vip.dcpay.merchant.application.dto.MerchantLoginInfoDto;

import java.lang.reflect.Field;

/**
 * 商户缓存更新断言
 * author lw
 * date 2019/8/28 11:30
 */
public class MerchantAlterAsset {

    /**
     * 判断是否是等出
     *
     * @param data
     * @return
     */
    public static boolean isLoginOut(MerchantFullDto data) {

        MerchantLoginInfoDto login = data.getLogin();
        if(null==login){
            return false;
        }
        Integer isLogin = login.getIsLogin();
        return 0 == isLogin ? true : false;
    }

    public static boolean allFieldIsNull(Object o) {
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object object = field.get(o);
                if (object instanceof CharSequence) {
                    if (!org.springframework.util.ObjectUtils.isEmpty(object)) {
                        return false;
                    }
                } else {
                    if (null != object) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            MyLogManager.error("判断对象属性为空异常", e);
        }
        return true;

    }

    public static boolean isNull(MerchantInfoCache merchantInfoCache) {
        String s = JSON.toJSONString(merchantInfoCache);
        if ("{}".equals(s)) {
            return true;
        }
        return false;
    }
}
