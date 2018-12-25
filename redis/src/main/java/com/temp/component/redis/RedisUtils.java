package com.temp.component.redis;

import com.temp.common.common.bean.RedisCacheImpl;
import com.temp.common.common.util.DateUtil;
import com.temp.component.redis.bean.RedisKeyPrefixConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.util.JedisClusterCRC16;

import java.lang.reflect.Field;
import java.util.*;

public class RedisUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    public static String getStringKey(String prefix, String id) {
        return "str" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix + id;
    }

    public static String getStringKey(String prefix, String... id) {
        return getKey("str" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix, id);
    }

    public static String getHashKey(String prefix, String id) {
        return "h" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix + id;
    }

    public static String getHashKey(String prefix, String... id) {
        return getKey("h" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix, id);
    }

    public static String getListKey(String prefix, String id) {
        return "l" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix + id;
    }

    public static String getListKey(String prefix, String... id) {
        return getKey("l" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix, id);
    }

    public static String getSetKey(String prefix, String id) {
        return "s" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix + id;
    }

    public static String getSetKey(String prefix, String... id) {
        return getKey("s" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix, id);
    }

    public static String getZsetKey(String prefix, String id) {
        return "zs" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix + id;
    }

    public static String getZsetKey(String prefix, String... id) {
        return getKey("zs" + RedisKeyPrefixConstant.REDIS_SPLIT + prefix, id);
    }

    public static String getKey(String... id) {
        return getKey("", id);
    }


    public static String getKeyAddrByJedis(JedisCluster jedisCluster, String keyName) {
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String ip : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(ip);
            try (Jedis jes = jp.getResource()) {
                if (jes.exists(keyName)) {
                    System.out.println(keyName + "这个key在-->" + ip);
                    return ip;
                }
                jes.close();
            } catch (Exception e) {
            }
        }
        return null;
    }


    public static <T extends RedisCacheImpl> Map<String, List<T>> getHostPortMapMap(List<T> list, TreeMap<Long, String> slotHostMap) {
        Map<String, List<T>> hostKeyListMaps = new HashMap<String, List<T>>();
        Iterator<Map.Entry<Long, String>> iterator = slotHostMap.entrySet().iterator();
        while (iterator.hasNext()) {
            String hostAndPort = iterator.next().getValue();
            hostKeyListMaps.put(hostAndPort, new ArrayList<T>());
        }

        for (T t : list) {
            //获取槽号
            String idCacheKey = t.findIdKey(t);
            int slot = JedisClusterCRC16.getSlot(idCacheKey)+1;
            String hostPort = null;
            try {
                hostPort = slotHostMap.lowerEntry(Long.valueOf(slot)).getValue();
                //将数据放到相应的map下
                hostKeyListMaps.get(hostPort).add(t);
            } catch (Exception e) {
                LOGGER.error("缓存数据解析失败  KEY是：{}  solt 是{}  ip端口是 {}", t, slot, hostPort);
            }
        }
        return hostKeyListMaps;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> byCountAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < number + 1; i++) {
            List<T> val = null;
            if (number > 0) {
                if (i == number) {
                    val = source.subList(i * n, source.size());
                } else {
                    val = source.subList(i * n, (i + 1) * n);
                }
            } else {
                val = source.subList(i * n, source.size());
            }
            result.add(val);
        }
        return result;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @return
     */
    public static <T> T[] toArray(List<T> t, T[] original) {
        T[] a = Arrays.copyOf(original, t.size());
        T[] expect = t.toArray(a);
        return expect;
    }


    public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String value = null;
            field.setAccessible(true);
            if ("java.util.Date".equals(field.getType().getName())) {
                value = DateUtil.getYMDHMS((Date) field.get(obj));
                map.put(fieldName, value);
                continue;
            }
            try {
                value = (String) field.get(obj);
                if (null == value) {
                    value = "";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName, value);
        }
        return map;
    }

    public static <T> T mapToObject(Map<String, String> map, T t) throws IllegalAccessException {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if ("java.util.Date".equals(f.getType().getName())) {
                if (map.get(f.getName()) != null) {
                    f.set(t, DateUtil.getDateYYMMDDhhmmss(map.get(f.getName())));
                    continue;
                }

            }
            f.set(t, map.get(f.getName()));
        }
        return t;
    }

    public static TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
        TreeMap<Long, String> tree = new TreeMap<Long, String>();
        String parts[] = anyHostAndPortStr.split(":");
        HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
        try {
            Jedis jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
            List<Object> list = jedis.clusterSlots();
            for (Object object : list) {
                List<Object> list1 = (List<Object>) object;
                List<Object> master = (List<Object>) list1.get(2);
                String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
                tree.put((Long) list1.get(0), hostAndPort);
                tree.put((Long) list1.get(1), hostAndPort);
            }
            jedis.close();
        } catch (Exception e) {

        }
        return tree;
    }

    private static String getKey(String prefix, String... strs) {
        if (strs == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(prefix);
        int length = strs.length;
        if (length > 1) {
            for (int i = 0; i < length; i++) {
                if (i != length - 1) {
                    sb.append(strs[i]).append(RedisKeyPrefixConstant.REDIS_SPLIT);
                } else {
                    sb.append(strs[i]);
                }
            }
        } else {
            return sb.append(strs[0]).toString();
        }
        return sb.toString();
    }

}
