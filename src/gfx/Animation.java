package gfx;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private byte currentFrame;
	private long lastTime;
	private int time;

	public Animation(BufferedImage[] frames, int time) {
		this.frames = frames;
		this.lastTime = System.currentTimeMillis();
		this.time = time;
	}
	
	public Animation(BufferedImage frame) {
		this.frames = new BufferedImage[1];
		this.frames[0] = frame;
	}
	
	public void tick() {
		if(frames.length > 1 && System.currentTimeMillis() >= lastTime + time) {
			this.lastTime = System.currentTimeMillis();
			currentFrame++;
			if(currentFrame >= frames.length) currentFrame = 0;
		}
	}
	
	public BufferedImage getCurrentFrame() {
		return frames[currentFrame];
	}
	
	public int getCurrentFrameID() {
		return currentFrame;
	}
	
	public boolean isLastFrame() {
		return (this.getCurrentFrameID() == frames.length - 1);
	}

}
