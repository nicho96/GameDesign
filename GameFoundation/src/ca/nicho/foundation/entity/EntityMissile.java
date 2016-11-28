package ca.nicho.foundation.entity;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.SpriteSheet;

public class EntityMissile extends Entity{

	public int velocity = 20; //px/s\
	public int timeAlive = 20;
	
	public EntityMissile(float x, float y, int id) {
		super(x, y, -1, SpriteSheet.SPRITE_MISSILE, id);
	}

	@Override
	public synchronized boolean tick() {
		super.tick();
		Game.world.checkEntityCollision(this);
		this.locX += velocity;
		if(timeAlive-- == 0){
			this.isDead = true;
		}
		return true;
	}

}
