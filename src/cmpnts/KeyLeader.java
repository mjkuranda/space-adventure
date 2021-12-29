package cmpnts;

import java.awt.event.KeyEvent;

import engine.core.GraphicsMain;
import engine.core.Main;
import engine.input.KeyManager;
import engine.input.MouseManager;
import objs.Bullet;
import scrns.GameScreen;

public class KeyLeader {

	private GraphicsMain gm;
	private GameScreen gms;
	
	public KeyLeader(GraphicsMain gm, GameScreen gms) {
		this.gm = gm;
		this.gms = gms;
	}
	
	public void tick() {
		KeyManager k = gm.getHandler().getKeyManager();
		
		if(k.getKey(KeyEvent.VK_S)) {
//			ypos -= spaceship_speed;
			gms.moveShip(false, false);
		}
		if(k.getKey(KeyEvent.VK_W)) {
//			ypos += spaceship_speed;
			gms.moveShip(false, true);
		}
		if(k.getKey(KeyEvent.VK_D)) {
//			xpos -= spaceship_speed;
			gms.moveShip(true, false);
		}
		if(k.getKey(KeyEvent.VK_A)) {
//			xpos += spaceship_speed;
			gms.moveShip(true, true);
		}
		
		if(k.getKey(KeyEvent.VK_SPACE)) {
			k.dropKey(KeyEvent.VK_SPACE);
			if(this.gms.getCabine().isReadyCannon()) {
				MouseManager mm = gms.getGM().getHandler().getMouseManager();
				gms.shoot();
				gms.getBullets().add(new Bullet(this.gms, mm.getXMoved(), mm.getYMoved(), (int) gms.getXPos(), (int) gms.getYPos()));	
			}
		}
		
		if(k.getKey(KeyEvent.VK_UP)) {
			System.out.println("up");
		}
		
		if(k.getKey(KeyEvent.VK_L)) {
			Main.LINES = !Main.LINES;
			k.dropKey(KeyEvent.VK_L);
		}
	}

}
