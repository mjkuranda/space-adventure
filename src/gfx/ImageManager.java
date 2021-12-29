package gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager {	
	
	private int width, height, size;
	
	private ImageLoader imgLoader;
	
	public ImageManager() {
		initGraphicsSize();
		loadImgs();
		initAssets();
	}
	
	private void loadImgs() {
	}
	
	public int getSize() {
		return size * 64; // 512
	}
	
	private void initGraphicsSize() {
		imgLoader = new ImageLoader(ImageLoader.GRAPHICS_TYPE_16_BIT);
		if(imgLoader.getType() == ImageLoader.GRAPHICS_TYPE_16_BIT) {
			width = 16;
			height = 16;
			size = 4;
		}
	}
	
	private BufferedImage loadSprite(BufferedImage img, int x, int y) {
		return img.getSubimage(x * width, y * height, width, height);
	}
	
	private BufferedImage loadSprite(BufferedImage img, int x, int y, int width, int height) {
		return img.getSubimage(x * width, y * height, width, height);
	}
	
//	private BufferedImage loadTexture(int x, int y) {
//		return texs.getSubimage(x * width, y * height, width, height);
//	}
	
	private void initAssets() {
		Assets.mouse_texture = imgLoader.loadImage("mouse");
		
		Assets.metal_texture = imgLoader.loadImage("metal_02");
		
		Assets.main_background = imgLoader.loadImage("bckgrnds/bcg0");
		Assets.menu_background = imgLoader.loadImage("bckgrnds/bcgmn");
		Assets.aster_tex = imgLoader.loadImage("aster");
		Assets.meteor_tex = imgLoader.loadImage("meteoroid");
		Assets.ufo_tex = imgLoader.loadImage("spcshp");
		
		Assets.background_textures = new BufferedImage[5];
		for(byte i = 0; i < 5; i++) {
			Assets.background_textures[i] = imgLoader.loadImage("bckgrnds/bcg" + i);
		}
		
		Assets.explosion_textures = new BufferedImage[25];
		BufferedImage fire = this.imgLoader.loadImage("fire");
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < 5; x++) {
				Assets.explosion_textures[(y * 5) + x] = loadSprite(fire, x, y, 64, 64);
			}
		}

		Assets.fire_tex = new ImageIcon(Assets.class.getClassLoader().getResource("imgs/fire.gif"));
		
	}

}
