package scrns;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cmpnts.Button;
import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.MouseManager;
import gfx.Assets;
import objs.Explosion;

public class MenuScreen extends Screen {

	private GraphicsMain gm;
	
	private MouseManager mm;
	
	private List<Button> butts;
	
	private float approx;
	
	// START
	private boolean str = true, exps = true, bgexp = true;
	private long curr, tm;
	private List<Explosion> expls;
	
	public MenuScreen(GraphicsMain gm) {
		this.gm = gm;
	}
	
	@Override
	public void init() {
		this.mm = gm.getHandler().getMouseManager();
		
		this.expls = new LinkedList<Explosion>();
		
		this.butts = new ArrayList<Button>();
		this.butts.add(new Button(gm, 1, Main.WIDTH / 2 - 200, 320, 400, 40));
		this.butts.add(new Button(gm, 2, Main.WIDTH / 2 - 200, 380, 400, 40));
		this.butts.add(new Button(gm, 3, Main.WIDTH / 2 - 200, 440, 400, 40));
		this.butts.add(new Button(gm, 4, Main.WIDTH / 2 - 200, 600, 400, 40));
	}
	
	public void run() {
		this.curr = System.currentTimeMillis();
		this.tm = System.currentTimeMillis();
	}
	
	public void render(Graphics g) {
		int xscale = (int) (approx) * 0;
		int yscale = (int) (approx) * 0;
		
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		int xi = ((1920 - Main.WIDTH) / 2);
		int yi = ((1080 - Main.HEIGHT) / 2);
		g.drawImage(Assets.menu_background, 0 - xi - (xscale / 2), 0 - yi - (yscale / 2), 1920 + xscale, 1080 + yscale, null);
		
		// title
		g.setColor(Color.WHITE);
		g.setFont(Assets.px_128);
		int spc = g.getFontMetrics().stringWidth("Space Adventure");
		g.drawString("Space Adventure", Main.WIDTH / 2 - (spc / 2) + 20, 200);
		
		for(int b = 0; b < butts.size(); b++) {
			butts.get(b).render(g);
		}
		
		// date and version
		g.setColor(Color.WHITE);
		g.setFont(Assets.px_28);
		g.drawString("v. f. 23.01.2019 AD", 5, Main.HEIGHT - 5);
		
		//--------------------------------------------------------------------------------------
		g.setColor(Color.WHITE);
		g.setFont(Assets.px_28);
		int poxs = g.getFontMetrics().stringWidth("Technikum Informatyczne - II t");
		g.drawString("Technikum Informatyczne - II t", Main.WIDTH - poxs - 5, Main.HEIGHT - 5);
		int posx = g.getFontMetrics().stringWidth(Main.lang_main[16] + ": " + "Marek 'Maycrawer' Kuranda");
		g.drawString(Main.lang_main[16] + ": " + "Marek 'Maycrawer' Kuranda", Main.WIDTH - posx - 5, Main.HEIGHT - 25);
		//--------------------------------------------------------------------------------------
		
		//-------------------------------------------------------------------------------------
		// MOUSE
		g.drawImage(Assets.mouse_texture, mm.getXMoved(), mm.getYMoved(), 32, 32, null);
		
		if(exps) {
			for(int e = 0; e < expls.size(); e++) {
				expls.get(e).render(g, 0, 0);
			}	
		}
	}
	
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
		
		this.mm.overTime();
		//-----------------------------------------------
		
//		this.approx += 0.001f;
		
		for(int b = 0; b < butts.size(); b++) {
			butts.get(b).tick();
			
			if(butts.get(b).isClicked()) {
				mm.clicked();
				
				switch(b) {
				case 0:
					gm.setScreen("PREGAME");
					gm.getGameScreen().init();
					break;
				case 1:
					gm.setScreen("OPTIONS");
					break;
				case 2:
					gm.setScreen("HIGHSCORE");
					break;
				case 3:
					System.exit(0);
					break;
				}
			}
		}
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



}
