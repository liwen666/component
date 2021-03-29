package com.temp.common.utils;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PlaceHolderReplaceUtilsTest {
    @Test
   public void name() {
        Map m = new HashMap<>();
        m.put("param1", "1");
        m.put("param2", "2");
        String str = "insert into a select * from b where param1=$ { p a ram1 } and param2  = $ {p aram2}";
        String s = PlaceHolderReplaceUtils.replacePlaceHolder(str, m);
        System.out.println(s);
    }

}
