#安装flink

./bin/start-cluster.sh

iptables -I INPUT -p tcp --dport 8081 -j ACCEPT

