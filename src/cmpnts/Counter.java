package cmpnts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import gfx.Assets;

public class Counter {
	
	// coordinates of the counter
	private int x, y;

	// length of the counter
	private byte length;
	
	// number given for the counter
	private int number;
	
	// how many places
	private byte plc;
	
	// how many zeros
	private byte zeros;
	
	// digits
	private byte[] digits;
	
	// Is there a change?
	private boolean upd;
	
	// is display it
	private boolean display;
	
	public Counter(int x, int y, int number, byte plc) {
		this.x = x;
		this.y = y;
		
		this.plc = plc;
		
		this.number = number;
		this.length = (byte) ("" + number).length();
		this.digits = new byte[this.plc];
		
		this.zeros = (byte) (this.plc - this.length);
	}
	
	public void update() {
		if(this.digits.length <= 3) {
			int auxnum = 0;
			
			for(byte b = 1; b < this.plc + 1; b++) {
				int num10 = build10(b);
				int div = ((this.number - auxnum) / num10);
//				System.out.println(b + ", " + number + ", " + num10 + ", " + div);
				this.digits[b - 1] = (byte) div;
				
				auxnum += div * num10;
			}	
		} else {
			int num = new Integer(this.number);
//			this.length = (byte) (this.number + "").length();
//			this.zeros = (byte) (this.plc - this.length);
//			
//			byte dif = (byte) (this.plc - this.length); // ile zer
//			for(byte g = 0; g < dif; g++) {
//				this.digits[g] = '0';
//			}
//			
//			byte in = dif;
//			
//			for(int l = 0; l < ("" + this.number).length(); l++) {
//				int dx = 1;
//				for(int j = 1; j < ("" + num).length(); j++) {
//					dx = dx * 10;
//				}
//				byte m = (byte) (num / dx);
//				//System.out.println((num + "").length() + ", " + num + ", " + dx + ", " + m);	
//				num -= (dx * m);
//				
//				System.out.println(in);
//				this.digits[in] = m;
//				in++;
//			}
//			
			byte q = (byte) (num / 10000000);
			num -= (q * 10000000);
			byte w = (byte) (num / 1000000);
			num -= (w * 1000000);
			byte e = (byte) (num / 100000);
			num -= (e * 100000);
			byte r = (byte) (num / 10000);
			num -= (r * 10000);
			byte t = (byte) (num / 1000);
			num -= (t * 1000);
			byte y = (byte) (num / 100);
			num -= (y * 100);
			byte u = (byte) (num / 10);
			num -= (u * 10);
			byte i = (byte) (num / 1);
			num -= (i * 1);
			
			//System.out.println(q + ", " + w + ", " + e + ", " + r + ", " + t + ", " + y + ", " + u + ", " + i);
			
			this.digits[0] = q;
			this.digits[1] = w;
			this.digits[2] = e;
			this.digits[3] = r;
			this.digits[4] = t;
			this.digits[5] = y;
			this.digits[6] = u;
			this.digits[7] = i;
		}
	}
	
	public void render(Graphics g) {
		// Background
		g.setColor(Color.GRAY);
		g.fillRect(x, y, length * 14 + 16 + (zeros * 14), 36);
		
		Graphics2D g2d = (Graphics2D) g;
		float thickness = 2;
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(thickness));
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x, y, length * 14 + 16 + (zeros * 14), 36);
		g2d.setStroke(oldStroke);
		
		g.setColor(Color.GREEN);
		g.setFont(Assets.font28);
		
//		String str = "";
//		for(byte z = 0; z < this.zeros; z++) {
//			str = str + "0";
//		}
//		str = str + number + "";
//		char[] c = new char[this.length];
//		str.getChars(0, this.length - 1, c, 0);
//		System.out.println(str);
//		for(byte b = 0; b < this.length; b++) {
//			byte a = 0;
//			g.drawString(c[b] + "", this.x + (b * 14) + 9 + a + (this.zeros * 14), this.y + 18 + 9);
//		}
		
//		for(byte z = 0; z < this.zeros; z++) {
//			g.drawString("0", this.x + (z * 14) + 9, this.y + 18 + 9);			
//		}
//		for(byte b = 0; b < this.length; b++) {
//			byte a = 0;
//			if(this.digits[b] == 1) {
//				a = 7;
//			}
//			g.drawString(this.digits[b] + "", this.x + (b * 14) + 9 + a + (this.zeros * 14), this.y + 18 + 9);
//		}
		
		for(byte b = 0; b < this.digits.length; b++) {
			byte a = 0;
			if(this.digits[b] == 1) {
				a = 7;
			}
			
			g.drawString(this.digits[b] + "", this.x + (b * 14) + 9 + a, this.y + 18 + 9);
		}
	}
	
	public void tick(int number) {
		setNumber(number);
		update();
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getWidth() {
		return (length * 14 + 16 + (zeros * 14));
	}
	
	
	
	
	private int build10(byte b) {
		int n = 1;
		
		for(byte i = 0; i < (this.length - b); i++) {
			n = n * 10;
		}
		
		return n;
		//return (n == 1) ? 0 : n;
	}

}
