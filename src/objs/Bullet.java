package objs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import engine.core.Main;
import scrns.GameScreen;

public class Bullet {

	// GameScreen
	private GameScreen gms;
	
	// coordinates of the destination
	private int xdes, ydes;
	
	// current position
	private float xcur, ycur;
	
	// aiming to the destination
	private double xa;
	private double ya;
	
	// length
	private byte length;
	
	// speed
	private byte speed;
	
	// collider
	private Rectangle rect;
	
	protected long lastTime, endTime;
	
	public Bullet(GameScreen gms, int xdes, int ydes, int xpos, int ypos) {
		this.xdes = xdes - xpos;
		this.ydes = ydes - ypos;
		
		this.gms = gms;
		this.speed = 16;
		this.length = 32;
		
		this.xcur = (Main.WIDTH / 2) - xpos;
		this.ycur = (Main.HEIGHT - 128) - ypos;
	
		this.rect = new Rectangle(this.xdes, this.ydes, xpos, ypos);
		
		this.lastTime = System.currentTimeMillis();
		this.endTime = System.currentTimeMillis() + 1200;
	}
	
	public void render(Graphics g, int x, int y) {
		g.setColor(Color.PINK);
//		g.drawLine((int) this.xcur, (int) this.ycur, this.xdes, this.ydes);
		g.drawLine((int) this.xcur + x, (int) this.ycur + y, (int) ((this.xcur) + this.xa * this.length) + x, (int) ((this.ycur) + this.ya * this.length) + y);
		g.drawLine((int) this.xcur + x - 1, (int) this.ycur + y, (int) ((this.xcur) + this.xa * this.length) + x - 1, (int) ((this.ycur) + this.ya * this.length) + y);
		g.drawLine((int) this.xcur + x + 1, (int) this.ycur + y, (int) ((this.xcur) + this.xa * this.length) + x + 1, (int) ((this.ycur) + this.ya * this.length) + y);
	
		this.rect = new Rectangle((int) (this.xcur + x), (int) (this.ycur + y), 16, 16);
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.fill(this.rect);
	}
	
	public void update() {
		double angle = Math.toDegrees(Math.atan2((double) (this.xdes - this.xcur),
				(double) (this.ydes - this.ycur)));

		this.xa = (float) Math.sin(Math.toRadians(angle));
		this.ya = (float) Math.cos(Math.toRadians(angle));

		this.xcur += this.xa * this.speed;
		this.ycur += this.ya * this.speed;
	}
	
	// it checks whether hit on asteroid and creates explosion
	public void kill() {
		List<Asteroid> as = this.gms.getAsteroids();
		List<Asteroid> ac = new LinkedList<Asteroid>();
		int id = -1;
		for(int a = 0; a < as.size(); a++) {
			if(this.rect.intersects(as.get(a).getRect())) {
				ac.add(as.get(a));
				if(id == -1) id = a;
			}
		}
		int dis = -1;
		for(int a = 0; a < ac.size(); a++) {
			if(dis < ac.get(a).getDistance()) {
				dis = ac.get(a).getDistance();
			}
		}
		
		Random r = new Random();
		
		if(id != -1 && this.gms.getAsteroids().get(id).getLifes() < 1) {
			Asteroid aster = this.gms.getAsteroids().get(id);
			int szas = aster.getSize() - (aster.getSize() * 40 / 100); // size of the asteroid
			byte scas = aster.getScale(); // scale of the asteroid
			byte cnex = (byte) (r.nextInt((aster.getScale() * 5)) + 12); // count of the explosions
			int xe = aster.getX() + (scas * 4);
			int ye = aster.getY() + (scas * 4);
			
			for(int c = 0; c < cnex; c++) {
				int xr = r.nextInt(szas);
				int yr = r.nextInt(szas);
				
				this.gms.addExplosion(new Explosion(xe + xr - (scas * 8), ye + yr - (scas * 8), r.nextInt(scas * 64 + 8)));
			}
			
			this.gms.getAsteroids().remove(id);
			int points = (r.nextInt(10) + (10 * scas)); if(points < 0) points *= -1;
//			System.out.println(points + ", " + scas);
			this.gms.addPoints(points);
		} else
		if(id != -1 && this.gms.getAsteroids().get(id).getLifes() > 0) {
			this.gms.getAsteroids().get(id).takeLife();
			this.gms.getAsteroids().get(id).distance -= 16;
			for(int e = 0; e < 3; e++) {
				int rx = (r.nextBoolean()) ? getRad() : getRad() * -1;
				int ry = (r.nextBoolean()) ? getRad() : getRad() * -1;
				this.gms.addExplosion(new Explosion((int) this.xcur - 32 + rx, (int) this.ycur - 32 + ry, 32));
			}
//			this.gms.addExplosion(new Explosion((int) this.xcur - 18, (int) this.ycur - 38, 32));
//			this.gms.addExplosion(new Explosion((int) this.xcur - 49, (int) this.ycur - 19, 32));
//			this.gms.addExplosion(new Explosion((int) this.xcur - 32, (int) this.ycur - 56, 32));
		}
		
		this.gms.addExplosion(new Explosion((int) this.xcur - 32, (int) this.ycur - 32, 32));
	}
	public void killShip() {
		List<Spaceship> as = this.gms.getSpaceships();
		List<Spaceship> ac = new LinkedList<Spaceship>();
		int id = -1;
		for(int a = 0; a < as.size(); a++) {
			if(this.rect.intersects(as.get(a).getRect())) {
				ac.add(as.get(a));
				if(id == -1) id = a;
			}
		}
		int dis = -1;
		for(int a = 0; a < ac.size(); a++) {
			if(dis < ac.get(a).getDistance()) {
				dis = ac.get(a).getDistance();
			}
		}
		
		Random r = new Random();
		
		if(id != -1 && this.gms.getSpaceships().get(id).getLifes() < 1) {
			Spaceship aster = this.gms.getSpaceships().get(id);
			int szas = aster.getSize() - (aster.getSize() * 40 / 100); // size of the asteroid
			byte scas = aster.getScale(); // scale of the asteroid
			byte cnex = (byte) (r.nextInt((aster.getScale() * 5)) + 12); // count of the explosions
			int xe = aster.getX() + (scas * 4);
			int ye = aster.getY() + (scas * 4);
			
			for(int c = 0; c < cnex; c++) {
				int xr = r.nextInt(szas);
				int yr = r.nextInt(szas);
				
				this.gms.addExplosion(new Explosion(xe + xr - (scas * 8), ye + yr - (scas * 8), r.nextInt(scas * 64 + 8)));
			}
			
			this.gms.getSpaceships().remove(id);
			int points = (r.nextInt(10) + (20 * scas)); if(points < 0) points *= -1;
			this.gms.addPoints(points);
		} else
		if(id != -1 && this.gms.getSpaceships().get(id).getLifes() > 0) {
			this.gms.getSpaceships().get(id).takeLife();
			this.gms.getSpaceships().get(id).distance -= 16;
			for(int e = 0; e < 3; e++) {
				int rx = (r.nextBoolean()) ? getRad() : getRad() * -1;
				int ry = (r.nextBoolean()) ? getRad() : getRad() * -1;
				this.gms.addExplosion(new Explosion((int) this.xcur - 32 + rx, (int) this.ycur - 32 + ry, 32));
			}
		}
		
		this.gms.addExplosion(new Explosion((int) this.xcur - 32, (int) this.ycur - 32, 32));
	}
	public void killMeteor() {
		List<Meteoroid> as = this.gms.getMeteoroids();
		List<Meteoroid> ac = new LinkedList<Meteoroid>();
		int id = -1;
		for(int a = 0; a < as.size(); a++) {
			if(this.rect.intersects(as.get(a).getRect())) {
				ac.add(as.get(a));
				if(id == -1) id = a;
			}
		}
		int dis = -1;
		for(int a = 0; a < ac.size(); a++) {
			if(dis < ac.get(a).getDistance()) {
				dis = ac.get(a).getDistance();
			}
		}
		
		Random r = new Random();
		
		if(id != -1 && this.gms.getMeteoroids().get(id).getLifes() < 1) {
			Meteoroid aster = this.gms.getMeteoroids().get(id);
			int szas = aster.getSize() - (aster.getSize() * 40 / 100); // size of the asteroid
			byte scas = aster.getScale(); // scale of the asteroid
			byte cnex = (byte) (r.nextInt((aster.getScale() * 5)) + 12); // count of the explosions
			int xe = aster.getX() + (scas * 4);
			int ye = aster.getY() + (scas * 4);
			
			for(int c = 0; c < cnex; c++) {
				int xr = r.nextInt(szas);
				int yr = r.nextInt(szas);
				
				this.gms.addExplosion(new Explosion(xe + xr - (scas * 8), ye + yr - (scas * 8), r.nextInt(scas * 64 + 8)));
			}
			
			this.gms.getMeteoroids().remove(id);
			int points = (r.nextInt(10) + (15 * scas)); if(points < 0) points *= -1;
			this.gms.addPoints(points);
		} else
		if(id != -1 && this.gms.getMeteoroids().get(id).getLifes() > 0) {
			this.gms.getMeteoroids().get(id).takeLife();
			this.gms.getMeteoroids().get(id).distance -= 16;
			for(int e = 0; e < 3; e++) {
				int rx = (r.nextBoolean()) ? getRad() : getRad() * -1;
				int ry = (r.nextBoolean()) ? getRad() : getRad() * -1;
				this.gms.addExplosion(new Explosion((int) this.xcur - 32 + rx, (int) this.ycur - 32 + ry, 32));
			}
		}
		
		this.gms.addExplosion(new Explosion((int) this.xcur - 32, (int) this.ycur - 32, 32));
	}
	
	private int getRad() {
		Random r = new Random();
		return r.nextInt(32);
	}
	
	public boolean isHit() {
//		System.out.println("X: " + (this.xdes - this.xcur) + ", Y: " + (this.ydes - this.ycur));
		return (Math.abs(this.xdes - this.xcur) < 5) && (Math.abs((this.ydes - this.ycur)) < 10);
	}
	
	public boolean isLonger() {
		this.lastTime = System.currentTimeMillis();
		return (this.lastTime > this.endTime);
	}

}
