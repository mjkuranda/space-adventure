package cmpnts;

import java.awt.Color;
import java.awt.Graphics;

public class LoadingBar {

	private int x, y, width, height;
	private int max, curr;
	
	public LoadingBar(int curr, int max, int x, int y, int height) {
		this.curr = curr;
		this.max = max;
		
		this.x = x;
		this.y = y;
		this.width = (max * 16) + max + 2;
		this.height = height;
	}
	
	public void render(Graphics g) {
		// background
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		
		// border
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		
		// bar
		int w = (curr * 255) / max;
		int rc = 255 - w;
		int gc = 0 + w;
		g.setColor(new Color(rc, gc, 0));
		for(int b = 0; b < curr; b++) {
			g.fillRect(x + 2 + (b * 16) + b, y + 2, 16, height - 3);
		}
	}
	
	public void tick() {
		check();
	}
	
	public void add(int value) {
		this.curr += value;
	}
	
	private void check() {
		if(curr > max) curr = max;
	}
	
	public boolean isFill() {
		return (curr >= max);
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setValue(int val) {
		this.curr = val;
	}

}
