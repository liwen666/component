
1、查看表中存在的锁
 
select a.locktype,a.database,a.pid,a.mode,a.relation,b.relname
 
from pg_locks a
 
join pg_class b on a.relation = b.oid
 
where upper(b.relname) = 'table_name';
 
 
2.杀掉进程。
kill有两种方式，第一种是：
SELECT pg_cancel_backend(PID);
这种方式只能kill select查询，对update、delete 及DML不生效)
 
第二种是：
SELECT pg_terminate_backend(PID);
这种可以kill掉各种操作(select、update、delete、drop等)操作
————————————————
版权声明：本文为CSDN博主「Teresa7」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/angelina7/java/article/details/78110138