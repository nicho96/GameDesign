package ca.nicho.client.entity;

import ca.nicho.client.SpriteSheet;

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
