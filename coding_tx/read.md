##测试提示找不到类加载路径
  1.可以修改累的启动目录
  2.对项目进行build
  
 ##http    302 重定向  400  请求失败  405 方法不允许   406 返回数据浏览器无法解析
 
 使用post  提交请求是，接收参数最好用 @RequestBody Map<String ,Object>  否则会遇到400  失败请求  
 
 #tomcat后台启动
  setclasspath.bat  将  里面的java.exe  改成 javaw.exe  即可
  
  
  java有一种map  currentMap  这种map无法通过参数进行修改值   map传入到方法体进行的一系列操作都会回归