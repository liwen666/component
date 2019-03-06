#1 创建Java jni接口
 
#2  在java 的源文件根路径下  使用javah 生成头文件

    cd D:\component\component\coding_tx\target\classes

    javah -jni com.temp.common.jni.MyJniMethod  
    
    即在class目录下生成 com_temp_common_jni_MyJniMethod.h  的头文件
    
#3 新建  com_lin_myjin_MyJniMethod.c 文件
          此文件为c++  win32 的源文件

#4  安装visual studio  
    安装必要组件
    
#5  新建-->  visual C++--> window 桌面 -->  window 桌面向导 -->选择 dll 和空项目

   ## 配置项目     点击项目右键-->属性-->vc++目录-->包含目录--> 将java的inclub目录以及win32目录引入
        注意  上面选择平台时选择 x64位系统
       D:\jdk_install\jdk1.8\include
       D:\jdk_install\jdk1.8\include\win32 
#6启动测试， 注意java的类加载的dll文件名  防止出错

     public class MyJniMethod {
     
         static {
             System.loadLibrary("Project3");
         }
         


