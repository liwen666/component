package com.temp.common.script_gam.system.mouse;

import java.awt.*;
import java.awt.event.*;

class MyCanvas extends Canvas implements MouseListener {
    int left = -1, right = -1; //记录左、右键用的变量。
    int x = -1, y = -1; //记录鼠标位置用的变量。

    MyCanvas() {
        setSize(100, 100);
        setBackground(Color.cyan);
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        if (left == 1) {
            g.drawOval(x - 10, y - 10, 20, 20);
        } else if (right == 1) {
            g.drawRect(x - 8, y - 8, 16, 16);
        }
    }

    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
            left = 1;
            right = -1;
            repaint();
        } else if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
            right = 1;
            left = -1;
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        left = -1;
        right = -1;
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void update(Graphics g) {
        if (left == 1 || right == 1) {
            paint(g);
        } else {
            super.update(g);
        }
    }
}

public class Example18_2 {
    public static void main(String args[]) {
        Frame f = new Frame();
        f.setBounds(100, 100, 200, 200);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() //适配器
        {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.add(new MyCanvas(), BorderLayout.CENTER);//添加画布。
        f.validate();
    }
}
