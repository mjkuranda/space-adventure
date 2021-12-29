package scrns;

import java.awt.Color;
import java.awt.Graphics;

import cmpnts.Button;
import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.KeyManager;
import engine.input.MouseManager;
import gfx.Assets;

public class PreGameScreen extends Screen {

	private GraphicsMain gm;
	private MouseManager mm;
	private Button bBack, bGo;
	
	private float approx;
	
	private String name = "";
	private boolean bigLetter = false, shift = false;
	private int space;
//	private byte pos;
	
	public PreGameScreen(GraphicsMain gm) {
		this.gm = gm;
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
			g.drawString("Space Adventure", Main.WIDTH / 2 - (spc / 2) + 20, 200);
			
		//---------------------------------------
			g.setFont(Assets.px_36);
			
		// field inputed
			int fx = (Main.WIDTH / 2) - 165;
			int fy = 300;
			g.setColor(Color.GRAY);
			g.fillRect(fx, fy, 330, 32);
			g.setColor(Color.BLACK);
			g.draw3DRect(fx, fy, 330, 32, true);
			
		// descriptions
			g.setColor(Color.BLACK);
			g.setFont(Assets.px_48);
			int spd = g.getFontMetrics().stringWidth(Main.lang_main[24]);
			g.drawString(Main.lang_main[24], (Main.WIDTH / 2) - (spd / 2), 294);
			
			Color color = (this.bigLetter) ? Color.GREEN : Color.RED;
			g.setColor(color);
			g.setFont(Assets.px_28);
			byte line = (this.bigLetter) ? (byte) 26 : (byte) 25;
			g.drawString(Main.lang_main[line], fx, fy + 50);
			
		// name inputed
			g.setColor(Color.WHITE);
			g.drawString(name, fx + 4, fy + 24);
			
			this.space = g.getFontMetrics().stringWidth(name);
			
			if(this.space < 380) {
				g.setColor(Color.WHITE);
				g.drawLine(fx + 4 + this.space, fy + 2, fx + 4 + this.space, fy + 22);
			}
			
		this.bBack.render(g);
		this.bGo.render(g);
		
		// MOUSE
		g.drawImage(Assets.mouse_texture, mm.getXMoved(), mm.getYMoved(), 32, 32, null);
	}

	@Override
	public void tick() {
		KeyManager key = this.gm.getHandler().getKeyManager();
		
//		if(!key.getLastCharKey().equals("Unknown keyCode: 0x0")) {
//			if(key.getKey(KeyEvent.VK_SPACE)) {
//				this.name += "_";
//				return;
//			} else
//			if(key.getKey(KeyEvent.VK_CAPS_LOCK) || key.getKey(KeyEvent.VK_ALT) ||
//				key.getKey(KeyEvent.VK_CONTROL) || key.getKey(KeyEvent.VK_ENTER) ||
//				key.getKey(KeyEvent.VK_TAB) || key.getKey(KeyEvent.VK_ESCAPE) ||
//				key.getKey(KeyEvent.VK_QUOTE) || key.getLastCharKey().equals("Back Quote")) {
//				return;
//			}
//			this.name += key.getLastCharKey();
//			key.resetLastKey();
//			return;
//		}
		
		//String text = key.getLastCharKey();
		
//		if(key.getLastKey() > 20 && key.getLastKey() <= 125 && key.getLastKey() != 44 && key.getLastKey() != 46 && key.getLastKey() != 47 &&
//				key.getLastKey() != 58 && key.getLastKey() != 59 && key.getLastKey() != 91 && key.getLastKey() != 92 && key.getLastKey() != 93 &&
//				key.getLastKey() != 45 && key.getLastKey() != 61 && 
//				!key.getLastCharKey().equals("NumPad ,") &&
//				!key.getKey(KeyEvent.VK_F1) && !key.getKey(KeyEvent.VK_F2) && !key.getKey(KeyEvent.VK_F3) && !key.getKey(KeyEvent.VK_F4) &&
//				!key.getKey(KeyEvent.VK_F5) && !key.getKey(KeyEvent.VK_F6) && !key.getKey(KeyEvent.VK_F7) && !key.getKey(KeyEvent.VK_F8) &&
//				!key.getKey(KeyEvent.VK_F9) && !key.getKey(KeyEvent.VK_F10) && !key.getKey(KeyEvent.VK_F11) && !key.getKey(KeyEvent.VK_F12)) {
//			if(key.getKey(KeyEvent.VK_NUMPAD0)) this.name += "0";
//			else if(key.getKey(KeyEvent.VK_NUMPAD0)) this.name += "0";
//			else if(key.getKey(KeyEvent.VK_NUMPAD1)) this.name += "1";
//			else if(key.getKey(KeyEvent.VK_NUMPAD2)) this.name += "2";
//			else if(key.getKey(KeyEvent.VK_NUMPAD3)) this.name += "3";
//			else if(key.getKey(KeyEvent.VK_NUMPAD4)) this.name += "4";
//			else if(key.getKey(KeyEvent.VK_NUMPAD5)) this.name += "5";
//			else if(key.getKey(KeyEvent.VK_NUMPAD6)) this.name += "6";
//			else if(key.getKey(KeyEvent.VK_NUMPAD7)) this.name += "7";
//			else if(key.getKey(KeyEvent.VK_NUMPAD8)) this.name += "8";
//			else if(key.getKey(KeyEvent.VK_NUMPAD9)) this.name += "9";
//			else {
//				this.name += key.getLastCharKey();	
//			}
//			key.resetLastKey();
//		}
		if(this.name.length() < 27 && this.space < 380 && key.getLastCharKey().length() == 1) {
			String s = key.getLastCharKey();
			boolean big = ((this.bigLetter && !this.shift) || (!this.bigLetter && this.shift));
			this.name += (big) ? s.toUpperCase() : s.toLowerCase();
		} else if(key.getLastCharKey().equals("Caps Lock")) {
			this.bigLetter = !this.bigLetter;
		} else if(key.getLastCharKey().equals("Backspace") && this.name.length() > 0) {
			StringBuilder sb = new StringBuilder(this.name);
			sb.deleteCharAt(this.name.length() - 1);
			this.name = sb.toString();
		} else if(this.name.length() < 27 && this.space < 380 && key.getLastCharKey().equals("Space")) {
			this.name += "_";
		}
//		else if(key.getKey(18)) {
//			this.pos--;
//			System.out.println("LLL");
//			if(this.pos < 0) this.pos = 0;
//		} else if(key.getKey(37)) {
//			this.pos++;
//			System.out.println("PPP");
//		}
		if(key.getKey(16)) {
			this.shift = true;
		} else {
			this.shift = false;
		}
		key.resetLastKey();
		
		//------------------------------
		this.bBack.tick();
		this.bGo.tick();
		
		if(this.bBack.isClicked()) {
			this.name = "";
			this.gm.setScreen("MENU");
		} else
		if(this.bGo.isClicked()) {
			if(this.name.length() == 0) this.name = "Captain_Nemo";
			this.gm.getGameScreen().giveNick(this.name);
			this.name = "";
			this.gm.setScreen("GAME");
		}
		this.mm.clicked();
	}

	@Override
	public void init() {
		this.mm = gm.getHandler().getMouseManager();
		
		this.bBack = new Button(this.gm, 11, (Main.WIDTH / 2) - 200, 490, 400, 40);
		this.bGo = new Button(this.gm, 27, (Main.WIDTH / 2) - 200, 440, 400, 40);
	}

}
