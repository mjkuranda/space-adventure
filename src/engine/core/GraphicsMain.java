package engine.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gfx.ImageManager;
import scrns.EndScreen;
import scrns.GameScreen;
import scrns.HighScoreScreen;
import scrns.LoadingScreen;
import scrns.MenuScreen;
import scrns.OptionsScreen;
import scrns.PreGameScreen;
import scrns.Screen;
import engine.input.Handler;
import engine.input.MouseManager;
import ffx.FontLoader;

public class GraphicsMain extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;
	
	private boolean running;
	
	private ImageManager imgManager;
	private List<Screen> screens;
	private Handler handler;
	
	private Dimension world_dimension;
	private byte current_screen = 3;
	
	public byte id_game_screen = 1;
	
//	double target = 60.0;
//    double nsPerTick = 1000000000.0 / target;
//    long lastTime = System.nanoTime();
//    long timer = System.currentTimeMillis();
//    double unprocessed = 0.0;
//    public static int fps = 0;
//    public static int tps = 0;
//    boolean canRender = false;
	
	long lastTime = System.nanoTime();
	final double amountOfTicks = 60.0;
	double ns = 1000000000 / amountOfTicks;
	double delta = 0;
	
	int updates = 0;
	int frames = 0;
	long timer = System.currentTimeMillis();

	int fps, ups;
	
	private Rectangle coll;
	
	public GraphicsMain(JFrame frame, Handler handler, int width, int height) {
		this.handler = handler;
		
		this.width = width;
		this.height = height;
		
		this.world_dimension = new Dimension(128, 128);
		
		this.running = true;
		 
		this.screens = new ArrayList<Screen>();
		this.screens.add(new MenuScreen(this));
		this.screens.add(new GameScreen(this));
		this.screens.add(new EndScreen(this));
		this.screens.add(new LoadingScreen(this));
		this.screens.add(new OptionsScreen(this));
		this.screens.add(new HighScoreScreen(this));
		this.screens.add(new PreGameScreen(this));
		
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render(g);
			frames++;
			
			if(current_screen == id_game_screen) {
				this.getGameScreen().give(fps, ups);
//				g.setColor(Color.WHITE);
//				g.setFont(new Font("Consolas", Font.PLAIN, 18));
//				g.drawString("FPS: " + fps, 500, 20);
//				g.drawString("UPS: " + ups, 500, 40);	
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				
				ups = updates;
				fps = frames;
				
				System.out.println("Frames: " + frames + ", Ticks: " + updates);
				updates = 0;
				frames = 0;
			}
			
			repaint();
	//}
		
		//System.exit(0);
	}
	
	private void tick() {
		MouseManager mm = this.handler.getMouseManager();
		this.coll = new Rectangle(mm.getXMoved(), mm.getYMoved(), 2, 2);
		
		screens.get(current_screen).tick();
	}
	
	private void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);
		
		screens.get(current_screen).render(g);
		
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.setColor(Color.RED);
		//g2d.fill(this.coll);
	}
	
	public ImageManager getImageManager() {
		return imgManager;
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public Dimension getWorldDimension() {
		return world_dimension;
	}
	
	public int getMaxTilesX() {
		return width / 32;
	}
	
	public int getMaxTilesY() {
		return height / 32;
	}
	
//	private void calculateFPS() {
//		while (running) {
//            long now = System.nanoTime();
//            unprocessed += (now - lastTime) / nsPerTick;
//            lastTime = now;
//
//            if (unprocessed >= 1.0) {
//                tick();
//                unprocessed--;
//                tps++;
//                canRender = true;
//            } else canRender = false;
//
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (System.currentTimeMillis() - 1000 > timer) {
//                timer += 1000;
//                System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
//                fps = 0;
//                tps = 0;
//            }
//        }
//	}
	
	public void loadImages() {
		this.imgManager = new ImageManager();
	}
	
	public void nextScreen() {
		 this.current_screen++;
	}

	public void setScreen(String screen) {
		if(screen == "GAME") current_screen = id_game_screen;
		if(screen == "END") current_screen = 2;
		if(screen == "MENU") current_screen = 0;
		if(screen == "OPTIONS") current_screen = 4;
		if(screen == "HIGHSCORE") current_screen = 5;
		if(screen == "PREGAME") current_screen = 6;
	}
	
	public Screen getScreen(byte id) {
		return this.screens.get(id);
	}
	
	public List<Screen> getScreens() {
		return this.screens;
	}
	
	public GameScreen getGameScreen() {
		return (GameScreen) screens.get(id_game_screen);
	}
	
	public EndScreen getEndScreen() {
		return (EndScreen) screens.get(2);
	}
	
	public MenuScreen getMenuScreen() {
		return (MenuScreen) screens.get(0);
	}
	
	public HighScoreScreen getHighScoreScreen() {
		return (HighScoreScreen) screens.get(5);
	}
	
	public void giveData(String name, int points) {
		getEndScreen().give(name, points);
	}
	
	public Rectangle getMouseColl() {
		return coll;
	}

}
