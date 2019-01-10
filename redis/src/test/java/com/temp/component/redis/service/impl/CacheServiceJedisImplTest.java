package com.temp.component.redis.service.impl;

import com.temp.common.common.util.DateUtil;
import com.temp.component.redis.bean.ProcessInstanceCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-web.xml")
public class CacheServiceJedisImplTest {
    Logger logger = LoggerFactory.getLogger(CacheServiceJedisImplTest.class);
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private CacheServiceJedisImpl cacheServiceJedisImpl;
    @Autowired
    private CacheServiceJedisClusterImpl cacheServiceJedisClusterImpl;
    @Qualifier("busiDataSource")
    @Autowired
    private DataSource dataSource;
    //    @Test
    public  List<ProcessInstanceCache> cacheMembershipGroup() throws Exception {
        long first = System.currentTimeMillis();
        Connection connection = dataSource.getConnection();
//        String sql ="select * from act_hq_procinst  where id_ in('79c8373f61f14640be2f2b5e3e9c8f39','d03a617c053a4b9a91d8a5e7edd5ddd9')";
//        String sql ="select * from act_hq_procinst";
        String sql = "select * from act_hq_procinst  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet.last());
        int row = resultSet.getRow();
        System.out.println(row);
        ResultSetMetaData rsm = resultSet.getMetaData(); //获得列集
        int col = rsm.getColumnCount();   //获得列的个数
        System.out.println("获得列的个数====" + col);
        String colName[] = new String[col];
        //取结果集中的表头名称, 放在colName数组中
        for (int i = 0; i < col; i++) {   //第一列,从1开始.所以获取列名,或列值,都是从1开始
            colName[i] = rsm.getColumnName(i + 1); //获得列值的方式一:通过其序号
        }//End for
        resultSet.beforeFirst();
        System.out.println("列名-------" + Arrays.toString(colName));
        String data[][] = new String[row][col];
        //取结果集中的数据, 放在data数组中
        for (int i = 0; i < row; i++) {
            resultSet.next();
            for (int j = 0; j < col; j++) {
                data[i][j] = resultSet.getString(j + 1);
                //System.out.println (data[i][j]);
            }
        }//End for
        String colProcessIns[] = {"piId", "bpmnType", "ticketId", "pdId", "startUserId", null, "startTime", null};
        if(row>0){
            System.out.println("数据-----" + Arrays.toString(data[0]));
        }
//
        System.out.print("查询数据系统耗时：");
        System.out.println(System.currentTimeMillis() - first);

        long conversionFirst = System.currentTimeMillis();
        /**
         * 将数据发送到redis
         */
        List<ProcessInstanceCache> list = new ArrayList<ProcessInstanceCache>();
        long count = 0L;
        for (String[] s : data) {
            ProcessInstanceCache p = new ProcessInstanceCache();
            for (int i = 0; i < colProcessIns.length; i++) {
                Field[] declaredFields = p.getClass().getDeclaredFields();
                for (Field f : declaredFields) {
                    f.setAccessible(true);
                    if (f.getName().equals(colProcessIns[i])) {
                        if ("java.util.Date".equals(f.getType().getName())) {
                            System.out.println(s[i]);
                            f.set(p, DateUtil.getDateYYMMDDhhmmss(s[i]));
                            continue;
                        }
                        f.set(p, s[i]);
                    }
                }
            }
//                long l = cacheServiceJedisImpl.cacheProcessInstance(s[2], p);
            list.add( p);
//                long l = cacheServiceJedisImpl.cacheProcessInstance(s[2], p);
//                long l = cacheServiceJedisClusterImpl.cacheProcessInstance(s[2], p);
//                count+=l;
        }
        System.out.println("数据转换耗时：");
        System.out.println(System.currentTimeMillis() - conversionFirst);
//        System.out.println(data.length);
        System.out.println(list.size());
        long cacheFirst = System.currentTimeMillis();
//        long countval = cacheServiceJedisImpl.cacheProcessInstance(map);
//        long countval = cacheServiceJedisClusterImpl.cacheProcessInstance(map);
//        System.out.println("缓存的实例数量："+countval);
//        System.out.print("缓存实例耗时：");
//        System.out.print(System.currentTimeMillis()-cacheFirst);
//        System.out.println("-----------------------------------------");
        return list;
    }

    @Test
    public void testJedisCache() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("22333");
        list.add("88888");
        list.add("3333");
        cacheServiceJedisImpl.cacheMembershipGroup("g11111", list);
        cacheServiceJedisImpl.cacheMembershipGroup("g22222", list);
        cacheServiceJedisImpl.cacheMembershipUser("u11111", list);
        cacheServiceJedisImpl.cacheMembershipUser("u22222", list);
        System.out.println(cacheServiceJedisImpl.listGroupIdList("u11111"));
        System.out.println(cacheServiceJedisImpl.listUserIdList("g11111"));
//        System.out.println( cacheServiceJedisImpl.removeMembershipGroupCache("g11111"));
//        System.out.println( cacheServiceJedisImpl.removeMembershipUserCache("u11111"));
        List<String> keyList = new ArrayList<String>();
        keyList.add("g11111");
        keyList.add("g22222");
//        System.out.println( cacheServiceJedisImpl.removeMembershipGroupCache(keyList));
        keyList.clear();
        keyList.add("u11111");
        keyList.add("u22222");
//        System.out.println( cacheServiceJedisImpl.removeMembershipUserCache(keyList));
        long first = System.currentTimeMillis();
        List<ProcessInstanceCache> stringProcessInstanceCacheMap = cacheMembershipGroup();
        if(stringProcessInstanceCacheMap.size()>0){
            cacheServiceJedisImpl.cacheProcessInstance(stringProcessInstanceCacheMap.get(0));

        }
        cacheServiceJedisImpl.cacheProcessInstance(stringProcessInstanceCacheMap);
        System.out.println(cacheServiceJedisImpl.getProcessInstanceCache("7EC08AF84FB4954EFB1CF15CEE1170E8", "0203"));
//        cacheServiceJedisImpl.removeProcessInstanceCache("7EC08AF84FB4954EFB1CF15CEE1170E8","0203");
//        keyList.clear();
//        keyList.add("004185E11E42F10DE0C66709FA191DD0");
//        keyList.add("B8EB07C463E578678B53E61A1EC68CD8");
//        keyList.add("CBDE0B7E1526BA950738BBCB27F67B5F");
//        Map<String ,List<String> >proceIns = new HashMap<String ,List<String>>();
//        proceIns.put("0203",keyList);
//        System.out.println( cacheServiceJedisImpl.removeProcessInstanceCache(proceIns));

        Jedis resource = jedisPool.getResource();
//        resource.eval("local t1 = redis.call('keys','s:*') for k,v in pairs(t1) do redis.call('del',v) print(v) end");
        logger.info("end time {}",System.currentTimeMillis()-first);
        logger.debug("debug end time {}",System.currentTimeMillis()-first);
//        jedisCluster.eval("local t1 = redis.call('keys','s:*') for k,v in pairs(t1) do redis.call('del',v) print(v) end",1);
//        cacheServiceJedisImpl.listGroupIdList();

    }









    @Test
    public void clusterJedisClusterTest() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("22333");
        list.add("88888");
        list.add("3333");
        cacheServiceJedisClusterImpl.cacheMembershipGroup("g11111", list);
        cacheServiceJedisClusterImpl.cacheMembershipGroup("g22222", list);
        cacheServiceJedisClusterImpl.cacheMembershipUser("u11111", list);
        cacheServiceJedisClusterImpl.cacheMembershipUser("u22222", list);
        System.out.println(cacheServiceJedisClusterImpl.listGroupIdList("u11111"));
        System.out.println(cacheServiceJedisClusterImpl.listUserIdList("g11111"));
//        System.out.println( cacheServiceJedisClusterImpl.removeMembershipGroupCache("g11111"));
//        System.out.println( cacheServiceJedisClusterImpl.removeMembershipUserCache("u11111"));
        List<String> keyList = new ArrayList<String>();
        keyList.add("g11111");
        keyList.add("g22222");
//        Map<String, List<String>> hostPortMapMap = BpmnJedisClusterUtil.getHostPortMapMap(keyList, cacheServiceJedisClusterImpl.slotHostMap);
//        System.out.println("==d==================="+hostPortMapMap);
//        Jedis resource = cacheServiceJedisClusterImpl.nodeMap.get("192.168.100.109:3791").getResource();
//        String keys="";
//        for(String str :keyList){
//        keys +="','"+str;
//        }
//        String script = "local t1 = {'"+keys+"'} for k,v in pairs(t1) do redis.call('del',v) print(v) end";
//        resource.eval(script,0);
//        resource.del(BpmnJedisClusterUtil.toArray(keyList,new String[0]));
        logger.info("删除数据数量   {}",cacheServiceJedisClusterImpl.removeMembershipGroupCache(keyList));
        keyList.clear();
        keyList.add("u11111");keyList.add("u22222");keyList.add("");


        logger.info("删除数据数量   {}",cacheServiceJedisClusterImpl.removeMembershipUserCache(keyList));
//        keyList.clear();
//        keyList.add("g11111");keyList.add("g22222");//;keyList.add("");
//        System.out.prisntln( cacheServiceJedisClusterImpl.removeMembershipGroupCache(keyList));
        List<ProcessInstanceCache> stringProcessInstanceCacheMap = cacheMembershipGroup();
//        cacheServiceJedisClusterImpl.cacheProcessInstance(stringProcessInstanceCacheMap.get(0));
//        long l = System.currentTimeMillis();
        cacheServiceJedisClusterImpl.cacheProcessInstance(stringProcessInstanceCacheMap);
//        long endtime = System.currentTimeMillis() - l;
//        logger.debug("耗时============{}"+endtime);
//        System.out.println(cacheServiceJedisClusterImpl.getProcessInstanceCache("7EC08AF84FB4954EFB1CF15CEE1170E8", "0203"));

        cacheServiceJedisClusterImpl.removeProcessInstanceCache("7EC08AF84FB4954EFB1CF15CEE1170E8","0203");
        keyList.clear();
        keyList.add("004185E11E42F10DE0C66709FA191DD0");
        keyList.add("B8EB07C463E578678B53E61A1EC68CD8");
        keyList.add("CBDE0B7E1526BA950738BBCB27F67B5F");
        Map<String ,List<String> > proceIns = new HashMap<String ,List<String>>();
        proceIns.put("0203",keyList);
        System.out.println( cacheServiceJedisClusterImpl.removeProcessInstanceCache(proceIns));
//
    }
}