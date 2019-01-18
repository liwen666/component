package com.temp.common.base.io.filesecurity;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import sun.security.util.Resources;

import javax.xml.transform.Source;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;

public class WriteFile {
    public static void main(String[] args) throws IOException {
        File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\filesecurity\\txt.tst");
        System.out.println(f.exists());
        System.out.println(f.isFile());
        System.out.println(f.isDirectory());
        System.out.println(f.isAbsolute());
        System.out.println(f.isHidden());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getPath());
        System.out.println(f.getCanonicalPath());
        if(!f.exists()){
            f.createNewFile();
        }
        ClassLoader classLoader = WriteFile.class.getClassLoader();
        System.out.println(classLoader.getResource("").getPath());
        System.out.println(classLoader.getResource("com/temp/common/base/io/filesecurity/WriteFile.class").getPath()+"-----------/");
        InputStream resourceAsStream = classLoader.getResourceAsStream("com/temp/common/base/io/filesecurity/WriteFile.class");
        System.out.println(resourceAsStream.markSupported()+"-----markSupported");
//      resourceAsStream.mark(1);
//      resourceAsStream.reset();
//        byte[] bt = new byte[1024];
//        int realbyte = 0;
//        int read = resourceAsStream.read(bt);
//        System.out.println(read);
//        System.out.println(bt);
//        System.out.println(new String(bt, "utf8"));
//        OutputStream os = new FileOutputStream(f,true);
        OutputStream os = new FileOutputStream(f);
//        os.write(bt);
//        Enumeration<URL> systemResources = classLoader.getSystemResources("com/temp/common");
        Enumeration<URL> systemResources = classLoader.getResources("com/temp/common/");
        URL resource = classLoader.getResource("com/temp/common/");

        String protocol = resource.getProtocol();
        System.out.println(protocol+"---------protocol");
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:com/temp/common/**/*");
        System.out.println(resources);
//        Enumeration<URL> systemResources = ClassLoader.getSystemResources("/com/temp/common");
//        Enumeration<URL> systemResources = ClassLoader.getSystemResources("com.temp.common");
        while (systemResources.hasMoreElements()){
            URL url = systemResources.nextElement();
            System.out.println(url);
            Resource s = new UrlResource(url);
            System.out.println(s.getFilename());
            System.out.println(s.getFile().getPath());
            System.out.println(s.getFile().getAbsoluteFile().getAbsolutePath()+"------");
            System.out.println(s.getFile().getAbsolutePath()+"------");
            String[] list = s.getFile().getAbsoluteFile().list();
            for(String str:list){
                System.out.println(str );
            }
        }


    }
}
