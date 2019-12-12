package com.temp.common.base.JDBC.greeplum;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author lw
 * date 2019/8/22  16:44
 * discribe
 */
//@SpringBootApplication(exclude = {H2Config.class})
public class H2Test {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    public DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("org.h2.Driver");
        druidDataSource.setUrl("jdbc:h2:tcp://192.168.1.170:8043/mem:testbpmn");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("testdcpaymysql@2019");
//        基本属性 url、user、password
//                <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
//        <property name="url" value="jdbc:mysql://192.168.9.180:3306/hqbpmn?useUnicode=true"/>
//        <property name="username" value="root"/>
//        <property name="password" value="root"/>
//
//                配置初始化大小、最小、最大
//                <property name="initialSize" value="10"/>
//        <property name="minIdle" value="10"/>
//        <property name="maxActive" value="50"/>
//                配置获取连接等待超时的时间
//                <property name="maxWait" value="60000"/>
//                配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
//                <property name="timeBetweenEvictionRunsMillis" value="60000"/>
//
//                配置一个连接在池中最小生存的时间，单位是毫秒
//                <property name="minEvictableIdleTimeMillis" value="300000"/>
//
//        <property name="validationQuery" value="SELECT 'x'"/>
//        <property name="testWhileIdle" value="true"/>
//        <property name="testOnBorrow" value="false"/>
//        <property name="testOnReturn" value="false"/>
//
//                打开PSCache，并且指定每个连接上PSCache的大小  如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。
//        <property name="poolPreparedStatements" value="false"/>
//        <property name="maxPoolPreparedStatementPerConnectionSize" value="20"/>
//
//                配置监控统计拦截的filters
//                <property name="filters" value="wall,stat"/>
        return druidDataSource;
    }

    public DataSource getmysqlDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("org.h2.Driver");
        druidDataSource.setUrl("jdbc:h2:tcp://192.168.1.170:8043/mem:testbpmn");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("testdcpaymysql@2019");
//        基本属性 url、user、password
//                <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
//        <property name="url" value="jdbc:mysql://192.168.9.180:3306/hqbpmn?useUnicode=true"/>
//        <property name="username" value="root"/>
//        <property name="password" value="root"/>
//
//                配置初始化大小、最小、最大
//                <property name="initialSize" value="10"/>
//        <property name="minIdle" value="10"/>
//        <property name="maxActive" value="50"/>
//                配置获取连接等待超时的时间
//                <property name="maxWait" value="60000"/>
//                配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
//                <property name="timeBetweenEvictionRunsMillis" value="60000"/>
//
//                配置一个连接在池中最小生存的时间，单位是毫秒
//                <property name="minEvictableIdleTimeMillis" value="300000"/>
//
//        <property name="validationQuery" value="SELECT 'x'"/>
//        <property name="testWhileIdle" value="true"/>
//        <property name="testOnBorrow" value="false"/>
//        <property name="testOnReturn" value="false"/>
//
//                打开PSCache，并且指定每个连接上PSCache的大小  如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。
//        <property name="poolPreparedStatements" value="false"/>
//        <property name="maxPoolPreparedStatementPerConnectionSize" value="20"/>
//
//                配置监控统计拦截的filters
//                <property name="filters" value="wall,stat"/>
        return druidDataSource;
    }

    @Test
    public void testefficient() throws SQLException, IllegalAccessException {
        System.out.println("=========================================================================================");
        DataSource dataSource = getDataSource();
        System.out.println(dataSource);
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        long startTime = System.currentTimeMillis();
        long count = 1000000;
        //如果存在USER_INFO表就先删除USER_INFO表
        stmt.execute("DROP TABLE IF EXISTS merchant_info");
        //创建USER_INFO表
        stmt.execute("  CREATE TABLE `merchant_info` (\n" +
                "                `id` bigint(32) DEFAULT NULL,\n" +
                "        `uid` bigint(32) DEFAULT NULL,\n" +
                "        `type` int(10) DEFAULT NULL,\n" +
                "        `realname` varchar(255) DEFAULT NULL,\n" +
                "        `activate_status` int(10) DEFAULT NULL,\n" +
                "        `recv_pay_ways` varchar(2000) DEFAULT NULL,\n" +
                "        `assets` varchar(2000) DEFAULT NULL,\n" +
                "        `day_mount_sum` decimal(20,10) DEFAULT NULL\n" +
                ")");

        for (int i = 0; i < count; i++) {
            String sql = getInsertSql(MerchantInfo.builder().activate_status(1).assets("{}").day_mount_sum(new BigDecimal(1000))
                    .uid((long) i)
                    .realname("test").recv_pay_ways("weixin").type(1).build());
            stmt.executeUpdate(sql);

        }
//        System.out.println("插入数据 "+count+"  条 耗时"+(System.currentTimeMillis()-startTime));
        //删除
//            stmt.executeUpdate("DELETE FROM USER_INFO WHERE name='大日如来'");
        //修改
//            stmt.executeUpdate("UPDATE USER_INFO SET name='孤傲苍狼' WHERE name='苍狼'");
        //查询
        long queryStart = System.currentTimeMillis();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM MERCHANT_INFO ");
        ResultSet rs = stmt.executeQuery("SELECT * FROM MERCHANT_INFO limit 100 ");
        //遍历结果集
        while (rs.next()) {
            Field[] declaredFields = MerchantInfo.class.getDeclaredFields();
            for (Field f : declaredFields) {
                System.out.print(rs.getString(f.getName()));
            }
        }
        System.out.println("查询耗时 ：" + (System.currentTimeMillis() - queryStart));
        System.out.println("=========================================================================================");
        //释放资源
        stmt.close();
        conn.close();
    }

    public static  String getInsertSql(Object build) throws IllegalAccessException {
//        insert into MERCHANT_INFO (id,uid,type,realname) values (1,11,1,'我')
        String sql = "insert into MERCHANT_INFO (";
        Field[] declaredFields = build.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            sql += f.getName() + ",";
        }
        sql = sql.substring(0, sql.length() - 1) + ") values( ";
        for (Field f : declaredFields) {
            f.setAccessible(true);
            if (f.getType().getName().equals("java.lang.String")) {
                sql += "'" + f.get(build) + "',";
                continue;
            }
            sql += f.get(build) + ",";
        }
        sql = sql.substring(0, sql.length() - 1) + ")";
        return sql;
    }

//    @Test
//    public void select() {
//        MerchantInfo merchantInfo = merchantInfoDao.selectOne(Wrappers.<MerchantInfo>lambdaQuery().eq(MerchantInfo::getUid, 10000).apply(" 1=1 limit 1"));
//        System.out.println(JSON.toJSONString(merchantInfo));
//    }
//
//    @Test
//    public void insert() {
//        int insert = merchantInfoDao.insert(MerchantInfo.builder().uid(10000l).realname("rest").activate_status(1).recv_pay_ways("FSAFDS").build());
//        System.out.println(JSON.toJSONString(insert));
//    }

    @Test
    public void temp() throws IllegalAccessException, InterruptedException {
        int weixin = jdbcTemplate.update(getInsertSql(MerchantInfo.builder().id(100l).recv_pay_ways("weixin").uid(123l).build()));
        List<MerchantInfo> query = jdbcTemplate.query("select * from merchant_info", new BeanPropertyRowMapper(MerchantInfo.class));
        Thread.sleep(1000);
        System.out.println(JSON.toJSONString(query.get(0)));

    }


    @Test
    public void insertObject() throws IllegalAccessException, InterruptedException {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            jdbcTemplate.update(getInsertSql(MerchantInfo.builder().id(1l + i).uid(100l + i)
                    .recv_pay_ways(JSON.toJSONString(new ArrayList<String>() {{
                        add("weixin");
                        add("zhifubao");
                    }}))
                    .activate_status(1)
                    .realname("test")
                    .type(1).build()));
        }
        System.out.println("======添加数据耗时  " + (System.currentTimeMillis() - startTime) + "ms");
        List<MerchantInfo> query = jdbcTemplate.query("select * from merchant_info", new BeanPropertyRowMapper(MerchantInfo.class));
        Thread.sleep(1000);
        System.out.println(JSON.toJSONString(query.get(0)));


    }

    @Test
    public void insertObjectBatch() throws IllegalAccessException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<MerchantInfo> merchantInfos = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            merchantInfos.add(MerchantInfo.builder().id(1l + i).uid(100l + i)
                    .recv_pay_ways(JSON.toJSONString(new ArrayList<String>() {{
                        add("weixin");
                        add("zhifubao");
                    }}))
                    .activate_status(1)
                    .realname("test")
                    .day_mount_sum(new BigDecimal(1000))
                    .type(1).build());
        }
        List<List<MerchantInfo>> lists = MySubTUtil.subList(merchantInfos, 10);
        Mytask task = new Mytask(lists);
        ExecutorService es = Executors.newFixedThreadPool(10);//创建固定大小的线程
        for(int i=0;i<10;i++){
            //线程池可以 处理Callablel类型的任务  获取返回值
            /**
             * 线程是5个5个的执行任务
             * 这里下面两个处理任务的效果是一样的
             */
//            es.submit(task);//这个会返回一个future对象
            es.execute(task);

        }
        while(task.getDown().get()!=10){
        }
        es.shutdown();
        System.out.println("======添加数据耗时  " + (System.currentTimeMillis() - startTime) + "ms");
        List<MerchantInfo> query = jdbcTemplate.query("select * from merchant_info", new BeanPropertyRowMapper(MerchantInfo.class));
        System.out.println(JSON.toJSONString(query.size()));


    }
    class Mytask implements Runnable{

        private final List<List<MerchantInfo>> merchantInfos;
        private  AtomicInteger i = new AtomicInteger(0);
        private AtomicInteger down = new AtomicInteger(0);

        public Mytask(List<List<MerchantInfo>> merchantInfos) {
            this.merchantInfos = merchantInfos;
        }

        @Override
        public void run() {
            System.out.println(this.i);
            System.out.println(System.currentTimeMillis()+":thread ID:"+Thread.currentThread().getId());
                addByBatch(merchantInfos.get(this.i.getAndIncrement()));
            down.incrementAndGet();
        }

        public AtomicInteger getDown() {
            return down;
        }
    }

    private void addByBatch(List<MerchantInfo> merchantInfos) {
        {
            final List<MerchantInfo> tempBpplist = merchantInfos;
            String sql = "insert into MERCHANT_INFO (id,uid,type,realname,activate_status,recv_pay_ways,assets,day_mount_sum,day_order_count)" +
                    " values( ?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public int getBatchSize() {
                    return tempBpplist.size();
                }

                @Override
                public void setValues(PreparedStatement ps, int i)
                        throws SQLException {
                    ps.setLong(1, tempBpplist.get(i).getId());
                    ps.setLong(2, tempBpplist.get(i).getUid());
                    ps.setInt(3, tempBpplist.get(i).getType());
                    ps.setString(4, tempBpplist.get(i).getRealname());
                    ps.setInt(5, tempBpplist.get(i).getActivate_status());
                    ps.setString(6, tempBpplist.get(i).getRecv_pay_ways());
                    ps.setString(7,tempBpplist.get(i).getAssets());
                    ps.setBigDecimal(8,tempBpplist.get(i).getDay_mount_sum());
                }
            });
        }
    }

//    @Test
//    public void conversion() {
//        MerchantInfo build = MerchantInfo.builder().id(100000l).uid(100l)
//                .recv_pay_ways(JSON.toJSONString(new ArrayList<String>() {{
//                    add("weixin");
//                    add("zhifubao");
//                }}))
//                .activate_status(1)
//                .realname("test")
//                .day_mount_sum(new BigDecimal(1000))
//                .type(1).build();
//        merchantInfoDao.insert(build);
//    }

//    @Test
//    public void merchantInfoCache() {
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            MerchantInfoCache build = MerchantInfoCache.builder().id(100000l + i).uid(100l + i)
//                    .recvPayWays(JSON.toJSONString(new ArrayList<String>() {{
//                        add("weixin");
//                        add("zhifubao");
//                    }}))
//                    .activateStatus(1)
//                    .realname("test" + i)
//                    .assets(JSON.toJSONString(new ArrayList<AssetInfo>() {{
//                        add(AssetInfo.builder().accuracy(10).currency("CNY")
//                                .amount(new BigDecimal(10000)).build());
//                    }}))
//                    .dayMountSum(new BigDecimal(1000))
//                    .dayOrderCount(10l + i)
//                    .type(1).build();
//            merchantInfoCacheDao.insert(build);
//        }
//        System.out.println("===耗时" + (System.currentTimeMillis() - startTime) + "ms");
//        List<MerchantInfoCache> merchantInfoCaches = merchantInfoCacheDao.selectList(Wrappers.<MerchantInfoCache>lambdaQuery().eq(MerchantInfoCache::getType, 1));
//    }

//    @Test
//    public void queryMerchantInfo() {
//        long startTime = System.currentTimeMillis();
//        List<MerchantInfoCache> merchantInfoCaches = merchantInfoCacheDao.selectList(Wrappers.<MerchantInfoCache>lambdaQuery().eq(MerchantInfoCache::getType, 1));
//        System.out.println(JSON.toJSONString(merchantInfoCaches));
//        List<MerchantInfoPojo> merchantInfoPojos = conversionToPojo(merchantInfoCaches);
//        System.out.println("=====查询商户信息耗时" + (System.currentTimeMillis() - startTime) + "ms");
//        System.out.println(merchantInfoPojos.size());
//
//
//    }

//    public List<MerchantInfoPojo> conversionToPojo(List<MerchantInfoCache> merchantInfoCaches) {
//        List<MerchantInfoPojo> merchantInfoPojos = new ArrayList<>();
//        for (MerchantInfoCache merchant : merchantInfoCaches) {
//            merchantInfoPojos.add(MerchantInfoPojo.builder().activateStatus(merchant.getActivateStatus())
//                    .assets(JSON.parseArray(merchant.getAssets(), AssetInfo.class))
//                    .dimension(DimensionDto.builder().dayAmountSum(merchant.getDayMountSum()).dayOrderCount(merchant.getDayOrderCount()).merchantId(merchant.getUid()).build())
//                    .id(merchant.getId())
//                    .realname(merchant.getRealname())
//                    .recvPayways(JSON.parseArray(merchant.getRecvPayWays(), String.class))
//                    .type(merchant.getType())
//                    .uid(merchant.getUid() + "").build());
//        }
//        return merchantInfoPojos;
//    }


    @Test
    public void batchInsert() throws SQLException {
        long startTime = System.currentTimeMillis();
        List<MerchantInfo> merchantInfos = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            merchantInfos.add(MerchantInfo.builder().id(1l + i).uid(100l + i)
                    .recv_pay_ways(JSON.toJSONString(new ArrayList<String>() {{
                        add("weixin");
                        add("zhifubao");
                    }}))
                    .activate_status(1)
                    .realname("test")
                    .day_mount_sum(new BigDecimal(1000))
                    .type(1).build());
        }
        List<Integer> integers = addProduct(merchantInfos);
        System.out.println("===耗时" + (System.currentTimeMillis() - startTime) + "ms");
    }

    public List<Integer> addProduct(List<MerchantInfo> expList) throws SQLException {

        final List<MerchantInfo> tempBpplist = expList;

        String sql = "insert into MERCHANT_INFO (id,uid,type,realname,activate_status,recv_pay_ways,assets,day_mount_sum,day_order_count)" +
                " values( ?,?,?,?,?,?,?,?,?)";
        Connection con = jdbcTemplate.getDataSource().getConnection();
        con.setAutoCommit(false);
        PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        for (MerchantInfo n : tempBpplist) {
            ps.setLong(1, n.getId());
            ps.setLong(2, n.getUid());
            ps.setInt(3, n.getType());
            ps.setString(4, n.getRealname());
            ps.setInt(5, n.getActivate_status());
            ps.setString(6, n.getRecv_pay_ways());
            ps.setString(7,n.getAssets());
            ps.setBigDecimal(8,n.getDay_mount_sum());
            ps.addBatch();
        }
        ps.executeBatch();
        con.commit();
//        ResultSet rs = ps.getGeneratedKeys(); //获取结果
        List<Integer> list = new ArrayList<Integer>();
//        while(rs.next()) {
//            list.add(rs.getInt(1));//取得ID
//        }
        con.close();
        ps.close();
//        rs.close();
        return list;

    }
}
