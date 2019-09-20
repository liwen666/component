# 使用Dcpay_dao多数据源0.0.3-SNAPSHOT版本规范

## 1、本地新建文件夹，在项目所在盘符下新建/home/deploy/config
## 2、将本项目中的db.properties文件粘贴纸该目录中
## 3、修改jdbc.properties文件配置
### 例：
    db.default.database=BASE                                    项目默认数据库
    db.filePath=/home/deploy/config/db.properties               db.properties文件位置
    db.database=base,h2,merchant                                项目允许连接数据库用,号隔开
    db.PerformanceInterceptor=true                              保持原有默认
    db.minIdle=5                                                保持原有默认
    db.initialSize=10                                           保持原有默认
    db.maxActive=100                                            保持原有默认
    db.under_score_to_camel_case=true                           保持原有默认
    mybatis.mapper.path=classpath*:/mapper/**/*Mapper.xml       保持原有默认
