package scrns;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cmpnts.LoadingBar;
import cmpnts.Reader;
import engine.core.GraphicsMain;
import engine.core.Main;
import ffx.FontLoader;
import gfx.Assets;
import gfx.ImageLoader;

public class LoadingScreen extends Screen {

	private GraphicsMain gm;
	private Reader read;
	
	private LoadingBar bar;
	private String[] strs = new String[] {
			"Loading Files", "Loading Classes", "Loading Images", "Loading Sounds", "Finished!"
	};
	private byte curs = 0;
	
	private boolean load, pre, img, sou, fil;
	
	private BufferedImage bcg;
	
	public LoadingScreen(GraphicsMain gm) {
		this.gm = gm;
		this.bar = new LoadingBar(0, 64, Main.WIDTH / 2 - ((64 * 16 + 64 + 2) / 2), Main.HEIGHT - 320, 32);
		
		init();
	}
	
	@Override
	public void init() {
		try {
			this.bcg = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream("/imgs/bckgrnds/bcgth.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		int xi = ((1920 - Main.WIDTH) / 2);
		int yi = ((1080 - Main.HEIGHT) / 2);
		g.drawImage(this.bcg, 0 - xi, 0 - yi, 1920, 1080, null);
		
		
		this.bar.render(g);
		
		// rendering the text over bar
		g.setColor(new Color(200, 0, 0));
		g.setFont(new Font("Consolas", Font.BOLD, 18));
		int pox = g.getFontMetrics().stringWidth(strs[curs]);
		g.drawString(strs[curs], Main.WIDTH / 2 - (pox / 2), Main.HEIGHT - 335);
	}

	@Override
	public void tick() {
		
		this.bar.tick();
		
		// pre-loading
		// loading images
		// loading sounds
		// loading files
		
		if(pre && img && sou && fil) {
			load = true;
			gm.setScreen("MENU");
//			gm.setScreen("HIGHSCORE");
			gm.getMenuScreen().run();
		}
		if(pre && img && sou && !fil) {
			initializingClasses();
			fil = true;
			this.bar.add(16);
			curs = 4;
		}
		if(pre && img && !sou) {
			loadingSounds();
			sou = true;
			this.bar.add(16);
			curs = 3;
		}
		if(pre && !img) {
			loadingImages();
			img = true;
			this.bar.add(16);
			curs = 2;
		}
		if(!pre) {
			loadingFiles();
			pre = true;
			this.bar.add(16);
			curs = 1;
		}
		
	}
	
	private void initializingClasses() {
		for(byte s = 0; s < this.gm.getScreens().size(); s++) {
			this.gm.getScreen(s).init();
		}
	}

	private void loadingImages() {
		this.gm.loadImages();
		FontLoader.init();
	}
	
	private void loadingSounds() {
		
	}

	private void loadingFiles() {
		this.read = new Reader();
		Main.lang_en = this.read.readLanguage("en", Main.lang_en);
		Main.lang_pl = this.read.readLanguage("pl", Main.lang_pl);
		Main.lang_main = this.read.prescribeLines(Main.lang_en);
	}


}
