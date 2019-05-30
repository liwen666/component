package com.temp.common.base.util.part1;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Enumeration;

public class HttpRequestUtils {


    public static <T>T coversion(HttpServletRequest request, Class<T> clazz) {
        T t =null;
        Field target=null;
        try {
           t=clazz.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
            for(Field f :declaredFields){
                target=f;
                f.setAccessible(true);
                if(f.getType().getName().equalsIgnoreCase("int")){
                    f.set(t,Integer.parseInt(request.getParameter(f.getName())));
                    continue;
                }
                f.set(t,request.getParameter(f.getName()));
                f.setAccessible(false);
            }
        } catch (Exception e) {
            System.out.println(target.getName()+target.getType().getName());
            System.out.println("请求参数转换错误");
        }
        return t;
    }
    public static boolean paramCheck(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            String parameter = request.getParameter(s);
            /**
             * 解密url
             */
            if (parameter != null) {
                String paramVal = decoderParam(parameter);
            }
        }
        return true;
    }
    private static String decoderParam(String parameter)  {
        String paramVal = "";
        int baseLong = parameter.length();
        try {
            paramVal = URLDecoder.decode(parameter, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (baseLong == paramVal.length()) {
            return paramVal;
        } else {
            return decoderParam(paramVal);
        }

    }
}
