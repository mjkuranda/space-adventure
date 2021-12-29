package engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseManager implements MouseListener, MouseWheelListener, MouseMotionListener {

	private int xClicked, yClicked;
	private int xMoved, yMoved;
	private boolean clicked;
	private byte buttonID = -1;
	private boolean dragged;
	private int wheelValue;
	
	private byte correctedX = (-9 + 30) * 0;
	private byte correctedY = (-28) * 0;
	
	private long lastClicked, timeCurr;
	
	public MouseManager() {
	}
	
	public void overTime() {
		this.timeCurr = System.currentTimeMillis();
		if(timeCurr > lastClicked + 50) {
			this.clicked = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		xClicked = arg0.getX() + correctedX;
		yClicked = arg0.getY() + correctedY;
		clicked = true;
		buttonID = (byte) arg0.getButton();
		this.lastClicked = System.currentTimeMillis();
		this.timeCurr = System.currentTimeMillis();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		xClicked = arg0.getX() + correctedX;
		yClicked = arg0.getY() + correctedY;
		clicked = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		clicked = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		xMoved = e.getX() + correctedX;
		yMoved = e.getY() + correctedY;
		dragged = true;
		clicked = true;
		this.lastClicked = System.currentTimeMillis();
		this.timeCurr = System.currentTimeMillis();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		xMoved = e.getX() - 9;
//		yMoved = e.getY() - 26;
		xMoved = e.getX() + correctedX;
		yMoved = e.getY() + correctedY;
	}
	
	public byte getButtonID() {
		return buttonID;
	}
	
	public void clicked() {
		this.clicked = false;
		this.buttonID = -1;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public boolean isClicked(byte id) {
		if(id == buttonID)
			return true;
		return false;
	}
	
	public void dragged() {
		this.dragged = false;
	}
	
	public boolean isDragged() {
		return dragged;
	}
	
	public int getXClicked() {
		return xClicked;
	}
	
	public int getYClicked() {
		return yClicked;
	}
	
	public int getXMoved() {
		return xMoved;
	}
	
	public int getYMoved() {
		return yMoved;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int keyCode = e.getUnitsToScroll();
		if(keyCode < 0) wheelValue = -1;
		if(keyCode > 0) wheelValue = 1;
	}
	
	public int getWheelValue() {
		return wheelValue;
	}
	
	public void resetWheelValue() {
		this.wheelValue = 0;
	}

}