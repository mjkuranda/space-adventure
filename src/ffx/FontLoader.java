package ffx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import gfx.Assets;

public class FontLoader {

	public static Font loadFont(String path, float size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static void init() {
		Assets.font20 = loadFont("res/fnts/digital/digital-7.ttf", 20);
		Assets.font28 = loadFont("res/fnts/digital/digital-7.ttf", 28);
		
		Assets.px_20 = loadFont("res/fnts/pixel/computer_pixel-7.ttf", 20);
		Assets.px_28 = loadFont("res/fnts/pixel/computer_pixel-7.ttf", 28);
		Assets.px_36 = loadFont("res/fnts/pixel/computer_pixel-7.ttf", 36);
		Assets.px_48 = loadFont("res/fnts/pixel/computer_pixel-7.ttf", 48);
		Assets.px_72 = loadFont("res/fnts/pixel/computer_pixel-7.ttf", 72);
		Assets.px_128 = loadFont("res/fnts/pixel/computer_pixel-7.ttf", 128);
	}

}
