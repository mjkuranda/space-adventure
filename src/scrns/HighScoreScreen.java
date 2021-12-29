package scrns;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cmpnts.Button;
import cmpnts.Reader;
import cmpnts.Writer;
import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.MouseManager;
import gfx.Assets;
import objs.Explosion;

public class HighScoreScreen extends Screen {

	private GraphicsMain gm;
	private MouseManager mm;
	private Reader read;
	private Writer write;
	
	private float approx;
	
	private List<Explosion> expls;
	private boolean exps;
	
	private String[] strings;
	
	private Button breset, bmenu;
	
	
	
	private byte idd = 0;
		
	
	
	
	public HighScoreScreen(GraphicsMain gm) {
		this.gm = gm;
	}

	@Override
	public void init() {
		this.mm = gm.getHandler().getMouseManager();
		
		this.expls = new LinkedList<Explosion>();
		
		this.breset = new Button(this.gm, 12, Main.WIDTH / 2 - 200, ((Main.HEIGHT == 768) ? 660 : 770), 400, 40);
		this.bmenu = new Button(this.gm, 11, Main.WIDTH / 2 - 200, ((Main.HEIGHT == 768) ? 710 : 820), 400, 40);
		
		this.read = new Reader();
		this.strings = this.read.readPlayers();
		
		this.write = new Writer();
	}
	
	@Override
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
		g.drawString("Space Adventure", Main.WIDTH / 2 - (spc / 2) + 20, 100);
		
		// table
		int xt = Main.WIDTH / 2 - 512;
		int yt = (Main.HEIGHT == 768) ? 128 : 192;
		g.setColor(new Color(200, 0, 0));
		g.drawRect(xt, yt, 1024, 511);
		g.drawRect(xt + 1, yt + 1, 1024, 511);
		
		g.setColor(new Color(100, 100, 100, 100));
		g.fillRect(xt + 2, yt + 2, 1022, 509);
		
		// 0. place
		g.setColor(new Color(50, 50, 50, 150));
		g.fillRect(xt + 4, yt + 4, 1017, 44);
		//---
		g.setColor(Color.BLACK);
		g.setFont(Assets.px_20);
		g.drawString(Main.lang_main[18], xt + 8, yt + 16);
		//---
		g.setColor(Color.WHITE);
		g.setFont(Assets.px_36);
		g.drawString(Main.lang_main[19], xt + 128 + 48, yt + 32);
		//---
		g.drawString(Main.lang_main[5], xt + 458, yt + 32);
		//---
		g.drawString(Main.lang_main[20], xt + 928, yt + 32);
		
		// 1. place
		g.setColor(new Color(200, 200, 0, 150));
		g.fillRect(xt + 4, yt + 4 + 46, 1017, 44);
		
		// 2. place
		g.setColor(new Color(200, 200, 200, 150));
		g.fillRect(xt + 4, yt + 4 + 46 + 44 * 1 + (2 * 1), 1017, 44);
		
		// 3. place
		g.setColor(new Color(150, 75, 0, 150));
		g.fillRect(xt + 4, yt + 4 + 46 + 44 * 2 + (2 * 2), 1017, 44);
	
		// 4. - 10. places
		for(int p = 3; p < 10; p++) {
			g.setColor(new Color(100, 100, 100, 150));
			g.fillRect(xt + 4, yt + 4 + 46 + 44 * p + (2 * p), 1017, 44);
		}
		
		// 1. - 10. places
		for(int p = 0; p < 10; p++) {
			g.setColor(Color.BLACK);
			g.setFont(Assets.px_20);
			g.drawString((p + 1) + ".", xt + 8, yt + 46 + 16 + (p * 46));
			
			g.setColor(Color.WHITE);
			g.setFont(Assets.px_36);
			g.drawString(this.strings[p * 3], xt + 48, yt + 46 + 32 + (p * 46));
			
			g.drawString(this.strings[p * 3 + 1], xt + 458, yt + 46 + 32 + (p * 46));
			
			g.drawString(this.strings[p * 3 + 2], xt + 898, yt + 46 + 32 + (p * 46));
		}
		
		this.breset.render(g);
		this.bmenu.render(g);
		
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
		if(exps) {
			for(int e = 0; e < expls.size(); e++) {
				expls.get(e).tick();
			}	
		}
		
		this.mm.overTime();
		
		this.breset.tick();
		this.bmenu.tick();
		
		if(this.breset.isClicked()) {
			this.write.resetHighScore();
			this.strings = this.read.readPlayers();
			this.mm.clicked();
		}
		
		if(this.bmenu.isClicked()) {
			this.gm.setScreen("MENU");
			this.mm.clicked();
		}

//		Random r = new Random();
//		if(this.mm.isClicked()) {
//			this.mm.clicked();
//			this.addNewRecord("Player" + this.idd, r.nextInt(1000));
//			this.idd++;
//		}
	}
	
	public void addNewRecord(String name, int points) {
		GregorianCalendar greg = new GregorianCalendar();
		int year = greg.get(Calendar.YEAR);
		int month = greg.get(Calendar.MONTH) + 1;
		int day = greg.get(Calendar.DAY_OF_MONTH);
		
		String date = ((day < 10) ? "0" : "") + day + "." + ((month < 10) ? "0" : "") + month + "." + year;
		
		int plc = -1; // place
		for(int p = 0; p < 10; p++) {
			System.out.println(this.strings[(p * 3)]);
			System.out.println(this.strings[(p * 3) + 1]);
			System.out.println(this.strings[(p * 3) + 2]);
			if(points > Integer.parseInt(this.strings[(p * 3) + 1])) {
				
				plc = new Integer(p);
				
				p = 10;
			}
		}
		
		if(plc > -1) {
			for(int c = 9; c > plc; c--) {
				this.strings[(c * 3)] = new String(this.strings[((c - 1) * 3)]);
				this.strings[(c * 3) + 1] = new String(this.strings[((c - 1) * 3 + 1)]);
				this.strings[(c * 3) + 2] = new String(this.strings[((c - 1) * 3 + 2)]);
			}
			
			this.strings[plc * 3] = "" + name;
			this.strings[plc * 3 + 1] = "" + points;
			this.strings[plc * 3 + 2] = "" + date;	
		}
		
		this.write.saveAll(this.strings);
		
	}

}
