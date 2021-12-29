package cmpnts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.PointerInfo;
import java.util.LinkedList;
import java.util.List;

import engine.core.GraphicsMain;
import engine.core.Main;
import gfx.Assets;
import objs.Asteroid;
import objs.Meteoroid;
import objs.Spaceship;
import scrns.GameScreen;
import scrns.Screen;

public class CabineRender {

	private GraphicsMain gm;
	private GameScreen gameScreen;
	private int xsrn, ysrn;
	
	private int mapX, mapY;
	
	private int xpos, ypos;
	private List<Asteroid> objs;
	private List<Meteoroid> metr;
	private List<Spaceship> spsh;
	
	private Counter cp/*, cs*/;
	private LoadingBar bCn, bStr;
	
	private long curC, maxC;

	private int fps, ups, lmx, lmy;
	
	public CabineRender(GraphicsMain gm, GameScreen gameScreen, int xpos, int ypos) {
		this.gm = gm;
		this.gameScreen = gameScreen;
		
		this.xpos = xpos;
		this.ypos = ypos;
		
		this.mapX = (Main.WIDTH / 2) - 29;
		this.mapY = Main.HEIGHT - 106;
	
		this.xsrn = Main.WIDTH;
		this.ysrn = Main.HEIGHT;
		
		this.objs = new LinkedList<Asteroid>();
		this.metr = new LinkedList<Meteoroid>();
		this.spsh = new LinkedList<Spaceship>();
		
		this.cp = new Counter((Main.WIDTH / 2) - 400, Main.HEIGHT - 57, this.gameScreen.getPoints(), (byte) 8); this.cp.update();
		//this.cs = new Counter((Main.WIDTH / 2) - 180, Main.HEIGHT - 57, this.gameScreen.getStrenght(), (byte) 3); this.cs.update();
		
		this.bCn = new LoadingBar(0, 10, Main.WIDTH / 2 + 100, Main.HEIGHT - 57, 20);
		this.bStr = new LoadingBar(10, 10, (Main.WIDTH / 2) - 230, Main.HEIGHT - 57, 20);
	}
	
	public void render(Graphics g) {
		// cannon
		g.setColor(new Color(150, 150, 150));
		g.fillOval(Main.WIDTH / 2 - 32, Main.HEIGHT - 128 - 32, 64, 64);
		
		// background
		g.drawImage(Assets.metal_texture, 0, Main.HEIGHT - 128, Main.WIDTH, 128, null);
	
		//----------------------------------------------------------------------------------
		// map title
		g.setColor(Color.GREEN);
		g.setFont(Assets.px_20);
		int spc = g.getFontMetrics().stringWidth(Main.lang_main[10]);
		g.drawString(Main.lang_main[10], (Main.WIDTH / 2) - (spc / 2), Main.HEIGHT - 80);
		
		// map background
		g.setColor(new Color(20, 20, 0));
		g.fillRect(this.mapX, this.mapY + 32, 58, 58);
		
		// map
		g.setColor(Color.GRAY);
		g.draw3DRect(this.mapX, this.mapY + 32, 58, 58, false);
		
		// loading a cannon
		this.bCn.render(g);
		
		// cannon title
		g.setColor(Color.GREEN);
		g.setFont(Assets.font28);
		int spc2 = g.getFontMetrics().stringWidth(Main.lang_main[21]);
		g.drawString(Main.lang_main[21], (Main.WIDTH / 2) + 100 + (this.bCn.getWidth() / 2) - (spc2 / 2), Main.HEIGHT - 72 + 7);
		
		// cannon loading bar text
		g.setFont(Assets.px_20);
		int spc3 = g.getFontMetrics().stringWidth(Main.lang_main[22]);
		g.drawString(Main.lang_main[22], (Main.WIDTH / 2) + 100 + (this.bCn.getWidth() / 2) - (spc3 / 2), Main.HEIGHT - 24);
		
		// fps & ups
		g.setFont(Assets.px_20);
		g.setColor(Color.GRAY);
		g.fillRect((Main.WIDTH / 2) - 550, Main.HEIGHT - 80, 80, 24);
		g.setColor(Color.BLACK);
		g.drawRect((Main.WIDTH / 2) - 550, Main.HEIGHT - 80, 80, 24);
		g.setColor(Color.GREEN);
		g.drawString("FPS: " + ((fps < 1000) ? "0" : "") +
								((fps < 100) ? "0" : "") +
								((fps < 10) ? "0" : "") + fps, (Main.WIDTH / 2) - 542, Main.HEIGHT - 63);
		g.setColor(Color.GRAY);
		g.fillRect((Main.WIDTH / 2) - 550, Main.HEIGHT - 46, 80, 24);
		g.setColor(Color.BLACK);
		g.drawRect((Main.WIDTH / 2) - 550, Main.HEIGHT - 46, 80, 24);
		g.setColor(Color.GREEN);
		g.drawString("UPS: " + ((ups < 1000) ? "0" : "") +
								((ups < 100) ? "0" : "") + 
								((ups < 10) ? "0" : "") + ups, (Main.WIDTH / 2) - 542, Main.HEIGHT - 29);
		
		// rocking the spaceship
		int rx = (Main.WIDTH / 2) + 340;
		int ry = Main.HEIGHT - 57;
		g.setColor(Color.GRAY);
		g.fillRect(rx, ry, 200, 20);
		g.setFont(Assets.px_20);
		g.drawString("0", rx + 97, ry + 32);
		g.setColor(Color.BLACK);
		g.drawRect(rx, ry, 200, 20);
		g.setColor(Color.GREEN);
		
		// text of the indicators
		int spc4 = g.getFontMetrics().stringWidth(Main.lang_main[23]);
		g.drawString(Main.lang_main[23], rx + 100 - (spc4 / 2), Main.HEIGHT - 72 + 7);
		
		// others indicators
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(rx + 99, ry + 2, rx + 99, ry + 18);
		g.drawLine(rx + 100, ry + 2, rx + 100, ry + 18);
		g.drawLine(rx + 101, ry + 2, rx + 101, ry + 18);
		//----
		g.drawLine(rx + 49, ry + 2, rx + 49, ry + 18);
		g.drawLine(rx + 50, ry + 2, rx + 50, ry + 18);
		g.drawLine(rx + 51, ry + 2, rx + 51, ry + 18);
		
		//----
		g.drawLine(rx + 149, ry + 2, rx + 149, ry + 18);
		g.drawLine(rx + 150, ry + 2, rx + 150, ry + 18);
		g.drawLine(rx + 151, ry + 2, rx + 151, ry + 18);
		
		// x indicator
		g.setColor(new Color(200, 200, 0));
		g.drawLine(rx + 100 + this.lmx, ry + 2, rx + 100 + this.lmx, ry + 18);
		
		// y indicator
		g.setColor(new Color(0, 200, 200));
		g.drawLine(rx + 100 + this.lmy, ry + 2, rx + 100 + this.lmy, ry + 18);
		
		// indicator
		g.setColor(Color.RED);
		g.drawLine(rx + 100 + (this.gameScreen.getRocking() * 2), ry + 2, rx + 100 + (this.gameScreen.getRocking() * 2), ry + 18);
		
		// asteroids
		g.setColor(Color.ORANGE);
		for(int o = 0; o < objs.size(); o++) {
			Asteroid obj = objs.get(o);
			int xo = obj.getX() / 100;
			int yo = obj.getY() / 100;
			int so = obj.getScale() * 1;
			g.fillRect(this.mapX + 9 + xo, this.mapY + 42 + yo, so, so);
		}
		// meteors
		g.setColor(Color.GRAY);
		for(int o = 0; o < metr.size(); o++) {
			Meteoroid obj = metr.get(o);
			int xo = obj.getX() / 100;
			int yo = obj.getY() / 100;
			int so = obj.getScale() * 1;
			g.fillRect(this.mapX + 9 + xo, this.mapY + 42 + yo, so, so);
		}
		// spaceships
		g.setColor(Color.WHITE);
		for(int o = 0; o < spsh.size(); o++) {
			Spaceship obj = spsh.get(o);
			int xo = obj.getX() / 100;
			int yo = obj.getY() / 100;
			int so = obj.getScale() * 1;
			g.fillRect(this.mapX + 9 + xo, this.mapY + 42 + yo, so, so);
		}
		
		// player on map
		int wp = (Main.WIDTH / 100) * 2;
		int hp = (Main.HEIGHT / 100) * 2;
		g.setColor(new Color(180));
		g.drawRect(this.mapX + 1 - (xpos / 100), this.mapY + 33 - (ypos / 100), wp, hp);
		//----------------------------------------------------------------------------------
		
		// points's bar
		this.bStr.render(g);
		g.setFont(Assets.font28);
		g.setColor(Color.GREEN);
		this.cp.render(g);
		//this.cs.render(g);
		int spt = g.getFontMetrics().stringWidth(Main.lang_main[5]);
		int sst = g.getFontMetrics().stringWidth(Main.lang_main[6]);
		g.drawString(Main.lang_main[5], (Main.WIDTH / 2) - 400 + (this.cp.getWidth() / 2) - (spt / 2), Main.HEIGHT - 72 + 7);
		g.drawString(Main.lang_main[6], (Main.WIDTH / 2) - 230 + (this.bStr.getWidth() / 2) - (sst / 2), Main.HEIGHT - 72 + 7);
	}
	
	public void tick(List<Asteroid> objs, List<Meteoroid> metr, List<Spaceship> spsh, int strength, int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.objs = objs;
		this.metr = metr;
		this.spsh = spsh;
		
		this.cp.tick(this.gameScreen.getPoints());
		//this.cs.tick(this.gameScreen.getStrenght());
		this.bStr.setValue((strength / 50) + 1);
		this.bStr.tick();
		
		this.curC = System.currentTimeMillis();
		this.maxC = System.currentTimeMillis() + 100;
		if(this.curC + 100 >= this.maxC) {
			this.curC = System.currentTimeMillis();
			this.maxC = System.currentTimeMillis() + 100;
			if(!this.bCn.isFill()) this.bCn.add(1);
		}
	}
	
	public boolean isReadyCannon() {
		return this.bCn.isFill();
	}
	
	public void shoot() {
		this.bCn.add(-10);
	}
	
	public void setPS(int fps, int ups) {
		this.fps = fps;
		this.ups = ups;
	}
	
	public void giveMovement(int lmx, int lmy) {
		this.lmx = lmx;
		this.lmy = lmy;
	}

}
