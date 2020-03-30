查看系统连接使用情况

show variables like '%connect%';

SHOW VARIABLES LIKE '%log_bin%';

设置单个用户最大连接数
set global max_user_connections=2000;

查看连接情况
SHOW PROCESSLIST

show status


set GLOBAL max_connections=10000


#mysql  系统时间问题

获取mysql  的数据库时间   SELECT NOW(3);

查看MySQL的时区
show variables like '%zone%';

SET GLOBAL time_zone = '+8:00';

SET GLOBAL time_zone = '+8:00';  修改mysql时区不一致问题

mysql数据库 查询字段类型

SELECT column_name,column_comment,data_type,table_name 
FROM information_schema.columns 
WHERE data_type='timestamp'


#mysql慢日志查询

show variables like 'slow_query%';

在my.ini添加如下代码，即可查看那个sql语句执行慢了

log-slow-queries = d:/log/mysql-slow.log

long_query_time = 1

打开日志

log = "d:/log/log.log"
打开错误日志
log-error= "d:/log/mysql-error.log"


#创建索引

DROP INDEX idx_run_state ON schedule_partition_execution;
ALTER TABLE schedule_partition_execution ADD INDEX idx_run_state (run_state);
  DROP INDEX idx_schedule_execution_id ON schedule_partition_execution;
 ALTER TABLE schedule_partition_execution ADD INDEX idx_schedule_execution_id (schedule_execution_id);
   DROP INDEX idx_task_key ON schedule_partition_execution;
 ALTER TABLE schedule_partition_execution ADD INDEX idx_task_key(task_key);


DROP INDEX idx_run_state ON schedule_plan_execution_task;
ALTER TABLE schedule_plan_execution_task ADD INDEX idx_run_state (run_state);
DROP INDEX idx_schedule_execution_id ON schedule_plan_execution_task;
ALTER TABLE schedule_plan_execution_task ADD INDEX idx_schedule_execution_id (schedule_execution_id);


DROP INDEX idx_run_state ON schedule_plan_execution;
ALTER TABLE schedule_plan_execution ADD INDEX idx_run_state (run_state);

#执行节点

 DROP INDEX IDX_PARENT_EXECUTION_ID ON `task_execution`;
ALTER TABLE task_execution ADD INDEX IDX_PARENT_EXECUTION_ID (PARENT_EXECUTION_ID);




ALTER TABLE 表名 ADD [UNIQUE | FULLTEXT | SPATIAL]  INDEX | KEY  [索引名] (字段名1 [(长度)] [ASC | DESC]) [USING 索引方法]；



CREATE  [UNIQUE | FULLTEXT | SPATIAL]  INDEX  索引名 ON  表名(字段名) [USING 索引方法]；

#删除索引

drop index fileuploadercode1 on projectfile;

EXPLAIN SELECT * FROM `index_demo` ii WHERE ii.e_name = 'Jane';
