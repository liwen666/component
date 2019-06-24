//package com.temp.common.common.util;
//
//import com.alibaba.fastjson.JSON;
//import com.hq.bpmn.templatedef.bean.BpmnCode;
//import org.junit.Test;
//
//import java.lang.reflect.Field;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class DataBaseConversion {
//
//    @Test
//    public void getData() throws Exception {
//        Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
//        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.81:1521:orcl", "FASP_90", "FASP_90");
//        System.out.println(connection + "000");
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("select ft.appname as name ,ft.appid as value from fw_t_sysapp ft");
//        List<Map<String,String>> category = new ArrayList<>();
//        while (resultSet.next()) {
//            String name = resultSet.getString("name");
//            String value = resultSet.getString("value");
//            Map map = new HashMap(1);
//            map.put("name",name);
//            map.put("value",value);
//            category.add(map);
//        }
//        System.out.println(JSON.toJSONString(category));
//    }
//    @Test
//    public void ACT_HQ_CODE() throws Exception {
//        Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
//        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "parent_service", "1");
//        System.out.println(connection + "000");
//        Statement statement = connection.createStatement();
//
//        List<BpmnCode> list = new ArrayList<>();
//        ResultSet resultSet = statement.executeQuery("select id,CODE_KEY as codeKey,CODE_NAME as codeName,SUPER_ID as superId,CODE_TYPE as codeType ,ORDER_ID as orderId ,DEPT_ID as deptId from ACT_HQ_CODE  where code_type='taskDescription'");
//        while (resultSet.next()) {
//            BpmnCode bc = new BpmnCode();
//            Field[] declaredFields = bc.getClass().getDeclaredFields();
//            for(Field f :declaredFields){
//                f.setAccessible(true);
//                if(f.getType().getTypeName().equalsIgnoreCase("java.lang.Integer")){
//                    f.set(bc,resultSet.getInt(f.getName()));
//                    continue;
//                }
//                f.set(bc,resultSet.getString(f.getName()));
//            }
//            list.add(bc);
//        }
//        System.out.println(JSON.toJSONString(list));
//    }
//
//    @Test
//    public void bpmnType() throws Exception {
//        Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
//        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "parent_service", "1");
//        System.out.println(connection + "000");
//        Statement statement = connection.createStatement();
//
//        List<BpmnCode> list = new ArrayList<>();
//        ResultSet resultSet = statement.executeQuery("select ID as id,BUTTON_KEY as codeKey,BUTTON_NAME as codeName,BUTTON_ORDER as orderId ,BUTTON_TYPE as codeType from ACT_HQ_CUSTOM_BUTTON t");
//        while (resultSet.next()) {
//            BpmnCode bc = new BpmnCode();
//            Field[] declaredFields = bc.getClass().getDeclaredFields();
//            for(Field f :declaredFields){
//                f.setAccessible(true);
//                if(f.getType().getTypeName().equalsIgnoreCase("java.lang.Integer")){
//                    f.set(bc,resultSet.getInt(f.getName()));
//                    continue;
//                }
//                try {
//                    f.set(bc,resultSet.getString(f.getName()));
//                } catch (IllegalArgumentException e) {
//                } catch (IllegalAccessException e) {
//                } catch (SQLException e) {
//                }
//            }
//            list.add(bc);
//        }
//        System.out.println(JSON.toJSONString(list));
//        Map<String, List<BpmnCode>> collect = list.stream().collect(Collectors.groupingBy(BpmnCode::getCodeType));
//        System.out.println(JSON.toJSONString(collect));
//        System.out.println("======================================================");
//        List<BpmnCode> bpmnCodes = collect.get("4");
//        System.out.println(JSON.toJSONString(bpmnCodes));
//    }
//}