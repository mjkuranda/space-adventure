package objs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.core.Main;
import gfx.Assets;

public abstract class Object {

	protected String name;
	protected byte id;
	protected byte scale;
	protected int x, y;
	protected int distance;
	protected BufferedImage img;
	protected Rectangle rect;
	protected byte lifes;
	protected Bullet bull;
	
	public Object(String name, byte id, byte scale, int x, int y, int distance, BufferedImage img) {
		this.name = name;
		this.id = id;
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.img = img;
		int spc = (getSize() * 10) / 100;
		this.rect = new Rectangle(x + spc, y + spc, getSize() - (2 * spc), getSize() - (2 * spc));
		this.lifes = scale;
	}
	
	public void render(Graphics g, float x, float y) {
		int xx = (int) (this.x + x);
		int yy = (int) (this.y + y);
		
		int spc = (getSize() * 15) / 100;	
		this.setRect(new Rectangle(xx + spc, yy + spc, getSize() - (2 * spc), getSize() - (2 * spc)));
		
		g.drawImage(this.img, xx, yy, this.getSize(), this.getSize(), null);
		if(this.id == 2) {
			g.drawImage(Assets.fire_tex.getImage(), xx + (this.getSize() / 4), yy + (this.getSize() / 4), this.getSize() / 2, this.getSize() / 2, null);
		}
		
		if(Main.LINES) {
			g.setColor(Color.RED);
			g.drawRect(xx, yy, this.getSize(), this.getSize());
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.GREEN);
			g2d.draw(this.rect);	
		}
		
		if(this.bull != null) this.bull.render(g, xx, yy);
	}
	
	public void tick() {
	}
	
	public void closer() {
		this.distance++;
		
		if(this.distance % 2 == 0) {
			this.x--;
//			this.y--;
		}
	}
	
	public boolean isTooBig() {
		return (this.getDistance() >= 512) || (this.getSize() >= 1024);
	}
	
	public boolean isHalfBig() {
		return (this.getDistance() >= 256) || (this.getSize() >= 512);
	}
	
	public int getSize() {
		return (this.scale * this.distance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public byte getScale() {
		return scale;
	}

	public void setScale(byte scale) {
		this.scale = scale;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	public void takeLife() {
		this.lifes--;
	}
	
	public byte getLifes() {
		return lifes;
	}
	
//	public void addBullet(Bullet bullet) {
//		this.bull = bullet;
//	}
//	
//	public Bullet getBullet() {
//		return bull;
//	}

}
