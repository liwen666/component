#pgsql

##让他支持 if not exit  
create or replace function ddl_ine(sql text) returns int2 as $$
declare
begin
  execute sql;
  return 0;  -- 返回0表示正常
  exception when duplicate_table then
    raise notice '%', SQLERRM;
    return 1;  -- 返回1表示已存在
  when others then
    raise notice '%ERROR: % %create table error:  %', chr(10), SQLERRM, chr(10), sql;
    return 2;  -- 返回2表示DDL其他错误
end;
$$ language plpgsql strict;  



#GP  数据库类型转换
create cast (varchar as timestamp) with inout as ASSIGNMENT;
create cast (varchar as timestamptz) with inout as ASSIGNMENT;
create cast (varchar as date) with inout as ASSIGNMENT;

