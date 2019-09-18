package com.temp.common.common.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class IpUtil {

    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static String getIps(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            return ip;
        }
        ip = request.getHeader("X-Real-IP");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    //ip串检查
    public static  boolean  checkIps(String curIps, Map<String,String> configIpMap){

        //IP 串为空有异常
        if(StringUtils.isEmpty(curIps)){
            return false;
        }

        String strOpen = configIpMap.get("open");
        //如果配置了关 则不校验，其他情况则表示开
        if( !StringUtils.isEmpty(strOpen) && strOpen.equalsIgnoreCase("0")){
            return true;
        }

        String ipCount = configIpMap.get("count");
        //没有配置个数,则校验不通过
        if (StringUtils.isEmpty(ipCount) ){
            return false;
        }
        Integer configIpCount = Integer.parseInt(ipCount);
        String[] curIpList = curIps.split(",");

        //IP个数不同
        if(curIpList.length != configIpCount ){
            return false;
        }

        for(int index = 0 ; index < curIpList.length; index++ ){
            String curIp = curIpList[index];
            curIp = curIp.trim();
            if(configIpMap.containsKey(String.valueOf(index))){
                String configIps =  configIpMap.get(String.valueOf(index));
                //如果配置了下标，并且当前ip不包含在内
                if(!StringUtils.isEmpty(configIps) && !configIps.contains(curIp)){
                    return false;
                }
            }
        }
        return true;
    }
}
