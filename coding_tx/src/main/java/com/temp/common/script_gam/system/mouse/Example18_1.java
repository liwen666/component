package com.temp.common.script_gam.system.mouse;

import java.applet.*;import java.awt.*;
import java.awt.event.*;
public class Example18_1 extends Applet implements MouseListener
{ TextField text;
 public void init()
 { text=new TextField(40); add(text);
 addMouseListener(this) ;//向小程序增加鼠标事件监视器。
 }

    public void mousePressed(MouseEvent e) {
        text.setText("鼠标键按下了,位置是" + e.getX() + "," + e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        text.setText(" 鼠标松开了,位置是" + e.getX() + "," + e.getY());
    }

    public void mouseEntered(MouseEvent e) {
        text.setText(" 鼠标进来了,位置是" + e.getX() + "," + e.getY());
    }

    public void mouseExited(MouseEvent e) {
        text.setText(" 鼠标走开了");
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            text.setText("鼠标键双击,位置:" + e.getX() + "," + e.getY());
        } else {
        }
    }
}
