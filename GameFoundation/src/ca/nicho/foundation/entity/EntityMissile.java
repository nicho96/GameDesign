package ca.nicho.foundation.entity;

import java.util.Map;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.SpriteSheet;

public class EntityMissile extends Entity{

	public int velocity = 20; //px/s\
	public int timeAlive = 20;
	
	public float dx;
	public float dy;
	
	public EntityMissile(float x, float y, int id) {
		super(x, y, -1, SpriteSheet.SPRITE_MISSILE, id);
	}
	
	public EntityMissile(float x, float y, int id, float dx, float dy) {
		super(x, y, -1, SpriteSheet.SPRITE_MISSILE, id);
		
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public synchronized boolean tick() {
		super.tick();
		
		if(Game.world.checkEntityCollision(this))
			this.isDead = true;
		
		this.locX += velocity * dx;
		this.locY += velocity * dy;
		if(timeAlive-- == 0){
			this.isDead = true;
		}
		return true;
	}

}
