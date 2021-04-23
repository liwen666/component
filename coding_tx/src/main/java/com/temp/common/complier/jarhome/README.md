cmd.exe /c start 

###################
双引号可以不要
####################

jar -cvf "C:\Users\liwen\Desktop\test\aaaa.jar" -C "D:\workspace\BuildJar\tmp\classes" . 

jar -cvf "C:\Users\liwen\Desktop\test\aaaa.jar" -C "com/" . 


jar cvfm wxpay-sdk-3.0.9.jar wxpay-sdk-3.0.9\META-INF\MANIFEST.MF -C wxpay-sdk-3.0.9/ .


jar -cvfm "C:\Users\liwen\Desktop\test\aaaa.jar"  MANIFEST.MF -C "com" .

#打包命令，进入到目标文件夹下
jar -cvfm "C:\Users\liwen\Desktop\test\test.jar"  META-INF/MANIFEST.MF -C "." .

jar -cvfm "C:\Users\liwen\Desktop\test\test.jar"  MANIFEST.MF -C "." .


jar -cvfm "flink-job.jar"  META-INF/MANIFEST.MF -C "." .


#springboot项目打包
表示不产生清单，不压缩

jar  -cvfM0 flink-job.jar . 


#列出文件
jar -tvf test.jar | grep MANIFEST.MF

#解压指定路径下的文件
jar -xvf test.jar  test/jrx/data/hub/flink/data/SQLExampleData2PG.class
#替换文件
jar -uvf  "C:\Users\liwen\Desktop\test\test.jar"    META-INF/MANIFEST.MF
jar -uvf  "C:\Users\liwen\Desktop\test\test.jar"   MANIFEST.MF

 #linux cd  /home/data/job  & jar -uvf  /home/data/job/job-runtime-1.0.0-SNAPSHOT.jar    ./*.json
 #win
cmd /k cd d:  &  cd  D:\work\any-data-hub-parent\any-data-processor\src\main\resources\job\cfdafdasjob\0ed9c4ffd3954086b84e8912ad0b9e5b\  & jar -uvf  D:\work\any-data-hub-parent\any-data-processor\src\main\resources\job\cfdafdasjob\0ed9c4ffd3954086b84e8912ad0b9e5b\job-runtime-1.0.0-SNAPSHOT.jar    ./*.json