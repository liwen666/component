/**
 * 
 */
package h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
/**
 * 这种方式连接不受客户端锁控制
 * @author pc
 *
 */
public class TestH2Tcp {
    //数据库连接URL，通过使用TCP/IP的服务器模式（远程连接），当前连接的是E:/H2目录下的gacl数据库
    //private static final String JDBC_URL = "jdbc:h2:tcp://localhost/E:/H2/gacl";
    //private static final String JDBC_URL = "jdbc:h2:tcp://127.0.0.1/E:/H2/gacl";;MVCC=FALSE
//    private static final String JDBC_URL = "jdbc:h2:tcp://127.0.0.1/c:/Users/pc/Desktop/独立部署工作流/h2db/testbpmn";
//    private static final String JDBC_URL = "jdbc:h2:tcp://127.0.0.1:8044/c:/Users/pc/Desktop/独立部署工作流/h2db/testbpmn";
//		private static final String JDBC_URL = "jdbc:h2:mem:testbpmn";
	 private static final String JDBC_URL = "jdbc:h2:tcp://192.168.9.180:8043/mem:testbpmn";
//    private static final String JDBC_URL = "jdbc:h2:tcp://127.0.0.1/C:/Users/pc/Desktop/独立部署工作流/h2db/testbpmn";
    //连接数据库时使用的用户名
    private static final String USER = "gacl";
    //连接数据库时使用的密码
    private static final String PASSWORD = "123";
    //连接H2数据库时使用的驱动类，org.h2.Driver这个类是由H2数据库自己提供的，在H2数据库的jar包中可以找到
    private static final String DRIVER_CLASS="org.h2.Driver";
    
    public static void main(String[] args) throws Exception {
        // 加载H2数据库驱动
        Class.forName(DRIVER_CLASS);
        // 根据连接URL，用户名，密码获取数据库连接
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        //如果存在USER_INFO表就先删除USER_INFO表
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM act_hq_tem_def");
        
        //遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name"));
        }
        //不可以连续执行两次查询，否则报object close错误
//        ResultSet rs2 = stmt.executeQuery("SELECT * FROM act_hq_tem_def");
//        while (rs2.next()) {
//            System.out.println(rs2.getString("id") + "," + rs2.getString("APP_ID_"));
//        }
        //释放资源
        stmt.close();
        //关闭连接
        conn.close();
    }
//    public static void main(String[] args) throws Exception {
//    	// 加载H2数据库驱动
//    	Class.forName(DRIVER_CLASS);
//    	// 根据连接URL，用户名，密码获取数据库连接
//    	Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
//    	Statement stmt = conn.createStatement();
//    	//如果存在USER_INFO表就先删除USER_INFO表
//    	stmt.execute("DROP TABLE IF EXISTS USER_INFO");
//    	//创建USER_INFO表
//    	stmt.execute("CREATE TABLE USER_INFO(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100),sex VARCHAR(4))");
//    	//新增
//    	stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','大日如来','男')");
//    	stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','青龙','男')");
//    	stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','白虎','男')");
//    	stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','朱雀','女333')");
//    	stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','玄武','男4')");
//    	stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','苍狼','99')");
//    	//删除
//    	stmt.executeUpdate("DELETE FROM USER_INFO WHERE name='大日如来'");
//    	//修改
//    	stmt.executeUpdate("UPDATE USER_INFO SET name='孤傲苍狼' WHERE name='苍狼'");
//    	//查询
//    	ResultSet rs = stmt.executeQuery("SELECT * FROM user_info");
//    	
//    	//遍历结果集
//    	while (rs.next()) {
//    		System.out.println(rs.getString("id") + "," + rs.getString("name")+ "," + rs.getString("sex"));
//    	}
//    	//不可以连续执行两次查询，否则报object close错误
////        ResultSet rs2 = stmt.executeQuery("SELECT * FROM act_hq_tem_def");
////        while (rs2.next()) {
////            System.out.println(rs2.getString("id") + "," + rs2.getString("APP_ID_"));
////        }
//    	//释放资源
//    	stmt.close();
//    	//关闭连接
//    	conn.close();
//    }
}