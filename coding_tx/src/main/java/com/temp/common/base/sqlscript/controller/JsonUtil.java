package com.temp.common.base.sqlscript.controller;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class JsonUtil {

	public static <K> void outPutPRToJSON(ProcessResult<K> pr,HttpServletResponse response){
		JsonConfig jsonConfig = new JsonConfig();   
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.setContentType("text/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		JSONObject jsonObject=JSONObject.fromObject(pr,jsonConfig);
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(jsonObject.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static <K> void outPutPRToPLAIN(ProcessResult<K> pr,HttpServletResponse response){
		JsonConfig jsonConfig = new JsonConfig();   
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		JSONObject jsonObject=JSONObject.fromObject(pr,jsonConfig);
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(jsonObject.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
