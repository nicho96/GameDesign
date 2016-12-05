package ca.nicho.foundation.entity;

import ca.nicho.foundation.SpriteSheet;

public class EntityEnemy extends Entity {

	public EntityEnemy(float x, float y, int id) {
		super(x, y, 100, SpriteSheet.SPRITE_ENEMY, id);
		
	}

	@Override
	public boolean tick() {
		super.tick();
		locX += 1;
		return true;
	}

}
