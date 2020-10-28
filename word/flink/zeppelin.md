#linux打包
 1、压缩多个文件的命令
tar   zvfc   xx.tar.gz  /xx /xx /xx
tar   zcvf    zeppelin-0.9.0.tar.gz   zeppelin-0.9.0

2、解压.tar.gz的命令
tar -zxvf tar   zvfc   xx.tar.gz

#安装zeppelin

cd /root/flink/zeppelin-0.9.0-preview2-bin-all/conf
cp zeppelin-env.sh.template zeppelin-env.sh
cp zeppelin-site.xml.template zeppelin-site.xml

vi zeppelin-env.sh


在文件末尾添加代码段 
JAVA_HOME为你的Java jdk路径 
HADOOP_CONF_DIR是你的Hadoop的配置文件目录 
（Hadoop2的配置文件目录一般在安装目录的etc的Hadoop目录下）
export JAVA_HOME=/home/hadoop/java/jdk1.8.0_191/bin/java
export HADOOP_CONF_DIR=/home/hadoop/hadoop/hadoop-2.8.5/bin/hadoop



#安装hive
tar -zxvf apache-hive-2.3.7-bin.tar.gz -C /usr/local/
cd /usr/local/
 mv apache-hive-2.3.7-bin hive
 
 vi /etc/profile
 #hive
 export HIVE_HOME=/usr/local/hive
 export PATH=$PATH:$HIVE_HOME/bin

#刷新资源
 source /etc/profile
  hive --version
  
#配置hive
 cd /usr/local/hive/conf
 cp hive-default.xml.template hive-site.xml
 
 vi hive-site.xml  
 添加MySQL配置和驱动
 
 <!-- 插入一下代码 -->
   <property>
     <name>javax.jdo.option.ConnectionUserName</name>
     <!--用户名（这4是新添加的，记住删除配置文件原有的哦！）-->
     <value>root</value>
   </property>
   <property>
     <name>javax.jdo.option.ConnectionPassword</name>
     <!--密码-->
     <value>root</value>
   </property>
   <property>
     <name>javax.jdo.option.ConnectionURL</name>
     <!--mysql-->
     <value>jdbc:mysql://192.168.60.136:3306/hive?useSSL=false&amp;characterEncoding=utf8</value>
   </property>
   <property>
     <name>javax.jdo.option.ConnectionDriverName</name>
     <!--mysql驱动程序-->
     <value>com.mysql.jdbc.Driver</value>
   </property>
   
   
  <property>
     <name>system:java.io.tmpdir</name>
     <value>/usr/local/hive/tmp</value>
   </property>
   <!-- 到此结束代码 -->
 
 
 #初始化
 cd /usr/local/hive/bin
 schematool -dbType mysql -initSchema
 
 
 
 #执行hive
 mkdir -vp /usr/local/hive/tmp
 安装hadoop  见单机版
 hive
 #hive的使用
 
 create database hive_1;
 
 show databases;
 ##hdfs的变化   
  hadoop fs -lsr /
 ##mysql变化
 use hive
 select * from DBS;
 #创建表
  use hive_1;
create table hive_01 (id int,name string);
show tables;
##查看mysql 变化
select * from TBLS;
#浏览器查看变化
http://192.168.60.220:50070/explorer.html#/user/hive/warehouse/
 
 
 
