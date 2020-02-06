package com.temp.common.base.JDBC.jrx.jrx_bi_demo;



public enum DataTypeEnum {
    GREENPLUM("pivotal:greenplum", "pivotal:greenplum", "com.pivotal.jdbc.GreenplumDriver", "", "", "\'", "\'"),
    POSTGRESQL("postgresql", "postgresql", "org.postgresql.Driver", "", "", "\"", "\""),

    MYSQL("mysql", "mysql", "com.mysql.jdbc.Driver", "`", "`", "'", "'"),

    ORACLE("oracle", "oracle", "oracle.jdbc.driver.OracleDriver", "\"", "\"", "\"", "\""),

    SQLSERVER("sqlserver", "sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "\"", "\"", "\"", "\""),

    H2("h2", "h2", "org.h2.Driver", null, null, "'", "'"),

    PHOENIX("phoenix", "hbase phoenix", "org.apache.phoenix.jdbc.PhoenixDriver", null, null, "'", "'"),

    MONGODB("mongodb", "mongodb", "mongodb.jdbc.MongoDriver", null, null, "'", "'"),

    ELASTICSEARCH("sql4es", "elasticSearch", "nl.anchormen.sql4es.jdbc.ESDriver", null, null, "'", "'"),

    PRESTO("presto", "presto", "com.facebook.presto.jdbc.PrestoDriver", null, null, "'", "'"),

    CASSANDRA("cassandra", "cassandra", "com.github.adejanovski.cassandra.jdbc.CassandraDriver", null, null, "'", "'"),

    CLICKHOUSE("clickhouse", "clickhouse", "ru.yandex.clickhouse.ClickHouseDriver", null, null, "'", "'"),

    KYLIN("kylin", "kylin", "org.apache.kylin.jdbc.Driver", null, null, "'", "'"),

    VERTICA("vertica", "vertica", "com.vertica.jdbc.Driver", null, null, "'", "'"),

    HANA("sap", "sap hana", "com.sap.db.jdbc.Driver", null, null, "'", "'"),

    IMPALA("impala", "impala", "com.cloudera.impala.jdbc41.Driver", null, null, "'", "'");


    private String feature;
    private String desc;
    private String driver;
    private String keywordPrefix;
    private String keywordSuffix;
    private String aliasPrefix;
    private String aliasSuffix;

    DataTypeEnum(String feature, String desc, String driver, String keywordPrefix, String keywordSuffix, String aliasPrefix, String aliasSuffix) {
        this.feature = feature;
        this.desc = desc;
        this.driver = driver;
        this.keywordPrefix = keywordPrefix;
        this.keywordSuffix = keywordSuffix;
        this.aliasPrefix = aliasPrefix;
        this.aliasSuffix = aliasSuffix;
    }


    public static String  getAliasPreFix(String type) {
        DataTypeEnum greenplum = DataTypeEnum.valueOf(type);
        return greenplum.aliasPrefix;
    }

    public String getFeature() {
        return feature;
    }

    public String getDesc() {
        return desc;
    }

    public String getDriver() {
        return driver;
    }

    public String getKeywordPrefix() {
        return keywordPrefix;
    }

    public String getKeywordSuffix() {
        return keywordSuffix;
    }

    public String getAliasPrefix() {
        return aliasPrefix;
    }

    public String getAliasSuffix() {
        return aliasSuffix;
    }
}
