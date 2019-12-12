#本篇介绍查询某模式下所有表的分布键信息，适用于：
 
 ##排查是否有分布键创建不合理的表，如果分布键是重复率比较高的字段会造成数据分布不均匀，存储过于倾斜。
 ##排查经常做表关联的表是否是相同分布键，这样会提高执行效率。

SELECT
    aaa.nspname AS "模式名",
    aaa.relname AS "表名",
    aaa.table_comment AS "中文表明",
    ccc.attname AS "分布键"
FROM
    (
        SELECT
            aa.oid,
            obj_description (aa.oid) AS table_comment,
            aa.relname,
            bb.localoid,
            bb.attrnums,
            regexp_split_to_table(
                array_to_string(bb.attrnums, ','),
                ','
            ) att,
            dd.nspname
        FROM
            pg_class aa --原数据信息 最重要的表！
        LEFT JOIN gp_distribution_policy bb ON bb.localoid = aa.oid --分布键表 
        LEFT JOIN pg_namespace dd ON dd.oid = aa.relnamespace --模式 
        LEFT JOIN pg_inherits hh ON aa.oid = hh.inhrelid --继承表  
        WHERE
            dd.nspname = 'dim'  -- 替换成需要的模式
        AND hh.inhrelid IS NULL 
    ) aaa
LEFT JOIN pg_attribute ccc ON ccc.attrelid = aaa.oid
AND ccc.attnum = aaa.att
WHERE
    ccc.attnum > 0
ORDER BY
    aaa.relname ;
    
    
    
    
 #连接配置
 # 1.posgresql
 posgresql_driver=org.postgresql.Driver
 posgresql_url=jdbc:postgresql://192.168.xx.xx:5432/数据库名称(即schema)
 posgresql_user=账号
 posgresql_password=密码
 
 # 2.greenplum
 greenplum_driver=com.pivotal.jdbc.GreenplumDriver
 greenplum_url=jdbc:pivotal:greenplum://192.168.xx.xx:5432;DatabaseName=数据库名称(即schema)
 greenplum_user=账号
 greenplum_password=密码