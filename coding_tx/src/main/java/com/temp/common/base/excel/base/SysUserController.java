package com.temp.common.base.excel.base;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.temp.common.base.excel.SysUser;


import net.sf.json.JSONObject;

/**
 * 后台用户-controller
 * @author libo
 */
public class SysUserController {
/**
     * 导出系统用户数据
     * @throws IOException
     */
    public void exportSysUsers() throws IOException{
        List<SysUser> userList = new ArrayList<SysUser>();
        SysUser s1 = new SysUser();
        s1.setEmail("534093268@qq.com");
        s1.setFirstName("lw");
        s1.setStatus(1);
        userList.add(s1);

        String fileName="系统用户表-";
        //填充projects数据
        List<Map<String,Object>> list=createExcelRecord(userList);
        String columnNames[]={"姓名", "邮箱" ,"状态" };//列名
//        String columnNames[]={"姓名", "性别", "邮箱", "电话", "部门", "角色", "状态", "创建时间"};//列名
        String keys[] = {"firstName", "email",  "status"};//map中的key
//        String keys[] = {"name", "gender", "email", "phone", "department", "role", "status", "createTime"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        os.close();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
//        res.reset();
//        res.setContentType("application/vnd.ms-excel;charset=utf-8");
//        res.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//        ServletOutputStream out = res.getOutputStream();
        File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\excel\\base\\lw.xls");
        if(f.exists()){f.createNewFile();}
        FileOutputStream fo = new FileOutputStream(f);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(fo);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {

            if (bis != null)
                bis.close();

            if (bos != null)
                bos.close();
            if (is != null)
                is.close();
            if (fo != null)
                fo.close();
        }
    }
//    /*
//     * 导入系统用户数据
//     * @throws IOException
//     */
//    public void InputSysUsers() throws IOException{
//        List<SysUser> userList = new ArrayList<SysUser>();
//        SysUser s1 = new SysUser();
//        s1.setEmail("534093268@qq.com");
//        s1.setFirstName("lw");
//        s1.setStatus(1);
//        userList.add(s1);
//
//        String fileName="系统用户表-";
//        //填充projects数据
//        List<Map<String,Object>> list=createExcelRecord(userList);
//        String columnNames[]={"姓名", "邮箱" ,"状态" };//列名
////        String columnNames[]={"姓名", "性别", "邮箱", "电话", "部门", "角色", "状态", "创建时间"};//列名
//        String keys[] = {"firstName", "email",  "status"};//map中的key
////        String keys[] = {"name", "gender", "email", "phone", "department", "role", "status", "createTime"};//map中的key
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        byte[] content = os.toByteArray();
//        os.close();
//        InputStream is = new ByteArrayInputStream(content);
//        // 设置response参数，可以打开下载页面
////        res.reset();
////        res.setContentType("application/vnd.ms-excel;charset=utf-8");
////        res.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
////        ServletOutputStream out = res.getOutputStream();
//        File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\excel\\base\\lw.xls");
//        if(f.exists()){f.createNewFile();}
//        FileOutputStream fo = new FileOutputStream(f);
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        try {
//            bis = new BufferedInputStream(is);
//            bos = new BufferedOutputStream(fo);
//            byte[] buff = new byte[2048];
//            int bytesRead;
//            // Simple read/write loop.
//            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//                bos.write(buff, 0, bytesRead);
//            }
//        } catch (final IOException e) {
//            throw e;
//        } finally {
//            if (is != null)
//                is.close();
//            if (fo != null)
//                fo.close();
//            if (bis != null)
//                bis.close();
//
//            if (bos != null)
//                bos.close();
//        }
//    }

    public static void main(String[] args) throws IOException {
        SysUserController sysUserController = new SysUserController();
        sysUserController.exportSysUsers();
    }
    
    /**
     * 生成Excel数据
     * @param userList
     * @return
     */
    private List<Map<String, Object>> createExcelRecord(List<SysUser> userList) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        SysUser user = null;
        for (int j = 0; j < userList.size(); j++) {
            user = userList.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("firstName", user.getFirstName());
//            mapValue.put("gender", (user.()==1) ? "男":((user.getGender()==0) ? "女" : "保密"));
            mapValue.put("email", user.getEmail());
//            mapValue.put("phone", user.getPhone());
//            mapValue.put("department", user.getDepartment().getName());
//            mapValue.put("role", user.getRole().getName());
            mapValue.put("status", user.getStatus()==1 ? "启用" : "禁用");
//            mapValue.put("createTime", user.getCreateTime().substring(0, 19));
            listmap.add(mapValue);
        }
        return listmap;
    }
    
    
}