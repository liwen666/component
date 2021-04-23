package com.temp.common.complier.test.demo3;

import anycfs.common.util.ISO8601Utils;
import org.apache.flink.table.functions.ScalarFunction;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;

/**
 * @Author jie.lan
 * @Date 2021/3/22 20:07
 */
public class ReturnDate  extends ScalarFunction {
    public Date eval(Integer interval) {
        if(interval!=null){
            return new Date(interval*24*60*60*1000l);
        }
        return null;

    }
    public Timestamp eval(String timestamp ) {
        if(timestamp!=null){
            java.util.Date date=null;
            try {
                date = ISO8601Utils.parse(timestamp, new ParsePosition(0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Timestamp(date.getTime());
        }
        return null;

    }
    public Timestamp eval(Long unixtime ) {
        if(unixtime!=null){
            return new Timestamp(unixtime-8*60*60*1000L);
        }
        return null;

    }

}
