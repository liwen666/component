package com.temp.common.base.classload.newhigh;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
 
public class MyClassLoader extends URLClassLoader {
 
	private  MyClassLoader loader = null;
    Date startDate = new Date();
	public MyClassLoader(URL[] urls) {
		super(urls);
	}


	public MyClassLoader(ClassLoader parent) {
		super(new URL[0], parent);
	}
 
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		super.close();
	}
	/**
	 * Adds a jar file from the filesystems into the jar loader list.
	 * 
	 * @param jarfile
	 *            The full path to the jar file.
	 * @throws MalformedURLException
	 */
	public void addJarFile(String jarfile) throws MalformedURLException {
		URL url = new URL("file:" + jarfile);
		addURL(url);
	}
	
	public void addDir(String path) throws MalformedURLException{
		path= "file:"+path;
		URL url = new URL(path);
		addURL(url);
	}
 
 
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + ",time:"+startDate.toLocaleString();
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		String fileName = getFileName(name);
		URL[] urLs = getURLs();
		File file = new File(urLs[0].getPath(),fileName);
//		File file = new File();

		try {
			FileInputStream is = new FileInputStream(file);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len = 0;
			try {
				while ((len = is.read()) != -1) {
					bos.write(len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			byte[] data = bos.toByteArray();
			is.close();
			bos.close();
			if("TestClassLoad".equals(name)){
				name="me.zhengjie.monitor.domain.TestClassLoad";
			}
			return defineClass(name,data,0,data.length);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return super.findClass(name);
	}
	//获取要加载 的class文件名
	private String getFileName(String name) {
		// TODO Auto-generated method stub
		int index = name.lastIndexOf('.');
		if(index == -1){
			return name+".class";
		}else{
			return name.replace(".","/")+".class";
		}
	}
	
}