package engine.core;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gfx.Assets;
import engine.input.Handler;

public class Display {

	private JFrame frame;
	private GraphicsMain gMain;
	private Handler handler;
	
	private Cursor mainCursor;
	
	public Display(String title, int width, int height, boolean isPanel) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		//frame.setIconImage(new ImageIcon("/Icon.png").getImage());
		if(isPanel) {
			handler = new Handler();
			frame.addKeyListener(handler.getKeyManager());
			frame.addMouseListener(handler.getMouseManager());
			frame.addMouseMotionListener(handler.getMouseManager());
			gMain = new GraphicsMain(frame, handler, width, height);
			//mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			//		Assets.mouse_texture, new Point(0, 0), "main cursor");
			//gMain.setCursor(mainCursor);
			mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
					new Point(0, 0), "main cursor");
			gMain.setCursor(mainCursor);
			frame.add(gMain);
		} else frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public Display(String title, int width, int height, boolean isPanel, boolean maximizeBoth) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(maximizeBoth) frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		if(isPanel) {
			handler = new Handler();
			frame.addKeyListener(handler.getKeyManager());
			frame.addMouseListener(handler.getMouseManager());
			frame.addMouseMotionListener(handler.getMouseManager());
			gMain = new GraphicsMain(frame, handler, width, height);
			mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					Assets.mouse_texture, new Point(0, 0), "main cursor");
			gMain.setCursor(mainCursor);
			frame.add(gMain);
		} else frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public Display(String title, int width, int height, boolean isPanel, boolean maximizeBoth, boolean resizable) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(maximizeBoth) frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(resizable);
		if(isPanel) {
			handler = new Handler();
			frame.addKeyListener(handler.getKeyManager());
			frame.addMouseListener(handler.getMouseManager());
			frame.addMouseMotionListener(handler.getMouseManager());
			gMain = new GraphicsMain(frame, handler, width, height);
			mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					Assets.mouse_texture, new Point(0, 0), "main cursor");
			gMain.setCursor(mainCursor);
			frame.add(gMain);
		} else frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public void addToFrame(Component component) {
		frame.add(component);
		component.setVisible(true);
	}
	
	public void addToPanel(Component component) {
		gMain.add(component);
		component.setVisible(true);
	}
	
	public JFrame getJFrame() {
		return frame;
	}
	
	public void setVisible(boolean visibility) {
		frame.setVisible(visibility);
	}

}
