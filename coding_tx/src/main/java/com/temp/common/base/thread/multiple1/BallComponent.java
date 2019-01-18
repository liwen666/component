package com.temp.common.base.thread.multiple1;
//BallComponent.java

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallComponent extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5266116769281494567L;

	public void add(Ball b)
	{
		balls.add(b);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for(Ball b : balls)
		{
			g2.fill(b.getShape());
		}
	}
	
	private ArrayList<Ball> balls = new ArrayList<Ball>();
}