package com.bms.ui;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DefaultPanel extends JPanel{
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);                     
		//获取图像
		ImageIcon image = new ImageIcon(getClass().getResource("/image/bg.jpg"));        
		//调整图像的分辨率以适应容器
		g.drawImage(image.getImage(), 0, 0, getSize().width,
			     getSize().height, this);// 图片会自动缩放
	}

}
