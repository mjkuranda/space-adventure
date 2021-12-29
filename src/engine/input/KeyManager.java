package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	
	private int lastKey;
	
	public KeyManager() {
		keys = new boolean[2048];
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		this.lastKey = keyCode;
		keys[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		keys[keyCode] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
	public boolean getKey(int keyCode) {
		return keys[keyCode];
	}
	
	public void dropKey(int keyCode) {
		this.keys[keyCode] = false;
	}
	
	public int getLastKey() {
		return lastKey;
	}
	
	public String getLastCharKey() {
		return KeyEvent.getKeyText(lastKey);
	}
	
	public void resetLastKey() {
		this.lastKey = 0;
	}

}