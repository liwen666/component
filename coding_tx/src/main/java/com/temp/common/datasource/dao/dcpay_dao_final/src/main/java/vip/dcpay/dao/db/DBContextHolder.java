package com.temp.common.datasource.dao.dcpay_dao_final.src.main.java.vip.dcpay.dao.db;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    protected static void setDB(DBTypeEnum dbType,boolean master) {
        if(!master) {
           dbType = DBTypeEnum.getEnum(dbType.name()+"_SLAVE");
        }
        contextHolder.set(dbType);
        log.info("切换到数据库 【{}】", JSON.toJSONString(dbType.name()));
    }

    static DBTypeEnum getDB() {
        return contextHolder.get();
    }

    public static void clearDB() {
        contextHolder.remove();
    }

    public static void platform(boolean master) {
        setDB(DBTypeEnum.PLATFORM,master);
    }
    public static void newpay(boolean master) {
        setDB(DBTypeEnum.NEWPAY,master);
    }

    public static void merchant(boolean master) {
        setDB(DBTypeEnum.MERCHANT,master);
    }

    public static void order(boolean master) {
        setDB(DBTypeEnum.ORDER,master);
    }

    public static void base(boolean master) {
        setDB(DBTypeEnum.BASE,master);
    }

    public static void common(boolean master) {
        setDB(DBTypeEnum.COMMON,master);
    }

    public static void schedule(boolean master) {
        setDB(DBTypeEnum.SCHEDULE,master);
    }

    public static void slaveBase(boolean master) {
        setDB(DBTypeEnum.BASE_SLAVE,master);
    }

    public static void h2(boolean master) {
        setDB(DBTypeEnum.H2,master);
    }
}
