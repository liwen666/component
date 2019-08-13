rabbitmqctl add_user root root
rabbitmqctl set_user_tags root administrator
rabbitmqctl set_permissions -p / root ".*" ".*" ".*"
//查看用户命令
rabbitmqctl list_users

① 到指定目录：cd/etc/init.d

 ② 停止：rabbitmq-server stop

 ③ 启动：rabbitmq-server start

 ④ 查看是否停止/启动成功：ps -ef |grep rabbitmq
 
 
 2.开启RabbitMQ Managerment管理界面
 
 ① 到指定目录：cd /usr/lib/rabbitmq/lib/rabbitmq_server-3.1.5/plugins
 
 ② 开启管理界面：./rabbitmq-plugins enable rabbitmq_management
 
 ③ 重启RabbitMQ