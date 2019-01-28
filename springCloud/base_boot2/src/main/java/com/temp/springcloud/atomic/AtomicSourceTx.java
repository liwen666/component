package com.temp.springcloud.atomic;

import javax.sql.DataSource;
import java.sql.*;
import java.util.concurrent.atomic.AtomicStampedReference;

import static org.hibernate.cfg.AvailableSettings.USER;
import static org.hibernate.jpa.AvailableSettings.JDBC_URL;

/**
 * AtomicStampReference  的测试  模拟
 * <p>
 * 为数据的修改加上时间戳 防止CAS 操作有误
 * 例如  冲10元钱    两个线程   钱如果被花掉就会充值两次   0-->10---->0---->10
 * 两个0不是同一个   只能用时间戳区分
 * <p>describe</p>
 * <p>Atomic1.java</p>
 * <p></p>
 *
 * @author lw
 * @version 1.0
 * @date 2017年1月10日
 * @link
 */

public class AtomicSourceTx {
    public static AtomicStampedReference<Integer> mark = new AtomicStampedReference<Integer>(0, 100);//第一个值是要操作的值，第二个是时间戳的初始值

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {
        // 加载H2数据库驱动
        Class.forName("org.h2.Driver");
        // 根据连接URL，用户名，密码获取数据库连接
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://127.0.0.1:8043/mem:testbpmn", "root", "");
        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM act_id_user");
//        while (rs.next()) {
//            System.out.println(rs.getString("id_") + "," + rs.getString("first_"));
//        }
//        stmt.execute("insert into act_id_user (id_,first_) values (198718,'李文')");
        stmt.close();
        conn.close();
        Integer num = mark.getReference();
        for (int i = 0; i < 20; i++) {
            /**
             * 此处的timestamp的值是根据  money获得的 所有值是可以变化的
             */
            int timestamp = mark.getStamp();
            System.out.println("timestamp-----" + timestamp);
            new Thread() {
                public void run() {
                    System.out.println("修改数据库开始---------线程是： " + Thread.currentThread().getName());

                    System.out.println(num);
                    if (mark.compareAndSet(num, num + 1, timestamp, timestamp + 1)) {

                        System.out.println("修改成功！");
                    } else {
                        System.out.println("任务已经被办理！");
                    }
                    ;

                }
            }.start();


        }
        Thread.sleep(1000);
        System.out.println("主线程结束");

        int timestamp = mark.getStamp();
        System.out.println("timestamp-----" + timestamp);
    }


}
