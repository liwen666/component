package com.temp.springcloud.atomic;

import java.sql.*;
import java.util.concurrent.atomic.AtomicStampedReference;

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

public class AtomicStringSourceTx {
    public static AtomicStampedReference<String> mark = new AtomicStampedReference<String>("z", 100);//第一个值是要操作的值，第二个是时间戳的初始值

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
        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        updateData("lw"+Thread.currentThread().getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
        System.out.println(mark.getReference());
    }
    static void updateData(String name) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        // 根据连接URL，用户名，密码获取数据库连接
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://127.0.0.1:8043/mem:testbpmn", "root", "");
        Statement stmt = conn.createStatement();

        ResultSet resultSet = stmt.executeQuery("select * from act_id_user where id_='198718'");
        resultSet.next();
        String targetname = resultSet.getString("first_");
//        if(targetname.equals("lwThread-6")){
//            System.out.println("数据没变");
//        }else {
//            System.out.println("数据变了");
//        }
        String status_ = resultSet.getString("status_");
        System.out.println();
        String target = mark.getReference();
        System.out.println(target+"-------------");
        if(status_.equals("0")){
            int stamp = mark.getStamp();
//            mark.set(targetname,100);
            if (mark.compareAndSet(target, target+1, stamp, stamp + 1)) {
                stmt.execute("update   act_id_user set status_='0' ,first_='"+name+"' where id_='198718' ");

                System.out.println("修改成功！!!!!!");

            } else {
                System.out.println(target+"-------------");
                System.out.println("任务已经被办理！");
            }
        }

        stmt.close();
        conn.close();

    }


}
