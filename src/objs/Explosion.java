package objs;

import java.awt.Graphics;

import gfx.Animation;
import gfx.Assets;

public class Explosion {

	private Animation anim;
	private int x, y;
	private int size;
	
	public Explosion(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		
		this.anim = new Animation(Assets.explosion_textures, 50);
	}
	
	public void render(Graphics g, int xpos, int ypos) {
		g.drawImage(this.anim.getCurrentFrame(), this.x + xpos + (this.size / 2), this.y + ypos + (this.size / 2), this.size, this.size, null);
	}
	
	public void tick() {
		this.anim.tick();
	}
	
	public boolean isEndful() {
		return this.anim.isLastFrame();
	}

}
