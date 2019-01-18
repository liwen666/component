package com.temp.springcloud.fileupload;

import com.temp.springcloud.fileupload.base.EncodingDetect;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping("/fileUpload")
    public String fileUpload() {
        logger.info("文件上传！");

        return "file";
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public void uploadFile(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 因为springboot已经对request做过处理  这里可以使用StandardMultipartHttpServletRequest
         */
        StandardMultipartHttpServletRequest requestStand = (StandardMultipartHttpServletRequest) req;
        HttpServletRequest request = requestStand.getRequest();
        /**********************************************************************************/
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = "D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload/WEB-INF/upload";
        //上传时生成的临时文件保存目录
        String tempPath = "D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload/WEB-INF/temp";
        File file = new File(tempPath);
        Map<String, MultipartFile> fileMap = requestStand.getFileMap();
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("目录或文件不存在！");
            file.mkdirs();
        }
        for (Map.Entry<String, MultipartFile> fileEntry : fileMap.entrySet()) {
            //如果fileitem中封装的是普通输入项的数据
            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
            System.out.println(fileEntry.getKey() + "页面的  input   name");
            String fileName = fileEntry.getValue().getOriginalFilename();
            System.out.println("fileName-----getOriginalFilename" + fileName);
            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
            fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
            //得到上传文件的扩展名
            String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if ("zip".equals(fileExtName) || "rar".equals(fileExtName) || "tar".equals(fileExtName) || "jar".equals(fileExtName)) {
                request.setAttribute("message", "上传文件的类型不符合！！！");
                request.getRequestDispatcher("/message.jsp").forward(request, response);
                return;
            }
            //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
            System.out.println("上传文件的扩展名为:" + fileExtName);
            //获取item中的上传文件的输入流
            InputStream is = fileEntry.getValue().getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bis.mark(0);
            byte [] cache = new byte[100];
            bis.read(cache);
            bis.reset();
            String javaEncode = EncodingDetect.getJavaEncode(cache);
            System.out.println("网络传输的文件编码是"+javaEncode);

//            InputStreamReader isr = new InputStreamReader(is, "gb2312");
            InputStreamReader isr = new InputStreamReader(bis,javaEncode);
            //得到文件保存的名称
            fileName = mkFileName(fileName);
            //得到文件保存的路径
            String savePathStr = mkFilePath(savePath, fileName);
            System.out.println("保存路径为:" + savePathStr);
            //创建一个文件输出流
            FileOutputStream fos = new FileOutputStream(savePathStr + File.separator + fileName);
//            OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf8");
            //创建一个缓冲区
            char buffer[] = new char[1024];
            //判断输入流中的数据是否已经读完的标识
            int length = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while ((length = isr.read(buffer)) > 0) {
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                osw.write(buffer, 0, length);
            }
            //关闭输入流
            isr.close();
            is.close();
            //关闭输出流
            osw.close();
            fos.close();
            //删除处理文件上传时生成的临时文件
        }
        //        response.setContentType("text/json;charset=utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");

        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "成功上传" +
                    "<body>\n" +
                    "hello\n" +
                    "</body>\n" +
                    "</html>");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        uploadFile(request, response);
    }

    //生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
    public String mkFileName(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    public String mkFilePath(String savePath, String fileName) {
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = fileName.hashCode();
        int dir1 = hashcode & 0xf;
        int dir2 = (hashcode & 0xf0) >> 4;
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }
}
