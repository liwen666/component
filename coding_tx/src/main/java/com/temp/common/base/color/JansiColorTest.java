package com.temp.common.base.color;

import org.fusesource.jansi.Ansi;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.fusesource.jansi.Ansi.Color.*;

public class JansiColorTest {
    //    /**
//     * final method in order to force to use the subclasses to define the execute() method.
//     */
//    protected final Object doExecute() throws Exception {
//        try {
//            return execute();
//        } catch (Throwable e) {
//            if (isStackTraceEnabled()) {
//                throw e;
//            }
//            Ansi buffer = Ansi.ansi();
//            buffer.fg(RED);
//            buffer.a(e.getMessage());
//            buffer.reset();
//            System.out.println(buffer.toString());
//        }
//        return null;
//    }
    @Test
    public void dem1() throws IOException {
//        033[0m 阻止下文变色配置
        /**
         * 改变字体和背景色，添加到字体上
         */
//        Ansi.ansi().eraseScreen().bgYellow().fg(GREEN).a(colorTx1)

        System.out.println("没变色\033[42;31;4m我滴个颜什\033[0m没变色");
        System.out.println("\033[42;31m我滴个颜什\033[0m");
        System.out.println("jjjjj");
        System.out.println("\033[30;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[31;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[32;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[33;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[34;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[35;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[36;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[37;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[40;31;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[41;32;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[42;33;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[43;34;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[44;35;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[45;36;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[46;37;4m" + "我滴个颜什" + "\033[0m");
        System.out.println("\033[47;4m" + "我滴个颜什" + "\033[0m");
        String colorTx = "测试颜色";
        Ansi a = Ansi.ansi().eraseScreen().fg(RED).a(colorTx);
        System.out.println(a + "------------------------");
        Ansi reset = Ansi.ansi().eraseScreen().fg(GREEN).reset();
        System.out.println(reset);
        String otherMethod = "修改系统颜色";
        System.out.println(otherMethod);
        Ansi ansi = Ansi.ansi().eraseScreen().fg(GREEN).reset().fgYellow();
        System.out.println(ansi);
        System.out.println(otherMethod);
        Ansi ansi1 = Ansi.ansi().eraseScreen().fgBright(RED).reset();
        System.out.println(ansi1);
        System.out.println(otherMethod);
        String colorTx1 = "变色测试";
        Ansi reset1 = Ansi.ansi().eraseScreen().fg(GREEN).a(colorTx1).reset();
        System.out.println(reset1);
        Ansi reset2 = Ansi.ansi().eraseScreen().fgYellow().a(colorTx1).reset();
        System.out.println(reset2);
        Ansi reset3 = Ansi.ansi().eraseScreen().fgBrightYellow().a(colorTx1).reset();
        System.out.println(reset3);
        Ansi reset4 = Ansi.ansi().eraseScreen().fgBrightCyan().a(colorTx1).reset();
        System.out.println(reset4);
        Ansi reset5 = Ansi.ansi().eraseScreen().fgBrightMagenta().a(colorTx1).reset();
        System.out.println(reset5);
        Ansi reset6 = Ansi.ansi().eraseScreen().fgBlack().a(colorTx1);
        System.out.println(reset6);
        Ansi reset7 = Ansi.ansi().eraseScreen().bgYellow().fg(GREEN).a(colorTx1);
        System.out.println(reset7 + "---背景色改变");
        Ansi reset8 = Ansi.ansi().eraseScreen().reset();
        System.out.println(reset8);
        System.out.println("==================================");
        File file = new File("E:\\github_program\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\linux\\test\\test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fo = new FileOutputStream(file);
        fo.write("\033[42;31;4m我滴个颜什\033[0m".getBytes());
        fo.flush();
        fo.close();

        FileInputStream fi = new FileInputStream(file);
        byte [] b = new byte[1000];
        int read = fi.read(b);
        String readStr = new String(b,0,read);
        System.out.println(readStr);
        fi.close();
    }


}
