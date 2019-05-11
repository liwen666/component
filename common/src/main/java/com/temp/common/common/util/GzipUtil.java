package com.temp.common.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

public class GzipUtil {
	
	public static List  fileList = new ArrayList(); 
	
	private static org.w3c.dom.Document docc;

	private static DocumentBuilder builder;
	// private String selectedFile;//写xml加的改的
	private static File f;
	private static Document document;
	private static String userdir = System.getProperty("user.dir"); 
 	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
	    execGzipUtil();
	}
	
	public static void execGzipUtil() throws Exception{
        String basePath = userdir+"/common\\src\\main\\java\\com\\temp\\common\\common\\util\\js";
        List<String> oraginFileList = new ArrayList();  
        List<String> gzFileList = new ArrayList();
        File baseFolder = new File(basePath);
        getDirectory(baseFolder);   
        //记录文件版本号 
        String xmlVersionPath = userdir+"/common\\src\\main\\java\\com\\temp\\common\\common\\util/jsversion.xml";
        Document doc = openXmlFile(xmlVersionPath); 
        Element rootElm = doc.getRootElement();
        
        for(int i=0;i<fileList.size();i++){
              File f = (File) fileList.get(i);
              String fileName = f.getName();
              String path = f.getAbsolutePath();
              String parentPath = f.getParentFile().getAbsolutePath(); 
              String prefix=fileName.substring(fileName.lastIndexOf(".")+1); 
              if(prefix.equals("js")/*||prefix.equals("css")*/){
                  oraginFileList.add(path);
                  String newfileName = f.getName().substring(0,f.getName().lastIndexOf(".")); 
                  gzFileList.add(parentPath.replace("\\js","\\gzjs")+"\\"+newfileName+".gz"+prefix);
                  //创建文件
                  File file = new File(parentPath+newfileName+".gz"+prefix); 
                  if(file.exists()){
                      file.createNewFile();  
                  } 
              }
         }
        for (int i = 0; i < oraginFileList.size(); i++) {
            String filename = oraginFileList.get(i);
            //判断文件是否存在于xml中
            File file = new File(filename); 
            FileInputStream fis = new FileInputStream(file);
            File outFile = new File(gzFileList.get(i));
            FileOutputStream fos = new FileOutputStream(outFile);
            SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            
            //改成相对路径
             filename = filename.replace(userdir+"\\common\\src\\main\\java\\", "");
            Element fileElem = getElementFormXml(rootElm,filename);
            Date lastModified = new Date(file.lastModified());
            String md5N = MD5FileUtil.getFileMD5String(file);
            if(fileElem==null){  //不存在 
                Element elem = rootElm.addElement("file");
                elem.addAttribute("filename",filename);
                elem.addAttribute("lastmodify", ss.format(lastModified));   
                elem.addAttribute("version", "1");  
                elem.addAttribute("size", ""+file.length());   
                elem.addAttribute("md5", ""+md5N);
            }else{
                int elemntSize =Integer.parseInt(fileElem.attributeValue("size"));
                int version =Integer.parseInt(fileElem.attributeValue("version")); 
                String md5O = fileElem.attributeValue("md5");
                if(md5O==null||!md5O.equals(md5N)){       //文件大小不相同
                    version +=1; 
                     fileElem.attribute("version").setValue(""+version);   
                     fileElem.attribute("size").setValue(""+file.length()); 
                     fileElem.attribute("lastmodify").setValue(ss.format(lastModified));
                     if(md5O==null){
                         fileElem.addAttribute("md5", md5N);  
                     }else{
                         fileElem.attribute("md5").setValue(md5N);
                     }
                }
            }  
            GZIPOutputStream gos = new GZIPOutputStream(fos);
            int count;  
            byte data[] = new byte[100];  
            while ((count = fis.read(data, 0, 100)) != -1) {  
                gos.write(data, 0, count);  
            }  
            fis.close();
            gos.finish();   
            gos.flush();  
            gos.close(); 
            fos.flush();
            fos.close();
        }
        strChangeXML(xmlVersionPath,doc.asXML());
        
        //刷js文件
        String jsmoduleversion = userdir+"/common\\src\\main\\java\\com\\temp\\common\\common\\util\\config.version.js";
        File file = new File(jsmoduleversion);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fWriter);  
        writer.write("Common.ns(\"Common.modules.version\");");
        writer.write("Common.modules.version = ");
        List fileElemnts = rootElm.elements("file");
        JSONObject json = new JSONObject();
        for(int i=0;i<fileElemnts.size();i++){
            Element elem = (Element) fileElemnts.get(i);
            Map map  = new HashMap();
            String filename  = elem.attributeValue("filename").replace("\\", "/");
            String newfileName = filename.substring(0,filename.lastIndexOf(".")); 
            if(filename.substring(filename.lastIndexOf(".")).equals(".js")){
                map.put(newfileName+".gzjs", elem.attributeValue("version"));
            }
            if(filename.substring(filename.lastIndexOf(".")).equals(".css")){
                map.put(newfileName+".gzcss", elem.attributeValue("version")); 
            }
            //writer.write("\""+elem.attributeValue("filename")+"\":\""+elem.attributeValue("version")+"\","); 
            json.putAll(map);  
        }
        System.out.println("恭喜！压缩成功");  
        writer.write(json.toString());  
        writer.flush();
        fWriter.flush();
        fWriter.close();
        writer.close(); 
    }

    public static Element getElementFormXml(Element root,String filename){
		List elements = root.elements("file"); 
		for(int i= 0;i<elements.size();i++){
			Element elem = (Element)elements.get(i); 
			if(elem.attributeValue("filename").equals(filename)){
				return elem;    
			}
		}
		return null; 
	} 
	
	public static void strChangeXML(String fileName,String str) throws IOException {
		   SAXReader saxReader = new SAXReader();
		   Document document;
		   try {
			    //document = saxReader.read(new ByteArrayInputStream(str.getBytes("utf-8")));   
			   document = DocumentHelper.parseText(str); 
			   Element rootElement = document.getRootElement();
			   String getXMLEncoding   =   document.getXMLEncoding();
			   String rootname   =   rootElement.getName();
			   OutputFormat format = OutputFormat.createPrettyPrint();
			    /** 指定XML字符集编码 */
			   format.setEncoding("utf-8");
			    /** 将document中的内容写入文件中 */
			   XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(fileName),format);  
			   xmlWriter.write(document);
			   xmlWriter.close();
		   } catch (DocumentException e) {
		    // TODOAuto-generatedcatchblock
		    e.printStackTrace();
		   }
	}
	
	
	public synchronized static Document openXmlFile(String configFilePath){
	   File f = new File(configFilePath);
	   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   try {
		   builder = factory.newDocumentBuilder();
	   } catch (ParserConfigurationException e1) {
		   e1.printStackTrace();
	   }
	   try {
		   //BufferedInputStream和BufferedOutputStream只是在操作对应的方法之前，动态地为它们加上一些额外功能(像缓冲区功能)，
		   BufferedInputStream in= new BufferedInputStream(new FileInputStream(f));
		   docc = builder.parse(in);
	   } catch (SAXException e1) {
		   e1.printStackTrace();
	   } catch (IOException e1) {
	    // TODO 自动生成 catch 块
		   e1.printStackTrace();
	   }
	   Document document = buildDocment(docc);
	   return document;
	}
	private static Document buildDocment(org.w3c.dom.Document domDocument) {
        DOMReader xmlReader = new DOMReader();
        return xmlReader.read(domDocument);
	}

	private static void getDirectory(File file) {
		 File flist[] = file.listFiles();
		 if (flist == null || flist.length == 0) {
		 }
		 for (File f : flist) {
		     if (f.isDirectory()) {
		         System.out.println("Dir==>" + f.getAbsolutePath()); 
		         getDirectory(f);
		     } else {
		   	  fileList.add(f);
		     }
		 }
	}	  
	
}
