package ca.nicho.foundation.entity;

import java.awt.Rectangle;
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
		
		for(Map.Entry<Integer, Entity> ent2 : Game.world.entities.entrySet()){
			Entity e2 = ent2.getValue();
			if(this == e2 || e2.owner == this.owner)
				continue;
			//May want to consider changing to a stand-alone algorithm, this is for convenience
			Rectangle r1 = new Rectangle((int)locX, (int)locY, (int)sprites[current].width, (int)sprites[current].height);
			Rectangle r2 = new Rectangle((int)e2.locX, (int)e2.locY, (int)e2.sprites[e2.current].width, (int)e2.sprites[e2.current].height);
			if(r1.intersects(r2)){
				e2.damage(20);
				this.isDead = true;
				break;
			}
		}
		
		this.locX += velocity * dx;
		this.locY += velocity * dy;
		if(timeAlive-- == 0){
			this.isDead = true;
		}
		return true;
	}

}
