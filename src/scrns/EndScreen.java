package scrns;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cmpnts.Button;
import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.MouseManager;
import gfx.Assets;
import objs.Explosion;

public class EndScreen extends Screen {

	private GraphicsMain gm;
	private MouseManager mm;
	
	private float approx, speed = 4.0f;
	private boolean app = true;
	private byte currimg;
	
	private String name;
	private int points;
	
	private Button bhighscore;
	
	// START
		private boolean str = true, exps = true, bgexp = true;
		private long curr, tm;
		private List<Explosion> expls;
	
	public EndScreen(GraphicsMain gm) {
		this.gm = gm;
	}
	
	@Override
	public void init() {
		this.mm = gm.getHandler().getMouseManager();
		
		this.expls = new LinkedList<Explosion>();
		
		this.bhighscore = new Button(this.gm, 13, Main.WIDTH / 2 - 200, Main.HEIGHT - 200, 400, 40);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		int xscale = (int) (approx);
		int yscale = (int) (approx);
		g.drawImage(Assets.background_textures[currimg], 0 - (xscale / 2), 0 - (yscale / 2), 1920 + xscale, 1080 + yscale, null);
		
		g.setColor(Color.WHITE);
		g.setFont(Assets.px_128);
		
		String str = Main.lang_main[7] + "!";
		int spc = g.getFontMetrics().stringWidth(str);
		g.drawString(str, (Main.WIDTH / 2) - (spc / 2), 256);
		
		g.setFont(Assets.px_72);
		
		String str2 = this.name + ", " + Main.lang_main[28] + ":";
		int spc2 = g.getFontMetrics().stringWidth(str2);
		g.drawString(str2, (Main.WIDTH / 2) - (spc2 / 2), 400);
		
		String str3 = "" + points + " " + Main.lang_main[29] + "!";
		int spc3 = g.getFontMetrics().stringWidth(str3);
		g.drawString(str3, (Main.WIDTH / 2) - (spc3 / 2), 512);
		
		this.bhighscore.render(g);
		
		//-------------------------------------------------------------------------------------
		// MOUSE
		g.drawImage(Assets.mouse_texture, mm.getXMoved(), mm.getYMoved(), 32, 32, null);
		
		if(exps) {
			for(int e = 0; e < expls.size(); e++) {
				expls.get(e).render(g, 0, 0);
			}	
		}
	}

	@Override
	public void tick() {
		if(str) {
			tm = System.currentTimeMillis();
			
			// 1
			if(bgexp && tm > curr + 1) {
				intro();
				bgexp = false;
			}
			
			if(tm > curr + 100) {
				mm.clicked();
			}
			
			if(tm > curr + 1400) {
				exps = false;
			}
			
			if(tm > curr + 3000 & !exps) {
				str = false;
			}
		}
		
		if(exps) {
			for(int e = 0; e < expls.size(); e++) {
				expls.get(e).tick();
			}	
		}
		//-----------------------------------------------
		
		if(approx >= -128 && approx <= 1080) speed = 3.0f;
		if(approx >= -200 && approx <= -127) speed = 3.5f;
		if(approx >= -300 && approx <= -201) speed = 4.0f;
		if(approx >= -400 && approx <= -301) speed = 4.5f;
		if(approx >= -650 && approx <= -401) speed = 5.0f;
		if(approx >= -800 && approx <= -651) speed = 5.5f;
		if(approx >= -1080 && approx <= -901) speed = 6.0f;
		
		if(app) {
			approx += speed;
		} else approx -= speed;
		
		if(approx >= 1080) app = false;
		
		else if(approx <= -1080) {
			currimg++;
			if(currimg == 4) currimg = 0;
			
			app = true;
		}
		
		this.mm.overTime();
		
		this.bhighscore.tick();
		if(this.bhighscore.isClicked()) {
			this.mm.clicked();
			this.gm.setScreen("HIGHSCORE");
			this.gm.getHighScoreScreen().addNewRecord(this.name, this.points);
		}
	}

	public void give(String name, int points) {
		this.name = name;
		this.points = points;
		
		this.curr = System.currentTimeMillis();
		this.tm = System.currentTimeMillis();
	}
	
	private void intro() {
		Random r = new Random();
		
		for(int e = 0; e < 512; e++) {
			int x = r.nextInt(Main.WIDTH);
			int y = r.nextInt(Main.HEIGHT);
			int sdif = r.nextInt(256);
			this.expls.add(new Explosion(x - 240, y - 240, 128 + sdif));
		}
	}
	
	public void reset() {
		this.currimg = 0;
		this.approx = 0;
		this.app = true;
		this.str = true;
		this.exps = true;
		this.bgexp = true;
		this.tm = System.currentTimeMillis();
	}
	
}
