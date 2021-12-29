package objs;

import gfx.Assets;

public class Spaceship extends Object {

	public Spaceship(byte scale, int x, int y) {
		super("UFO", (byte) 1, scale, x, y, 0, Assets.ufo_tex);
	}

}
