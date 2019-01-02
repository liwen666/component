
package com.temp.common.base.h2;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
 


import java.sql.Statement;
import java.util.UUID;

import javax.sql.DataSource;

import com.temp.common.base.springannotation.base.ScanClass;
import org.h2.tools.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


class H2webTest{
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
//        annotationConfigApplicationContext.register(H2webTest.class);
//        annotationConfigApplicationContext.refresh();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/temp/common/base/h2/application-context.xml");


    }
}



public class App {
 
    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {
 
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "com\\temp\\common\\base\\h2\\application-context.xml");
 
       DataSource dbsource = (DataSource) context.getBean("h2dataSource");
       Connection connection = dbsource.getConnection();
       Statement stmt = connection.createStatement();
       //如果存在USER_INFO表就先删除USER_INFO表
//       stmt.execute("DROP TABLE IF EXISTS USER_INFO");
       //创建USER_INFO表
       stmt.execute("CREATE TABLE USER_INFO(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100),sex VARCHAR(4))");
       //新增
       stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','大日如来','男')");
       stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','青龙','男')");
       stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','白虎','男')");
       stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','朱雀','女333')");
       stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','玄武','男4')");
       stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','苍狼','160')");
       //删除
       stmt.executeUpdate("DELETE FROM USER_INFO WHERE name='大日如来'");
       //修改
       stmt.executeUpdate("UPDATE USER_INFO SET name='孤傲苍狼' WHERE name='苍狼'");
       //查询
       ResultSet rs = stmt.executeQuery("SELECT * FROM user_info");
       
       //遍历结果集
       while (rs.next()) {
           System.out.println(rs.getString("id") + "," + rs.getString("name")+ "," + rs.getString("sex"));
       }
     //释放资源
       stmt.close();
       //关闭连接
//       connection.close();
        //此处不关闭可以通过页面访问H2
        ((AbstractApplicationContext) context).close();
 
    }
 
}
