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
