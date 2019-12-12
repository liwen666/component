package com.temp.common.base.JDBC.greeplum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> 泛型
 */
public class MySubTUtil<T> {

    private static final Logger logger = LoggerFactory.getLogger(MySubTUtil.class);

    /**
     * 截取list集合，返回list集合
     *
     * @param tList  (需要截取的集合)
     * @param subNum (每次截取的数量)
     * @return
     */
    public static <T> List<List<T>> subList(List<T> tList, Integer subNum) {
        // 新的截取到的list集合
        List<List<T>> tNewList = new ArrayList<List<T>>();
        // 要截取的下标上限
        Integer priIndex = 0;
        // 要截取的下标下限
        Integer lastIndex = 0;
        // 每次插入list的数量
        // Integer subNum = 500;
        // 查询出来list的总数目
        Integer totalNum = tList.size();
        // 总共需要插入的次数
        Integer insertTimes = totalNum / subNum;
        List<T> subNewList = new ArrayList<T>();
        for (int i = 0; i <= insertTimes; i++) {
            // [0--20) [20 --40) [40---60) [60---80) [80---100)
            priIndex = subNum * i;
            lastIndex = priIndex + subNum;
            // 判断是否是最后一次
            if (i == insertTimes) {
                logger.info("最后一次截取：" + priIndex + "," + lastIndex);
                subNewList = tList.subList(priIndex, tList.size());
            } else {
                // 非最后一次
                subNewList = tList.subList(priIndex, lastIndex);

            }
            if (subNewList.size() > 0) {
                //logger.info("开始将截取的list放入新的list中");
                tNewList.add(subNewList);
            }

        }

        return tNewList;

    }

    /**
     * 截取list集合，返回map集合
     *
     * @param tList  (需要截取的集合)
     * @param subNum (每次截取的数量)
     * @return
     */
    public static <T> Map<Integer, List<T>> subListToMap(List<T> tList, Integer subNum) {
        // 新的截取到的list集合
        //List<List<T>> tNewList = new ArrayList<List<T>>();
        Map<Integer, List<T>> newTlsMap = new HashMap<Integer, List<T>>();
        // 要截取的下标上限
        Integer priIndex = 0;
        // 要截取的下标下限
        Integer lastIndex = 0;
        // 每次插入list的数量
        // Integer subNum = 500;
        // 查询出来list的总数目
        Integer totalNum = tList.size();
        // 总共需要插入的次数
        Integer insertTimes = totalNum / subNum;
        List<T> subNewList = new ArrayList<T>();
        for (int i = 0; i <= insertTimes; i++) {
            // [0--20) [20 --40) [40---60) [60---80) [80---100)
            priIndex = subNum * i;
            lastIndex = priIndex + subNum;
            // 判断是否是最后一次
            if (i == insertTimes) {
                //logger.info(priIndex + "," + tList.size());
                //logger.info("--------------------------------------");
                subNewList = tList.subList(priIndex, tList.size());
            } else {
                // 非最后一次
                //logger.info("最后一次截取："+priIndex + "," + lastIndex);
                //logger.info("***************************************");
                subNewList = tList.subList(priIndex, lastIndex);

            }
            if (subNewList.size() > 0) {
                //logger.info("开始将截取的list放入新的list中");
                newTlsMap.put(i, subNewList);
            }

        }

        return newTlsMap;
    }
}