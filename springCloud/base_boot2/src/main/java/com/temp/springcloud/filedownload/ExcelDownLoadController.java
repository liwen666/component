package com.temp.springcloud.filedownload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/**/DownLoad/")
public class ExcelDownLoadController {
    @RequestMapping("/downExcel")
    public void download(String path, HttpServletResponse response) throws IOException {

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
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        out.write(content);
        out.flush();
        out.close();
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
