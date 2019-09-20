package com.temp.common.datasource.dao.dcpay_dao_final.src.main.java.vip.dcpay.dao.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 朱光勋
 * @version V1.0
 * @date 2019/6/5 10:27
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Switch {

    DBTypeEnum value() default DBTypeEnum.BASE;

    boolean master() default true;
}
