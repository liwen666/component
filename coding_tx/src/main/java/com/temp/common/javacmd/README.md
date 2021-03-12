cmd.exe /c start 

jar -cvf "C:\Users\liwen\Desktop\test\aaaa.jar" -C "D:\workspace\BuildJar\tmp\classes" . 

jar -cvf "C:\Users\liwen\Desktop\test\aaaa.jar" -C "com/" . 


3.jar cvfm wxpay-sdk-3.0.9.jar wxpay-sdk-3.0.9\META-INF\MANIFEST.MF -C wxpay-sdk-3.0.9/ .


jar -cvfm "C:\Users\liwen\Desktop\test\aaaa.jar"  MANIFEST.MF -C "com" .

#打包命令，进入到目标文件夹下
jar -cvfm "C:\Users\liwen\Desktop\test\test.jar"  META-INF/MANIFEST.MF -C "." .