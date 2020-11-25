package com.temp.common.common.regx;

import com.alibaba.fastjson.JSON;
import com.temp.common.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
    public static void main(String args[]) {

        // 按指定模式在字符串查找
//      String line = "This order was select placed for 44QT3000! OK?33";
        String line = "This order was select placed fr 44QT3000! OK?33";
//      String pattern = "(\\D*)(\\d+)(.*)";
        String pattern = "(\\D*)(select|or)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find()) {
            System.out.println(m.groupCount());

            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        }
    }
}

class RegexMatches1 {
    private static final String REGEX = "\\bcat\\b";
    private static final String INPUT =
            "cat cat cat cattie cat";

    public static void main(String args[]) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT); // 获取 matcher 对象
        int count = 0;

        while (m.find()) {
            System.out.println(m.groupCount());
            System.out.println("Found value: " + m.group(0));
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + m.start());
            System.out.println("end(): " + m.end());
        }
    }
}

class RegexMatches2 {
    private static final String REGEX = "foo";
    private static final String INPUT = "fooooooooooooooooo";
    private static final String INPUT2 = "ooooofoooooooooooo";
    private static Pattern pattern;
    private static Matcher matcher;
    private static Matcher matcher2;

    public static void main(String args[]) {
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(INPUT);
        matcher2 = pattern.matcher(INPUT2);

        System.out.println("Current REGEX is: " + REGEX);
        System.out.println("Current INPUT is: " + INPUT);
        System.out.println("Current INPUT2 is: " + INPUT2);


        System.out.println("lookingAt(): " + matcher.lookingAt());
        System.out.println("matches(): " + matcher.matches());
        System.out.println("lookingAt(): " + matcher2.lookingAt());
    }
}

class RegexMatches3 {
    private static String REGEX = "dog";
    private static String INPUT = "The dog says meow. " +
            "All dogs say meow.";
    private static String REPLACE = "cat";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        // get a matcher object
        Matcher m = p.matcher(INPUT);
        INPUT = m.replaceAll(REPLACE);
        System.out.println(INPUT);
    }
}

class RegexMatches4 {
    private static String REGEX = "a*b";
    private static String INPUT = "aabfooaabfooabfoobkkk";
    private static String REPLACE = "-";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        // 获取 matcher 对象
        Matcher m = p.matcher(INPUT);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, REPLACE);
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
    }
}

class SqlFilter {

    public static void main(String args[]) {

        // 按指定模式在字符串查找
//        String sqlPattern="(selec)|(os)";
        String sqlPattern = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        String data = " select ";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(sqlPattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(data);
//        System.out.println(m.find()+"-----------");
        if (m.find()) {
            System.out.println("==========");
            System.out.println(m.group(0));
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
            System.out.println(m.groupCount());
        } else {
            System.out.println("NO MATCH");
        }
    }

}

class sssss {
    public static void main(String[] args) {
        Pattern compile = Pattern.compile("(\\sselect\\s|\\supdate\\s|and\\s|or\\s|\\sdelete\\s|\\sinsert\\s|\\strancate\\s|\\schar\\s|\\sinto\\s|\\ssubstr\\s|\\sascii\\s|\\sdeclare\\s|\\sexec\\s|\\scount\\s|\\smaster\\s|\\sinto\\s|\\sdrop\\s|\\sexecute\\s)|['#|\\\\/]");
        Matcher matcher = compile.matcher(" select ");
        boolean b = matcher.find();
        System.out.println(matcher.matches());
        System.out.println(matcher.groupCount());
        System.out.println(b);
    }
}


class ddlsql {
    public static void main(String[] args) {
        List<String> fields= new ArrayList<>();
        String ddl = "CREATE TABLE `any_data_hub`.`Untitled`  (\n" +
                "  `resource_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '函数信息ID',\n" +
                "  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '函数标识',\n" +
                "  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '函数名称',\n" +
                "  `content_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户号',\n" +
                "  `example` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '示例',\n" +
                "  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',\n" +
                "  `language` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '函数语言类型',\n" +
                "  `function_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '函数类型',\n" +
                "  `used` int(4) NULL DEFAULT NULL COMMENT '函数使用与否',\n" +
                "  `create_person` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',\n" +
                "  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "  `update_person` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',\n" +
                "  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',\n" +
                "  PRIMARY KEY (`resource_id`) USING BTREE,\n" +
                "  INDEX `idx_meta_function_info_name`(`name`) USING BTREE,\n" +
                "  INDEX `idx_meta_function_info_code`(`code`) USING BTREE\n" +
                ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '函数信息' ROW_FORMAT = Dynamic;";
//        Pattern pattern = Pattern.compile(".*?(?=\\()");
        Pattern pattern = Pattern.compile(".*?(?=\\))");
        Matcher matcher = pattern.matcher(ddl);
        while (matcher.find()) {
            System.out.println(matcher.group());
            if(!StringUtils.isEmpty(matcher.group())){
                fields.add(matcher.group()+")");

            }
        }
        System.out.println(JSON.toJSONString(fields));

    }
}


class test {
    public static void main(String[] args) {
        String text = "北京市(海淀区)(朝阳区)(西城区)";
        Pattern pattern = Pattern.compile(".*?(?=\\()");
        Matcher matcher = pattern.matcher(text);
//        if (matcher.find()) {
//            System.out.println(matcher.group(0));
//        }
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}