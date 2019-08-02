package com.temp.common.base.util.bigdecimal;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.temp.common.common.util.DateUtil;
import com.temp.common.common.util.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * author lw
 * date 2019/8/1  16:51
 * discribe
 */
public class DateUtilTest {
    @Test
    public void name() {
        String newDate = DateUtils.getNewDate();
        System.out.println(newDate);
        int currentDay = DateUtils.getCurrentDay();
        System.out.println(currentDay);
        Date lastMaxDay = DateUtils.getLastMaxDay(DateUtils.parse("2010-01-31 11:00:00"));
        System.out.println(DateUtils.format(lastMaxDay,"yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtils.isMaxDayOfDay(lastMaxDay));

        Date lastMinDay = DateUtils.getLastMinDay(DateUtils.parse("2010-01-31 11:00:00"));
        System.out.println(DateUtils.format(lastMinDay,"yyyy-MM-dd HH:mm:ss"));
    }
}
