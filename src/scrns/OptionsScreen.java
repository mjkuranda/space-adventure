package scrns;

import java.awt.Color;
import java.awt.Graphics;

import cmpnts.Button;
import cmpnts.Reader;
import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.MouseManager;
import gfx.Assets;

public class OptionsScreen extends Screen {

	private GraphicsMain gm;
	private MouseManager mm;
	
	private float approx;
	
	private Button bEn, bPl, bBack;
	
	public OptionsScreen(GraphicsMain gm) {
		this.gm = gm;
	}
	
	@Override
	public void init() {
		this.mm = gm.getHandler().getMouseManager();
		
		this.bEn = new Button(gm, 14, (Main.WIDTH / 2) - 200, 370, 400, 40);
		this.bPl = new Button(gm, 15, (Main.WIDTH / 2) - 200, 430, 400, 40);
		this.bBack = new Button(gm, 11, (Main.WIDTH / 2) - 200, 640, 400, 40);
	}

	@Override
	public void render(Graphics g) {
		int xscale = (int) (approx) * 0;
		int yscale = (int) (approx) * 0;
		
		int xi = ((1920 - Main.WIDTH) / 2);
		int yi = ((1080 - Main.HEIGHT) / 2);
		g.drawImage(Assets.menu_background, 0 - xi - (xscale / 2), 0 - yi - (yscale / 2), 1920 + xscale, 1080 + yscale, null);
	
		// title
		g.setColor(Color.WHITE);
		g.setFont(Assets.px_128);
		int spcc = g.getFontMetrics().stringWidth("Space Adventure");
		g.drawString("Space Adventure", Main.WIDTH / 2 - (spcc / 2) + 20, 200);
		
		//---------------------------------------------------------------------------------------
		g.setColor(Color.WHITE);
		g.setFont(Assets.px_36);
		int spc = g.getFontMetrics().stringWidth(Main.lang_main[17]);
		g.drawString(Main.lang_main[17], (Main.WIDTH / 2) - (spc / 2), 355);
		//-----------------------------------------------------------------------------------------
		
		this.bEn.render(g);
		this.bPl.render(g);
		this.bBack.render(g);
		//--------------------------------------------------------------------------------------
		
		//-------------------------------------------------------------------------------------
		// MOUSE
		g.drawImage(Assets.mouse_texture, mm.getXMoved(), mm.getYMoved(), 32, 32, null);
	}

	@Override
	public void tick() {
		this.bEn.tick();
		this.bPl.tick();
		this.bBack.tick();
		
		if(this.bEn.isClicked()) {
			Reader r = new Reader();
			Main.lang_main = r.prescribeLines(Main.lang_en);
		} else
		if(this.bPl.isClicked()) {
			Reader r = new Reader();
			Main.lang_main = r.prescribeLines(Main.lang_pl);
		} else
		if(this.bBack.isClicked()) {
			gm.setScreen("MENU");
		}
		mm.clicked();
	}


}
