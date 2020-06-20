package com.temp.common.base.seal.font;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rotate {

 /**
  * @param args
  */
 public static void main(String[] args) {
  JFrame jf = new JFrame();
  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  jf.getContentPane().add(new RotatePanel());
  jf.setPreferredSize(new Dimension(500, 400));
  jf.pack();
  jf.setVisible(true);

 }

}

class RotatePanel extends JPanel {
 protected void paintComponent(Graphics g) {
  super.paintComponent(g);
  Graphics2D g2d = (Graphics2D) g;

  g2d.setColor(Color.WHITE);
  g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

  String s = "Java 2d 旋转";
  Font f = new Font("宋体", Font.BOLD, 16);
  Color[] colors = { Color.ORANGE, Color.LIGHT_GRAY };
  g2d.setFont(f);

  //   平移原点到图形环境的中心
  g2d.translate(this.getWidth() / 2, this.getHeight() / 2);

  //   旋转文本
  for (int i = 0; i < 12; i++) {
//   g2d.rotate(30 * Math.PI / 180);
   g2d.rotate(Math.PI);
   g2d.setPaint(colors[i % 2]);
   g2d.drawString(s, 0, 0);
  }
 }



}