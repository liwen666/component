package com.temp.common.datasource.dao.dcpay_dao_final.src.main.java.vip.dcpay.dao.db;

import java.util.Arrays;
import java.util.Optional;

public enum DBTypeEnum {

    PLATFORM,
    PLATFORM_SLAVE,
    MERCHANT,
    MERCHANT_SLAVE,
    ORDER,
    ORDER_SLAVE,
    BASE,
    BASE_SLAVE,
    COMMON,
    COMMON_SLAVE,
    SCHEDULE,
    SCHEDULE_SLAVE,
    NEWPAY,
    NEWPAY_SLAVE,
    H2,
    H2_SLAVE;

    private String name;

    private String url;

    private String username;

    private String password;

    private String driver;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public DBTypeEnum setUrl(String url) {
        this.url = url;
        return this;
    }

    public DBTypeEnum setUsername(String username) {
        this.username = username;
        return this;
    }

    public DBTypeEnum setPassword(String password) {
        this.password = password;
        return this;
    }

    public DBTypeEnum setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public String getName() {
        return name;
    }

    public DBTypeEnum setName(String name) {
        this.name = name;
        return this;
    }

    public static DBTypeEnum getEnum(String code){
        Optional<DBTypeEnum> first = Arrays.stream(DBTypeEnum.values()).filter(e -> e.name().equals(code)).findFirst();
        return first.isPresent()?first.get():null;
    }

}
