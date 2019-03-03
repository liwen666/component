package com.temp.common.script_gam.system.mouse;

//例子3

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Example18_3 extends Applet implements MouseListener {
    TextField text;
    Button button;
    TextArea textArea;

    public void init() {
        text = new TextField(10);
        text.addMouseListener(this);
        button = new Button("按钮");
        button.addMouseListener(this);
        addMouseListener(this);
        textArea = new TextArea(8, 28);
        add(button);
        add(text);
        add(textArea);
    }

    public void mousePressed(MouseEvent e) {
        if (e.getSource() == button) {
            textArea.append("\n在按钮上鼠标按下,位置:" + "(" + e.getX() + "," + e.getY() + ")");
        } else if (e.getSource() == text) {
            textArea.append("\n在文本框上鼠标按下,位置:" + "(" + e.getX() + "," + e.getY() + ")");
        } else if (e.getSource() == this) {
            textArea.append("\n在容器上鼠标按下,位置:" + "(" + e.getX() + "," + e.getY() + ")");
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2)
            textArea.setText("鼠标连击，位置:" + "(" + e.getX() + "," + e.getY() + ")");
    }
}