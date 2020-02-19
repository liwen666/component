
#  查询数据库表字段
select column_name as fieldCode  from information_schema.columns where table_schema !='information_schema' and table_schema = 'public' and TABLE_NAME = 'stat_dev_test'  order by table_schema,table_name,ordinal_position