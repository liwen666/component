/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.temp.common.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;

/**
 * @author Flex Hu
 * 调用接口例子
 * @version 1.1
 */
public class MztSample {
    
    /**
     * POST参数
     * @param url
     * @param params
     * @return
     */
    public static String sendPostParams(String url,Map<String,Object> params){
        DefaultHttpClient httpclient = new DefaultHttpClient(
                new ThreadSafeClientConnManager());
        HttpPost httpost = new HttpPost(url);
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String,Object> entry = (Entry<String,Object>) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(),
                        (String) entry.getValue())); 
            }
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        try {
            String result =  httpclient.execute(httpost,responseHandler).toString();
            httpclient.getConnectionManager().shutdown();
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String url = "http://mztapp.fujian.gov.cn:8191/"
                + "dataset/AppSerController/invokeservice.do";
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        /*paramsMap.put("INVOKESERVICE_CODE","062");
        paramsMap.put("INVOKECALLER_CODE","授权码");
        paramsMap.put("USER_IDCARD",AESOperator.aesEncrypt("35222719870103333"));
        paramsMap.put("USER_NAME",AESOperator.aesEncrypt("张三"));
        paramsMap.put("USER_MOBILE",AESOperator.aesEncrypt("13452031733"));*/
        
        paramsMap.put("INVOKESERVICE_CODE","103");
        paramsMap.put("INVOKECALLER_CODE","授权码");
        paramsMap.put("TRUST_TICKET","34343433dddd");
        String POSTPARAM_JSON = JSON.toJSONString(paramsMap);
        Map<String,Object> clientParam = new HashMap<String,Object>();
        clientParam.put("POSTPARAM_JSON", POSTPARAM_JSON);
        String result = sendPostParams(url, clientParam);
        System.out.println(result);
    }

}
