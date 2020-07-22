查看系统连接使用情况

show variables like '%connect%';

SHOW VARIABLES LIKE '%log_bin%';

设置单个用户最大连接数
set global max_user_connections=6000;

查看连接情况
SHOW PROCESSLIST

show status


set GLOBAL max_connections=15000


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


#mysql用户操作
复制代码
1.创建用户:
# 指定ip：192.118.1.1的mjj用户登录
create user 'mjj'@'192.118.1.1' identified by '123';
# 指定ip：192.118.1.开头的mjj用户登录
create user 'mjj'@'192.118.1.%' identified by '123';
# 指定任何ip的mjj用户登录
create use 'mjj'@'%' identified by '123';

2.删除用户
drop user '用户名'@'IP地址';


3.修改用户
rename user '用户名'@'IP地址' to '新用户名'@'IP地址';

4.修改密码
set password for '用户名'@'IP地址'=Password('新密码');
复制代码
 

对当前用户授权管理：
　　

复制代码
#查看权限
show grants for '用户'@'IP地址'

#授权
grant select ,insert,update on db1.t1 to "mjj"@'%';

# 表示有所有的权限，除了grant这个命令，这个命令是root才有的。
grant all privileges  on db1.t1 to "mjj"@'%';

#取消权限
取消来自远程服务器的mjj用户对数据库db1的所有表的所有权限

revoke all on db1.* from 'mjj'@"%";  

取消来自远程服务器的mjj用户所有数据库的所有的表的权限
revoke all privileges on '*' from 'mjj'@'%';
复制代码
 

MySQL备份命令行操作：
复制代码
# 备份：数据表结构+数据
mysqdump -u root db1 > db1.sql -p


# 备份：数据表结构
mysqdump -u root -d db1 > db1.sql -p

#导入现有的数据到某个数据库
#1.先创建一个新的数据库
create database db10;
# 2.将已有的数据库文件导入到db10数据库中
mysqdump -u root -d db10 < db1.sql -p

