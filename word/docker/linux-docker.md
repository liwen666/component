

#linux 下安装 docker
 uname -a   
 
 yum list installed | grep docker
 yum -y install docker

 systemctl start docker
 
 systemctl status docker
 
 
 #开启docker 的远程访问
 systemctl daemon-reload
 systemctl restart docker
 
 看端口监听
 
 CentOS7还可以通过修改/etc/sysconfig/docker文件中的 OPTIONS来达到同样的目的
 
 OPTIONS='--selinux-enabled -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375'
 
  iptables -I INPUT -p tcp --dport 2375 -j ACCEPT
