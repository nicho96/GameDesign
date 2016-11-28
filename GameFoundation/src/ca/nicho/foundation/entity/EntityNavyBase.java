package ca.nicho.foundation.entity;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityNavyBase extends Entity {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_NAVY_BASE};
	
	public EntityNavyBase(float x, float y, int id) {
		super(x, y, 300, sprites, id);
	}

	@Override
	public boolean tick() {
		super.tick();
		return false;
	}

}
