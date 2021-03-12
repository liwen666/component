package com.temp.common.complier.tools;

import static org.junit.Assert.*;

public class JavaParseObjectTest {


    public static void main(String[] args) {
        String test = "package com.temp.common.complier.test.demo1;\n" +
                "import java.util.Map;\n" +
                "//\n" +
                "  //\n" +
                "/**\n" +
                " * <p>\n" +
                " * 描述\n" +
                " * </p>\n" +
                " *\n" +
                " * @author LW\n" +
                " * @since 2021/3/11  13:57\n" +
                " */\n" +
                "public class HelloWord {\n" +
                "    public static void main(String[] args) {\n" +
                "        /*---------------------------------------------------------------------------------/\n" +
                "jnfdaljf\n" +
                "        /---------------------------------------------------------------------------------*/\n" +
                "        /**\n" +
                "         *\n" +
                "         *\n" +
                "         * kfjdkla\n" +
                "         */\n" +
                "\n" +
                "\n" +
                "        ///\n" +
                "        ///\n" +
                "\n" +
                "\n" +
                "        //\n" +
                "        System.out.println(\"hello word!\");//fdafjdajfldka\n" +
                "    }\n" +
                "}\n";
        JavaParseObject build = new JavaParseObject().build(test);
        System.out.println(build.getSource());

        String source = build.changePackage("test", "test");
        System.out.println(source);

    }

}

 class test {
    public static void main(String[] args) {
        System.out.println("hello word!");//fdafjdajfldka
    }
}



