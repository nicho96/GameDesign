package ca.nicho.client.entity;

import ca.nicho.client.Game;
import ca.nicho.client.SpriteSheet;

public class EntityMissile extends Entity{

	public int velocity = 20; //px/s\
	public int timeAlive = 20;
	
	public EntityMissile(float x, float y, int id) {
		super(x, y, SpriteSheet.SPRITE_MISSILE, id);
	}

	@Override
	public synchronized boolean tick() {
		Game.world.checkEntityCollision(this);
		this.locX += velocity;
		if(timeAlive-- == 0){
			this.isDead = true;
		}
		return true;
	}

}
