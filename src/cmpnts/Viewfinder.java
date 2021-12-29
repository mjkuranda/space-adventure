package cmpnts;

import java.awt.Color;
import java.awt.Graphics;

public class Viewfinder {

	private int x, y;
	private int width, height;
	
	private int xmm, ymm;
	
	public Viewfinder(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g) {
		for(int l = -1; l < 2; l++) {
			if(l != 0) g.setColor(Color.BLACK);
			else g.setColor(Color.RED);
			
			g.drawLine(this.xmm - this.width, this.ymm + l, this.xmm + this.width, this.ymm + l);
		
			g.drawLine(this.xmm + l, this.ymm - this.width, this.xmm + l, this.ymm + this.height);
		}
	}
	
	public void update(int xmm, int ymm) {
		this.xmm = xmm;
		this.ymm = ymm;
	}

}
