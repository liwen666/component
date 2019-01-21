#JAR项目不能使用File来读取文件，使用spring提供的ResourcePatternResolver
## security   配置 user lw pass lw
  http://localhost:8000/login
  在启动类上加上注解  不加载哪些模块
## spring-boot的启动程序要加上@RestController才能提供rest服务
  
##jar项目不能使用File    = new  File（path）的方式

##IO  使用spring的Resource 获取资源文件名称  获取资源流。然后对流进行读写

  Jar项目如何操作文件流
  使用classpath*:  加载文件
  localhost:8000/initSqlScript/initSqlScriptJarSource/
##文件上传  http://localhost:8000/file/fileUpload

##中文乱码
  首先获取到文件的byte  将byte根据编码规则转换为String 
  在由string获取byte根据指定的编码规则，然后中文就会变成新的编码 
##上传图片无法打开
   通过工具获取图片编码方式，以相同的编码写入一个新的文件即可  
##文件下载  http://localhost:8000/DownLoad/downFile
  文件的名字必须使用iso8859-1编码  否则中文文件名称无法显示
##EXCEL下载    http://localhost:8000/DownLoad/downExcel
  
  

