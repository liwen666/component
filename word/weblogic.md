找到weblogic下的/common/bin目录，/home/weblogic/Oracle/Middleware/wlserver_10.3/common/bin

./config.sh -mode=console（用控制台模式安装）

（1）选择“创建新的Weblogic域”，输入“1”，按回车；

（3）根据自己需要来选择模板，这里选的是第一个；

（4）给domain命名；

（6）设置名称和口令，字母数字，例如weblogic  选择编号操作


输入错误按 ctrl+u
weblogic  登录错误用户被锁定  解决方式

    1.删除domain下的edit.lok
    2.删除config下的config.lok
    3.删除service下的服务器里面的文件夹  只保留security文件夹