package com.temp.common.java8.javascript;

import com.temp.common.base.util.PackageScanUtil;
import org.springframework.core.io.Resource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class Java_Script {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName( "JavaScript" );

        System.out.println( engine.getClass().getName() );
        System.out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );
    }
}
class ScriptFile{
    public static void main(String[] args) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Resource[] resource = PackageScanUtil.findResource("com.temp.common.java8.javascript");

        for(Resource r:resource){
            if(r.getFilename().equalsIgnoreCase("base64.js")){
                System.out.println(r.getFilename()+"=====");
                InputStream inputStream = r.getInputStream();
                BufferedInputStream bis =new BufferedInputStream(inputStream);
                byte [] data = new byte[1024*1024];
                int read = bis.read(data);
                System.out.println(read);
                System.out.println(new String(data,0,read));
                String script = new String(data,0,read);
                engine.eval(script);
                if (engine instanceof Invocable) {
                    Invocable in = (Invocable) engine;
                    System.out.println(in.invokeFunction("getLens",1));
                }

            }

        }
    }
}



/**
 * @author ceshi
 * @Title: JunitTestJS
 * @ProjectName ceshi
 * @Description: java 运行js
 * @date 2018/7/1016:35
 */
 class JunitTestJS {

    public static void main(String[] args) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try{
            engine.eval("function add(a,b){" +
                    "return a+b;" +
                    "}");
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                System.out.println(in.invokeFunction("add",1,1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}