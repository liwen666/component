package com.temp.common.common.node_parse;

import com.temp.common.common.node_parse.data.TaskSimulate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;
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
     * 获取分布式key
     */
    public static String getNodeDataKey(String key) {
        Pattern compile = Pattern.compile("([A-Za-z0-9_]+):([A-Za-z0-9_]+)");
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
        Pattern compile = Pattern.compile("([A-Za-z0-9_]+):([A-Za-z0-9_]+)");
        Matcher matcher = compile.matcher(name);
        boolean matches = matcher.matches();
        Assert.state(matches, "节点name名称不匹配->name:" + name+" 要求 ：([A-Za-z0-9_]+):([A-Za-z0-9_]+)");
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


    /**
     * 对计划任务进行排序
     */
    public static Map<Integer,List<String>> taskLink(String taskNode) {
        TaskSimulate taskSimulate = new TaskSimulate(taskNode);
        return taskSimulate.taskChain(taskNode);
    }

    public static void main(String[] args) {
        String nodeDate = "{\"nodeDatas\":[{\"text\":\"开始\",\"type\":\"start\",\"figure\":\"Circle\",\"fill\":\"#32CD32\",\"key\":1,\"loc\":\"50 -650\"},{\"text\":\"结束\",\"type\":\"end\",\"figure\":\"Circle\",\"fill\":\"#EE2C2C\",\"key\":-1,\"loc\":\"0 -350\"},{\"text\":\"主节点任务编排1\",\"type\":\"node\",\"pause\":false,\"figure\":\"RoundedRectangle\",\"fill\":\"lightyellow\",\"key\":\"157430328800700408\",\"loc\":\"50 -500\"},{\"text\":\"主节点任务编排2\",\"type\":\"node\",\"pause\":false,\"figure\":\"RoundedRectangle\",\"fill\":\"lightyellow\",\"key\":\"157430335842846191\",\"loc\":\"-150 -500\"},{\"text\":\"主节点任务编排3\",\"type\":\"node\",\"pause\":false,\"figure\":\"RoundedRectangle\",\"fill\":\"lightyellow\",\"key\":\"157430342936200690\",\"loc\":\"-150 -650\"},{\"text\":\"主节点任务编排4\",\"type\":\"node\",\"pause\":false,\"figure\":\"RoundedRectangle\",\"fill\":\"lightyellow\",\"key\":\"157430348311270590\",\"loc\":\"-330 -500\"},{\"text\":\"主节点任务编排5\",\"type\":\"node\",\"pause\":false,\"figure\":\"RoundedRectangle\",\"fill\":\"lightyellow\",\"key\":\"157430378597877175\",\"loc\":\"290 -500\"},{\"text\":\"\",\"type\":\"focal\",\"figure\":\"Circle\",\"fill\":\"#FFDEAD\",\"key\":-8,\"loc\":\"-240 -350\"},{\"text\":\"\",\"type\":\"focal\",\"figure\":\"Circle\",\"fill\":\"#FFDEAD\",\"key\":-9,\"loc\":\"190 -350\"}],\"linkDatas\":[{\"from\":1,\"to\":\"157430328800700408\",\"points\":[50,-616.5639703440112,50,-606.5639703440112,50,-567.8815591986822,50,-567.8815591986822,50,-529.1991480533532,50,-519.1991480533532]},{\"from\":1,\"to\":\"157430342936200690\",\"points\":[16.563970344011175,-650,6.5639703440111745,-650,-33.39402750175728,-650,-33.39402750175728,-650,-73.35202534752574,-650,-83.35202534752574,-650]},{\"from\":1,\"to\":\"157430378597877175\",\"points\":[83.43602965598882,-650,93.43602965598882,-650,153.39402750175728,-650,153.39402750175728,-500,213.35202534752574,-500,223.35202534752574,-500]},{\"from\":\"157430348311270590\",\"to\":-8,\"points\":[-330,-480.8008519466468,-330,-470.8008519466468,-330,-427.23307445021237,-240.00000000000003,-427.23307445021237,-240.00000000000003,-383.66529695377795,-240.00000000000003,-373.66529695377795]},{\"from\":\"157430335842846191\",\"to\":-8,\"points\":[-150,-480.8008519466468,-150,-470.8008519466468,-150,-427.23307445021237,-240.00000000000003,-427.23307445021237,-240.00000000000003,-383.66529695377795,-240.00000000000003,-373.66529695377795]},{\"from\":-8,\"to\":-1,\"points\":[-216.33470304622207,-350,-206.33470304622207,-350,-124.88536635110545,-350,-124.88536635110545,-349.9999999999999,-43.43602965598882,-349.9999999999999,-33.43602965598882,-349.9999999999999]},{\"from\":\"157430342936200690\",\"to\":\"157430348311270590\",\"points\":[-216.64797465247426,-650,-226.64797465247426,-650,-240,-650,-240,-500,-253.35202534752574,-500,-263.35202534752574,-500]},{\"from\":\"157430342936200690\",\"to\":\"157430335842846191\",\"points\":[-150,-630.8008519466468,-150,-620.8008519466468,-150,-575,-150,-575,-150,-529.1991480533532,-150,-519.1991480533532]},{\"from\":\"157430335842846191\",\"to\":\"157430348311270590\",\"points\":[-216.64797465247426,-500,-226.64797465247426,-500,-240,-500,-240,-500,-253.35202534752574,-500,-263.35202534752574,-500]},{\"from\":\"157430328800700408\",\"to\":-9,\"points\":[50,-480.8008519466468,50,-470.8008519466468,50,-427.23307445021237,189.99999999999997,-427.23307445021237,189.99999999999997,-383.66529695377795,189.99999999999997,-373.66529695377795]},{\"from\":\"157430378597877175\",\"to\":-9,\"points\":[290,-480.8008519466468,290,-470.8008519466468,290,-427.23307445021237,189.99999999999997,-427.23307445021237,189.99999999999997,-383.66529695377795,189.99999999999997,-373.66529695377795]},{\"from\":-9,\"to\":-1,\"points\":[166.33470304622205,-350,156.33470304622205,-350,99.88536635110543,-350,99.88536635110543,-349.9999999999999,43.43602965598883,-349.9999999999999,33.43602965598883,-349.9999999999999]}]}";
        Map<Integer, List<String>> integerListMap = BatchNodeUtils.taskLink(nodeDate);
        System.out.println(integerListMap);

    }
}



