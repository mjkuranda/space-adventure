package objs;

import gfx.Assets;

public class Meteoroid extends Object {

	public Meteoroid(byte scale, int x, int y) {
		super("Meteoroid", (byte) 2, scale, x, y, 0, Assets.meteor_tex);
	}

}
