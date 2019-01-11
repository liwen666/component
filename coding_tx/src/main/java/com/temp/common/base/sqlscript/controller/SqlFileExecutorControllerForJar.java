package com.temp.common.base.sqlscript.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * 读取 SQL 脚本并执行
 *
 * @author Unmi
 */
@Controller
@RequestMapping("/**/initSqlScript")
public class SqlFileExecutorControllerForJar {
    @Autowired
    private DataSource source;
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlFileExecutorControllerForJar.class);

    /**
     * 初始化脚本
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/initSqlScriptJarSource")
    public void initSqlScriptSource(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("");

        URL resource = this.getClass().getResource("");
        String dataPath = resource.getPath().substring(0, resource.getPath().lastIndexOf("/", resource.getPath().length() - 2)) + "/";
        System.out.println(dataPath);
        List<String> sqlFilePath = new ArrayList<String>();
        String msg = "升级成功！";
        boolean flag = true;
        System.out.println("根据不同业务系统可以定制不同的初始化以及升级脚本   " );
        System.out.println( "区分业务系统推荐以下几个方案： ");
        System.out.println( "1：业务系统设置系统标识全局变量 ");
        System.out.println( "2：通过编码设置静态常两，根据业务系统手动修改 ");
        System.out.println( "3：web.xml配置工作流监听器 ");
        System.out.println( "4：通过web一级目录来匹配 ");
        try {
            getFiles(dataPath, sqlFilePath);
//            getFiles(resourceAsStream,sqlFilePath);
            sortFiles(sqlFilePath);
            System.out.println(sqlFilePath);
            for (String s : sqlFilePath) {
                String sqlSource = null;
                String appidFromeFilePath = getAppidFromeFilePath(s);
                if(appidFromeFilePath.equals("hqbpmn")){
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("===========执行脚本文件：{} =========== ", s);
                    }
                    try {
                        sqlSource = loadSqlSource(s);
                        executeSource(sqlSource);
                    } catch (Exception e) {
                        LOGGER.error("==========执行脚本文件出错：{} =========== ", s.substring(s.lastIndexOf("\\") + 1));
                        msg = "脚本文件：" + s.substring(s.lastIndexOf("\\") + 1) + "    执行失败！" + "错误详细信息：" + e.getMessage();
                        flag = false;
                    }
                }

            }

        } catch (Exception e) {
            LOGGER.error("==========解析脚本文件路径出错!请检查脚本文件名格式是否正确==========");
            flag = false;
            msg = "解析脚本文件路径出错!请检查脚本文件名格式是否正确!";
            e.printStackTrace();
        }
    }

    public void sortFiles(List<String> sqlFilePath) throws Exception {
        List<String> files = new ArrayList<String>();
        int setValueCount = 0;
        // 从数组第二个元素开始排序，因为第一个元素本身肯定是已经排好序的
        for (int j = 1; j < sqlFilePath.size(); j++) {// 复杂度 n
            // 保存当前值
            String key = sqlFilePath.get(j);
            int index = binarySearchAsc(sqlFilePath, sqlFilePath.get(j), 0, j - 1);// 复杂度：O(logn)
            // 将目标插入位置，同时右移目标位置右边的元素
            for (int i = j; i > index; i--) {
                sqlFilePath.set(i, sqlFilePath.get(i - 1));
                setValueCount++;
            }
            sqlFilePath.set(index, key);
            setValueCount++;
        }
//        System.out.println("\n 设值次数(setValueCount)=====> " + setValueCount);
    }

    /**
     * 二分查找 升序 递归
     *
     * @param sqlFilePath 给定已排序的待查数组
     *                    <p>
     *                    查找目标
     * @param from        当前查找的范围起点
     * @param to          当前查找的返回终点
     * @return 返回目标在数组中，按顺序应在的位置
     */
    private int binarySearchAsc(List<String> sqlFilePath, String target, int from, int to) {
        int range = to - from;
        // 如果范围大于0，即存在两个以上的元素，则继续拆分
        if (range > 0) {
            // 选定中间位
            int mid = (to + from) / 2;
            if (formatFilePathToNum(sqlFilePath.get(mid)) > formatFilePathToNum(target)) {
                return binarySearchAsc(sqlFilePath, target, from, mid - 1);
            } else {
                return binarySearchAsc(sqlFilePath, target, mid + 1, to);
            }
        } else {
            if (formatFilePathToNum(sqlFilePath.get(from)) > formatFilePathToNum(target)) {
                return from;
            } else {
                return from + 1;
            }
        }
    }

    private int formatFilePathToNum(String str) {
        return Integer.parseInt(str.substring(str.lastIndexOf("\\") + 1).split("_")[0]);
    }
    private String getAppidFromeFilePath(String str) {
        return str.substring(str.lastIndexOf("\\") + 1).split("_")[1];
    }
    private void executeSource(String sqlSource) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = source.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String[] split = sqlSource.split(";");
//            String[] split = sqlSource.split("/\\|/");oracle
            for (String sql : split) {
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.error("---------执行的错误sql是:{}-----------", sql);
                    }
                    throw e;
                }
            }
            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            stmt.close();
            conn.close();
        }
    }

    private String loadSqlSource(String sqlFile) throws Exception {
        InputStream sqlFileIn = null;

        try {
            sqlFileIn = new FileInputStream(sqlFile);
            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead, "utf8"));
            }
            String sqlArr = sqlSb.toString();
            String source = sqlArr.replaceAll("--.*", "").trim();
            return source;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            sqlFileIn.close();
        }
    }

    private void getFiles(String path, List<String> sqlFilePath) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("---------目录:{}-----------", files[i].getPath());
                    }
                    getFiles(files[i].getPath(), sqlFilePath);
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("---------文件:{}-----------", files[i].getPath());
                    }
                    if (files[i].getPath().endsWith(".sql")) {
                        sqlFilePath.add(files[i].getPath());
                    }
                }

            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("---------sql文件:{}-----------", file.getPath());
            }
            if (file.getPath().endsWith(".sql")) {
                sqlFilePath.add(file.getPath());
            }
        }
    }
    private void getFiles(InputStream path, List<String> sqlFilePath) {

//        File file = new File(path);

    }
}