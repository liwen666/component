----规范工作流表索引---
declare
  indexname  varchar2(100);
  table_name varchar2(100);
  i     number;
begin
  for item in (select ui.index_name, ui.table_name
                 from user_indexes ui
                where ui.uniqueness ='NONUNIQUE'and ui.index_type = 'NORMAL'
                and ui.table_name like 'ACT_%') loop
    -- 将查询到的数据赋值给变量 --
    indexname   := item.index_name;
    table_name := item.table_name;
   --dbms_output.put_line(indexname||''-----''||table_name);
   execute immediate  'drop index '|| indexname;
  end loop;
  --创建索引

  commit;
end;
