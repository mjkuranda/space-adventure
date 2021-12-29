package scrns;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cmpnts.CabineRender;
import cmpnts.KeyLeader;
import cmpnts.Viewfinder;
import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.MouseManager;
import gfx.Assets;
import objs.Asteroid;
import objs.Bullet;
import objs.Explosion;
import objs.Meteoroid;
import objs.Spaceship;

public class GameScreen extends Screen {

	public static int part_scr_x = Main.WIDTH / 3;
	public static int part_scr_y = Main.HEIGHT / 3; 
	
	private int time = 30;
	private int count = 0;
	
	private byte level_min = 4, level_max = 25;
	
	private float spaceship_speed;
	
	private float xpos, ypos;
	private int mapwidth, mapheight;
	private float approx;
	
	private GraphicsMain gm;
	private CabineRender cbRen;
	private Viewfinder vwfind;
	private KeyLeader keyLeader;
	
	private List<Asteroid> objects;
	private List<Spaceship> ufos;
	private List<Meteoroid> meteor;
	private List<Bullet> bullets;
	private List<Explosion> explsns;
	
	private int points, strenght;
	private Rectangle rect;
	
	private long timeF, lastF;
	
	private String nickname;
	
	// when rocking
	private int rocking = 0;
	private long strike;
	
	public GameScreen(GraphicsMain gm) {
		this.gm = gm;
		
		this.objects = new LinkedList<Asteroid>();
		this.ufos = new LinkedList<Spaceship>();
		this.meteor = new LinkedList<Meteoroid>();
		this.bullets = new LinkedList<Bullet>();
		this.explsns = new LinkedList<Explosion>();
	}
	
	@Override
	public void init() {
		this.spaceship_speed = 14.5f;
		this.mapwidth = 3200 - ((Main.WIDTH == 1366) ? 260: 0);
		this.mapheight = 3200 + 400 + ((Main.HEIGHT == 768) ? 580 : 0);
		
		this.xpos = (this.mapwidth / 2) * -1;
		this.ypos = (this.mapheight / 2) * -1;
		
		this.cbRen = new CabineRender(gm, this, (int) (xpos), (int) (ypos));
		this.vwfind = new Viewfinder(Main.WIDTH / 2, Main.HEIGHT / 2, 16, 16);
		this.keyLeader = new KeyLeader(gm, this);
		
		this.rect = new Rectangle(32, 32, Main.WIDTH - 1 - 64, Main.HEIGHT - 1 - 192);
		
		this.points = 0;
		this.approx = 0;
		this.rocking = 0;
		this.strenght = 499;
		
		for(int i = 0; i < this.objects.size(); i++) {
			this.objects.remove(i);
			i--;
		}
		
		for(int i = 0; i < this.ufos.size(); i++) {
			this.ufos.remove(i);
			i--;
		}
		
		for(int i = 0; i < this.meteor.size(); i++) {
			this.meteor.remove(i);
			i--;
		}
		
		for(int i = 0; i < this.bullets.size(); i++) {
			this.bullets.remove(i);
			i--;
		}
		
		for(int i = 0; i < this.explsns.size(); i++) {
			this.explsns.remove(i);
			i--;
		}
		
		this.time = level_max;
		this.lastF = System.currentTimeMillis();
		this.timeF = System.currentTimeMillis();
	}

	@Override
	public void render(Graphics g) {
		int xscale = (int) (approx);
		int yscale = (int) (approx);
		int xpar = (int) (xpos / 15);
		int ypar = (int) (ypos / 15);
		g.drawImage(Assets.main_background, 0 - (xscale / 2) + xpar - 50, 0 - (yscale / 2) + ypar - 50, 2020 + xscale, 1180 + yscale, null);
		
		for(int o = this.objects.size(); o > 0; o--) {
			this.objects.get(o - 1).render(g, xpos, ypos);
			
//			int x = this.objects.get(o - 1).getX();
//			int y = this.objects.get(o - 1).getY();
//			g.setColor(Color.WHITE);
//			g.drawString("Sc: " + (this.objects.get(o - 1).getScale()), (int) (x + xpos), (int) (y + ypos));
		}
		
		for(int o = this.ufos.size(); o > 0; o--) {
			this.ufos.get(o - 1).render(g, xpos, ypos);
//			if(this.ufos.get(o - 1).getBullet() != null) {
//				this.ufos.get(o - 1).getBullet().render(g, (int) xpos, (int) ypos);
//			}
		}
		
		for(int o = this.meteor.size(); o > 0; o--) {
			this.meteor.get(o - 1).render(g, xpos, ypos);
		}
		
		for(int b = 0; b < this.bullets.size(); b++) {
			this.bullets.get(b).render(g, (int) xpos, (int) ypos);
		}
		
		for(int e = 0; e < this.explsns.size(); e++) {
			this.explsns.get(e).render(g, (int) xpos, (int) ypos);
		}
		
//		g.setColor(Color.RED);
//		g.drawLine(part_scr_x * 1, part_scr_y * 0, part_scr_x * 1, part_scr_y * 3);
//		g.drawLine(part_scr_x * 2, part_scr_y * 0, part_scr_x * 2, part_scr_y * 3);
//		g.drawLine(part_scr_x * 0, part_scr_y * 1, part_scr_x * 3, part_scr_y * 1);
//		g.drawLine(part_scr_x * 0, part_scr_y * 2, part_scr_x * 3, part_scr_y * 2);
		
		cbRen.render(g);
		vwfind.render(g);
		
		if(Main.LINES) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.WHITE);
			g2d.draw(rect);	
			
			g.setFont(new Font("Consolas", Font.PLAIN, 12));
			g.setColor(Color.WHITE);
			g.drawString("Time: " + time + ", count: " + count + ", approx: " + approx, 10, 10);
			g.drawString("XPOS: " + xpos + ", YPOS: " + ypos, 10, 30);
		}
	}

	@Override
	public void tick() {
		keyLeader.tick();
		
		if(getStrenght() <= 0) {
			gm.getEndScreen().reset();
			gm.giveData(nickname, points);
			gm.setScreen("END");
		}
		
		// light movement
		if(this.rocking > 0) {
			Random rr = new Random();
			int prx = rr.nextInt(this.rocking);
			int pry = rr.nextInt(this.rocking);
			
			int lmx = (rr.nextBoolean() ? prx : prx * -1);
			int lmy = (rr.nextBoolean() ? pry : pry * -1);
			this.gm.getGameScreen().getCabine().giveMovement(lmx, lmy);
			this.xpos += lmx;
			this.ypos += lmy;
			
			if(System.currentTimeMillis() > this.strike + 350) {
				this.rocking--;
				this.strike = System.currentTimeMillis();	
			}
		}
		
		int xp = (int) (xpos * -1.0f);
		int yp = (int) (ypos * -1.0f);
		
		if(xp < 0) xpos = 0;
		if(yp < 0) ypos = 0;
		if(xp > mapwidth) xpos = mapwidth * -1; 
		if(yp > mapheight) ypos = mapheight * -1;
		
		this.rect = new Rectangle(32, 32, Main.WIDTH - 1 - 64, Main.HEIGHT - 1 - 192);
		
		for(int o = 0; o < this.objects.size(); o++) {
			Random r = new Random();
			this.objects.get(o).closer();
//			if(this.objects.get(o).isTooBig()) {
//
//				Asteroid as = this.objects.get(o);
//				if(as.getRect().intersects(this.rect)) {
//					byte damage = (byte) (as.getScale() * 10 + r.nextInt(10));
//				
//					this.takeDamage(damage);
//					int sp = as.getSize() * 15 / 100;
//					this.addExplosion(new Explosion(as.getX() - sp, as.getY() - sp, as.getSize() - (sp * 2)));
//					
//					this.objects.remove(o);
//				} else {
//					
//				}
//			}
			
			Asteroid as = this.objects.get(o);
			if(as.getY() > mapheight) {
				this.objects.remove(o);
			} else
			if(as.getRect().intersects(this.rect)) {
				if(this.objects.get(o).isTooBig()) {
					byte damage = (byte) (as.getScale() * 10 + r.nextInt(10));
					
					this.takeDamage(damage);
					int sp = as.getSize() * 15 / 100;
					this.addExplosion(new Explosion(as.getX() - sp, as.getY() - sp, as.getSize() - (sp * 2)));
					
					this.objects.remove(o);	
				}
			} else {
				if(this.objects.get(o).isHalfBig()) {
					this.objects.remove(o);
				}
			}
		}
		
		for(int o = 0; o < this.ufos.size(); o++) {
			this.ufos.get(o).closer();
			
			Random r = new Random();
			Spaceship as = this.ufos.get(o);
			if(as.getY() > mapheight) {
				this.ufos.remove(o);
			} else
			if(as.getRect().intersects(this.rect)) {
				if(this.ufos.get(o).isTooBig()) {
					byte damage = (byte) (as.getScale() * 20 + r.nextInt(10));
					
					this.takeDamage(damage);
					int sp = as.getSize() * 15 / 100;
					this.addExplosion(new Explosion(as.getX() - sp, as.getY() - sp, as.getSize() - (sp * 2)));
					
					this.ufos.remove(o);	
				}
//				else if(this.ufos.get(o).getDistance() >= 4 && ufos.get(o).getBullet() == null) {
//					int xpos = this.ufos.get(o).getX();
//					int ypos = this.ufos.get(o).getY();
//					int spc = as.getSize() * 15 / 100;
//					this.ufos.get(o).addBullet(new Bullet(this, r.nextInt(Main.WIDTH), r.nextInt(Main.HEIGHT), xpos, ypos));
//					this.bullets.add(new Bullet(this, xpos + r.nextInt(Main.WIDTH), ypos + r.nextInt(Main.HEIGHT), xpos + (spc / 2), ypos + (spc / 2)));
//				}
//				if(this.ufos.get(o).getBullet() != null) {
//					this.ufos.get(o).getBullet().update();
//				}
			} else {
				if(this.ufos.get(o).isHalfBig()) {
					this.ufos.remove(o);
				}
			}
		}
		
		for(int o = 0; o < this.meteor.size(); o++) {
			this.meteor.get(o).closer();
			
			Random r = new Random();
			Meteoroid as = this.meteor.get(o);
			if(as.getY() > mapheight) {
				this.meteor.remove(o);
			} else
			if(as.getRect().intersects(this.rect)) {
				if(this.meteor.get(o).isTooBig()) {
					byte damage = (byte) (as.getScale() * 15 + r.nextInt(10));
					
					this.takeDamage(damage);
					int sp = as.getSize() * 15 / 100;
					this.addExplosion(new Explosion(as.getX() - sp, as.getY() - sp, as.getSize() - (sp * 2)));
					
					this.meteor.remove(o);	
				}
			} else {
				if(this.meteor.get(o).isHalfBig()) {
					this.meteor.remove(o);
				}
			}
		}
		
		for(int b = 0; b < this.bullets.size(); b++) {
			this.bullets.get(b).update();
			
			if(this.bullets.get(b).isLonger() || this.bullets.get(b).isHit()) {
				this.bullets.get(b).kill();
				this.bullets.get(b).killShip();
				this.bullets.get(b).killMeteor();
				this.bullets.remove(b);
				b--;
			}
		}
		
		for(int e = 0; e < this.explsns.size(); e++) {
			this.explsns.get(e).tick();
			
			if(this.explsns.get(e).isEndful()) {
				this.explsns.remove(e);
				e--;
			}
		}
		
		approx += 0.12f;
		
		cbRen.tick(this.objects, this.meteor, this.ufos, this.strenght, (int) xpos, (int) ypos);
		
		MouseManager mm = this.gm.getHandler().getMouseManager();
		vwfind.update(mm.getXMoved(), mm.getYMoved());
		
		if(mm.isClicked()) {
			mm.clicked();
			if(this.getCabine().isReadyCannon()) {
				this.getCabine().shoot();
				this.bullets.add(new Bullet(this, 3400, 3400, (int) xpos, (int) ypos));	
			}
		}
		
		Random r = new Random();
		if(r.nextInt(time) == 0) {
			if(time < 11) {
				int rr = r.nextInt(3);
				switch(rr) {
				case 0:
					this.objects.add(new Asteroid((byte) (r.nextInt(3) + 1),
							r.nextInt(2 * mapwidth - 2000) + 128, r.nextInt(2 * mapheight - 2600)));
					break;
				case 1:
					this.meteor.add(new Meteoroid((byte) (r.nextInt(3) + 1),
							r.nextInt(2 * mapwidth - 2000) + 128, r.nextInt(2 * mapheight - 2600)));
					break;
				case 2:
					this.ufos.add(new Spaceship((byte) (r.nextInt(3) + 1),
							r.nextInt(2 * mapwidth - 2000) + 128, r.nextInt(2 * mapheight - 2600)));
					break;
				}
			} else if(time < 18) {
				if(r.nextBoolean()) {
					this.objects.add(new Asteroid((byte) (r.nextInt(3) + 1),
							r.nextInt(2 * mapwidth - 2000) + 128, r.nextInt(2 * mapheight - 2600)));
				} else {
					this.meteor.add(new Meteoroid((byte) (r.nextInt(3) + 1),
							r.nextInt(2 * mapwidth - 2000) + 128, r.nextInt(2 * mapheight - 2600)));
				}
			} else if(time < 26) {
				this.objects.add(new Asteroid((byte) (r.nextInt(3) + 1),
						r.nextInt(2 * mapwidth - 2000) + 128, r.nextInt(2 * mapheight - 2600)));				
			}
//			count++;
//			if(count >= 3) {
//				count = 0;
//				if(time > level_min) time--;
//			}
		}
		
		timeF = System.currentTimeMillis();
		
		if(timeF >= lastF + 10000) {
			lastF = System.currentTimeMillis();
			timeF = System.currentTimeMillis();
			if(time > level_min) time--;
		}
	}
	
	public void giveNick(String name) {
		this.nickname = name;
	}
	
	public CabineRender getCabine() {
		return this.cbRen;
	}
	
	public void shoot() {
		this.getCabine().shoot();
	}
	
	public void give(int fps, int ups) {
		this.getCabine().setPS(fps, ups);
	}
	
	public int getRocking() {
		return rocking;
	}
	
	public void moveShip(boolean xORy, boolean plus) {
		if(xORy) {
			xpos += spaceship_speed * ((plus) ? 1 : -1);
		} else {
			ypos += spaceship_speed * ((plus) ? 1 : -1);			
		}
	}
	
	public List<Asteroid> getAsteroids() {
		return this.objects;
	}
	
	public List<Spaceship> getSpaceships() {
		return this.ufos;
	}
	
	public List<Meteoroid> getMeteoroids() {
		return this.meteor;
	}
	
	public List<Bullet> getBullets() {
		return this.bullets;
	}
	
	public void addExplosion(Explosion e) {
		this.explsns.add(e);
	}
	
	public GraphicsMain getGM() {
		return gm;
	}
	
	public float getXPos() {
		return xpos;
	}
	
	public float getYPos() {
		return ypos;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public int getStrenght() {
		return strenght;
	}
	
	public void addStrenght(int strenght) {
		this.strenght += strenght;
	}
	
	public void takeDamage(int damage) {
		this.strenght -= damage;
		
		this.strike = System.currentTimeMillis();
		this.rocking += 5;
	}

}
