package objs;

import gfx.Assets;

public class Asteroid extends Object {

	public Asteroid(byte scale, int x, int y) {
		super("Asteroid", (byte) 0, scale, x, y, 0, Assets.aster_tex);
	}

}
