package com.temp.component.dubbo.provider;
 
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.junit.Test;

import java.io.IOException;

public class ZooKeeperTest {
 
    public static void main(String[] args) throws Exception{
        ZooKeeper zk = new ZooKeeper("172.23.81.110:2181", 3000, new Watcher() {
//        ZooKeeper zk = new ZooKeeper("192.168.42.220:2181", 3000, new Watcher() {
            public void process(WatchedEvent event) {
                // TODO Auto-generated method stub
                System.out.println(" receive event : " + event.getType().name());
            }
        });
        System.out.println("=========创建节点===========");
        if (null == zk.exists("/test", false)) {
            zk.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zk.getData("/test", false, null)));

        System.out.println("=========修改节点的数据==========");
        zk.setData("/test", "zNode2".getBytes(), -1);
        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zk.getData("/test", false, null)));

        System.out.println("=======删除节点==========");
        zk.delete("/test", -1);
        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zk.exists("/test", false));
        zk.close();
    }
    @Test
    public void insertNode() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("192.168.42.136:2181", 3000, new Watcher() {
            public void process(WatchedEvent event) {
                // TODO Auto-generated method stub
                System.out.println(" receive event : " + event.getType().name());
            }
        });
        if (null == zk.exists("/test", false)) {
            zk.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }
}