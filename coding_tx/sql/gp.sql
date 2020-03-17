
#  查询数据库表字段
select column_name as fieldCode  from information_schema.columns where table_schema !='information_schema' and table_schema = 'public' and TABLE_NAME = 'stat_dev_test'  order by table_schema,table_name,ordinal_position


-- 处理日期类型的字段转换问题
create cast (varchar as timestamp) with inout as ASSIGNMENT;

create cast (varchar as timestamptz) with inout as ASSIGNMENT;

create cast (varchar as date) with inout as ASSIGNMENT;