package com.temp.common.base.seal.font;

import sun.font.FontDesignMetrics;
 
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
 
/**
 * Created by zengrenyuan on 18/5/11.
 */
public class ImageTest {
 
    public static void main(String[] args) throws IOException {
        Font font = new Font("微软雅黑", Font.BOLD, 60);
        String content = "你好Java!";
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = getWordWidth(font, content);//计算图片的宽
        int height = metrics.getHeight();//计算高
        BufferedImage bufferedImage = new BufferedImage(width, height+50, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        //设置背影为白色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        graphics.drawString(content, 0, metrics.getAscent());//图片上写文字
        graphics.dispose();
        write(bufferedImage, "D:\\idea2018workspace\\component_new\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\seal\\font\\test.png");
 
 
    }
 
    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }
 
    public static void write(BufferedImage bufferedImage, String target) throws IOException {
        File file = new File(target);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (OutputStream os = new FileOutputStream(target)) {
            ImageIO.write(bufferedImage, "PNG", os);
        }
    }
 
}