package cmpnts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.MouseManager;
import gfx.Assets;

public class Button {

	private GraphicsMain gm;
	
	private int text;
	private int x, y;
	private int width, height;
	
	private Rectangle rect;
	
	public Button(GraphicsMain gm, int idtx, int x, int y, int width, int height) {
		this.gm = gm;
		
		this.text = idtx;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.rect = new Rectangle(x, y, width, height);
	}
	
	public void render(Graphics g) {
		//Graphics2D g2d = (Graphics2D) g;
		//
		
		g.setColor(Color.GRAY);
		g.fill3DRect(x, y, width, height, true);
		
		
		if(isColl(gm.getMouseColl(), rect)) {
			g.setColor(Color.WHITE);
		} else g.setColor(Color.BLACK);
		
		g.setFont(Assets.px_28);
		
		int spc = g.getFontMetrics().stringWidth(Main.lang_main[text]);
		int spcY = g.getFontMetrics().getHeight();
		
		g.drawString(Main.lang_main[text], x + (width / 2) - (spc / 2), y + (height / 2) + (spcY / 4));
	}
	
	public void tick() {
	}
	
	private boolean isColl(Rectangle r1, Rectangle r2) {
		return (r1.intersects(r2));
	}
	
	public boolean isClicked() {
		MouseManager mm = gm.getHandler().getMouseManager();
		return (isColl(rect, gm.getMouseColl()) && (mm.isClicked()));
	}

}
