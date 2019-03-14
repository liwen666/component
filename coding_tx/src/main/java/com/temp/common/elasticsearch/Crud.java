package com.temp.common.elasticsearch;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;

/**
 * ClassName:Crud 简单的增删改查 Date: 2018年3月21日 下午12:51:15
 * 
 * @author xbq
 * @version
 * @since JDK 1.8
 */
public class Crud {

    public final static String HOST = "127.0.0.1";
    // http请求的端口是9200，客户端是9300
    public final static int PORT = 9300; 

    private TransportClient client = null;

    /**
     * getConnection:(获取连接).
     * 
     * @author xbq Date:2018年3月21日下午4:03:32
     *
     * @throws Exception
     */
    @SuppressWarnings({ "resource", "unchecked" })
    @Before
    public void getConnection() throws Exception {
        // 设置集群名称
//        Settings settings = Settings.builder().put("cluster.name", "nmtx-cluster").build();
//        // 创建client
//        client = new PreBuiltTransportClient(settings)
//                .addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), PORT));
        client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(
                new TransportAddress(InetAddress.getByName(HOST),PORT));
    }
    
    /**
     * closeConnection:(关闭连接).
     * 
     * @author xbq Date:2018年3月21日下午4:03:45
     *
     */
    @After
    public void closeConnection() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * testIndex:(创建索引).
     * 
     * @author xbq Date:2018年3月21日下午4:04:16
     * @throws IOException
     *
     */
    @Test
    public void testCreateIndex() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderNo", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "001");
        jsonObject.put("orderName", "购买元宝");
        jsonObject.put("orderTime", new Date());
        jsonObject.put("price", 1.5);
        jsonObject.put("ip", "127.0.0.1");
        
//        IndexResponse response = client.prepareIndex("rxpay", "order").setSource(jsonObject.toString(), XContentType.JSON).get();
        IndexResponse response = client.prepareIndex("rxpay", "order","100").setSource(jsonObject.toString(), XContentType.JSON).get();
        System.out.println("索引名称：" + response.getIndex());
        System.out.println("类型：" + response.getType());
        System.out.println("文档ID：" + response.getId()); // 第一次使用是1
        System.out.println("当前实例状态：" + response.status());
    }
    
    /**
     * testQuery:(查询).  
     * @author xbq
     * Date:2018年5月3日下午6:04:22
     */
    @Test
    public void testQuery() {
        try {
            GetResponse response = client.prepareGet("rxpay", "order", "KmdXemkBTsriNR9F75oN").execute().actionGet();
            System.out.println(response.getSourceAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * testUpdate:(更新).  
     * @author xbq
     * Date:2018年5月3日下午6:07:58
     */
    @Test
    public void testUpdate() {
        JSONObject json = new JSONObject();
        json.put("user", "Joe");
        json.put("age", "22");
        json.put("sex", "男");
        json.put("orderTime", "6666666");
        UpdateResponse response = client.prepareUpdate("rxpay", "order", "KmdXemkBTsriNR9F75oN").setDoc(json.toJSONString(), XContentType.JSON).get();
        System.out.println("索引名称：" + response.getIndex());
        System.out.println("类型：" + response.getType());
        System.out.println("文档ID：" + response.getId());
        System.out.println("当前实例状态：" + response.status());
    }
    
    /**
     * testDelete:(删除).  
     * @author xbq
     * Date:2018年5月4日下午5:44:32
     */
    @Test
    public void testDelete() {
        DeleteResponse response = client.prepareDelete("rxpay", "order", "6W2QKmMBhrIcTC9dgt7A").get();
        System.out.println("索引名称：" + response.getIndex());
        System.out.println("类型：" + response.getType());
        System.out.println("文档ID：" + response.getId());
        System.out.println("当前实例状态：" + response.status());
    }

}