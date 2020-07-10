package com.temp.common.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/11/12 10:19
 */
@Component
@Slf4j
public class BatchNodeUtils implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    public static void isAllowDefineName(String definitionName) {
        Pattern compile = Pattern.compile("[A-Za-z0-9_-]+");
        Matcher matcher = compile.matcher(definitionName);
        boolean matches = matcher.matches();
        Assert.state(matches, "任务定义不能输入中文->definitionName:" + definitionName);

    }


    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        BatchNodeUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象 这里重写了bean方法，起主要作用
     */
    public static <T> T getBean(Class<T> t) throws BeansException {
        return applicationContext.getBean(t);
    }

    /**
     * 获取对象 这里重写了bean方法，起主要作用
     */
    public static <T> T getBean(String beanId, Class<T> t) throws BeansException {
        return applicationContext.getBean(beanId, t);
    }

    /**
     * 获取节点地址
     */
    public static String getNodeAddr(String name) {
        Pattern compile = Pattern.compile("([A-Za-z0-9_]+):([A-Za-z0-9_-]+)");
        Matcher matcher = compile.matcher(name);
        boolean matches = matcher.matches();
        Assert.state(matches, "节点名称不匹配->name:" + name);
        String nodeName = matcher.group(1);
        return BatchNodeProperties.address.get(nodeName);
    }

    /**
     * 获取分布式key
     */
    public static String getNodeDataKey(String key) {
        String agex = "([A-Za-z0-9_]+):([A-Za-z0-9_-]+)";
        Pattern compile = Pattern.compile(agex);
        Matcher matcher = compile.matcher(key);
        boolean matches = matcher.matches();
        Assert.state(matches, "节点key名称不匹配->key:" + key);
        String nodeKey = matcher.group(2);
        return nodeKey;
    }

    /**
     * 获取分布式key
     */
    public static String getNodeName(String name) {
        Pattern compile = Pattern.compile("([A-Za-z0-9_]+):([A-Za-z0-9_-]+)");
        Matcher matcher = compile.matcher(name);
        boolean matches = matcher.matches();
        Assert.state(matches, "节点name名称不匹配->name:" + name+" 要求 ：([A-Za-z0-9_]+):([A-Za-z0-9_-]+)");
        String nodeName = matcher.group(1);
        return nodeName;
    }

    /**
     * 判断节点是否存活
     *
     * @param ip
     * @param port
     * @param timeout
     * @return
     */
    public static boolean isReachable(String ip, String port, int timeout) {
        boolean reachable = false;
        // 如果端口为空，使用 isReachable 检测，非空使用 socket 检测
        if (port == null) {
            try {
                InetAddress address = InetAddress.getByName(ip);
                reachable = address.isReachable(timeout);
                System.out.println("===============" + reachable);
            } catch (Exception e) {
                log.error(e.getMessage());
                reachable = false;
            }
        } else {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(ip, Integer.parseInt(port)), timeout);
                reachable = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                reachable = false;
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (Exception e) {
                }
            }
        }
        return reachable;
    }

    /**
     * 判断节点是否存活
     * addr 节点地址
     *
     * @param timeout
     * @return
     */
    public static boolean isReachable(String addr, int timeout) {
        Pattern compile = Pattern.compile("(http://)([a-z0-9.]+):([0-9]+)");
        Matcher matcher = compile.matcher(addr);
        boolean matches = matcher.matches();
        Assert.state(matches, "域名不匹配->addr:" + addr);
        String ip = matcher.group(2);
        String port = matcher.group(3);
        return isReachable(ip, port, timeout);
    }


//    /**
//     * 对计划任务进行排序
//     */
//    public static Map<Integer,List<String>> taskLink(String taskNode) {
//        TaskSimulate taskSimulate = new TaskSimulate(taskNode);
//        return taskSimulate.taskChain(taskNode);
//    }


    public static void main(String[] args) {
//        boolean reachable = isReachable("http://172.16.102.23:9001", 3000);
//        System.out.println(reachable);

        String nodeName = getNodeName("master_node:fda");
        System.out.println(nodeName);



    }
}



