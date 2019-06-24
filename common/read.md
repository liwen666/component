#window  的bat启动文件

（for /f "delims=" %%A in ('dir /b *.jar') do set "filename=%%A"
title %filename%
java -Dloader.path=ext -jar -Djava.ext.dirs=lib %filename% --debug=false --portal.devMode=false）


for /f "delims=" %%i in ('dir /ah /s/b') do attrib "%%i" -s -h 其实后边应该再加个-r
命令的意思解释：
for /f "delims=" %%i in 循环
dir /s显示当前目录及子目录中所有文件
参数 /ah具有隐藏属性的文件
参数 /b用短文件名的方式显示
do attrib "%%i" -s -h 取消这个文件/文件夹的 系统属性 隐藏属性

#linux的sh启动文件

  ##start
          #!/bin/sh
          PWD=`pwd`
          RESOURCE_NAME=`find ./ -name portal*.jar`
          RESOURCE_NAME=$PWD/$RESOURCE_NAME
          profile=""
          if [ -n "$1" ];then
          profile="-Dspring.profiles.active=$1"
          fi
          echo $profile  $RESOURCE_NAME
          
          tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
          if [ ${tpid} ]; then
          echo 'Stop Process...'
          kill -15 $tpid
          fi
          sleep 5
          tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
          if [ ${tpid} ]; then
          echo 'Kill Process!'
          kill -9 $tpid
          else
          echo 'Stop Success!'
          fi
           
          tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
          if [ ${tpid} ]; then
              echo 'App is running.'
          else
              echo 'App is NOT running.'
          fi
          
          rm -f tpid
          
          localjava=`find ./jres/jre_linux/bin -name java`
          if [ ${localjava} ]; then
              echo 'use local java.'
            nohup jres/jre_linux/bin/java -Dloader.path=./ext -jar -Djava.ext.dirs=./lib $profile $RESOURCE_NAME --debug=false --portal.devMode=false &
          else
              echo 'use system java.'
            nohup java -Dloader.path=./ext -jar -Djava.ext.dirs=./lib $profile $RESOURCE_NAME --debug=false --portal.devMode=false &
          fi
          echo $! > tpid
          echo Start Success!


#stop 
        #!/bin/sh
        PWD=`pwd`
        RESOURCE_NAME=`find ./ -name bdp-*.jar`
        RESOURCE_NAME=$PWD/$RESOURCE_NAME
        
        tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
        if [ ${tpid} ]; then
        echo 'Stop Process...'
        kill -15 $tpid
        fi
