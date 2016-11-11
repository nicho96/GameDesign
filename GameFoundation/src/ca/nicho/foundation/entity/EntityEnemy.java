package ca.nicho.foundation.entity;

import ca.nicho.foundation.SpriteSheet;

public class EntityEnemy extends Entity {

	public EntityEnemy(float x, float y, int id) {
		super(x, y, SpriteSheet.SPRITE_ENEMY, id);
	}

	@Override
	public boolean tick() {
		locX += 1;
		return true;
	}

}