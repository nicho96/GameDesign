package ca.nicho.foundation.entity;

import java.util.Map;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityExplosion extends Entity {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_EXPLOSION_1, SpriteSheet.SPRITE_EXPLOSION_2, SpriteSheet.SPRITE_EXPLOSION_3, SpriteSheet.SPRITE_EXPLOSION_4, SpriteSheet.SPRITE_EXPLOSION_5, SpriteSheet.SPRITE_EXPLOSION_6, SpriteSheet.SPRITE_EXPLOSION_7};
	
	public EntityExplosion(float x, float y, int id) {
		super(x, y, -1, sprites, id);
	}

	public int tickCount;
	public boolean tick() {
		super.tick();
					
		if(tickCount == 30){
			this.isDead = true;
			return true;
		}
		tickCount++;
		
		for(Map.Entry<Integer, Entity> ent : Game.world.entities.entrySet()){
			Entity e = ent.getValue();
			
			float dx = e.locX - (locX + sprites[0].width);
			float dy = e.locY - (locY + sprites[0].height);
			double distance = Math.sqrt(dx*dx + dy*dy);
			
			if(distance < 80){
				e.damage(100);
			}
			
		}
		
		return false;
	}

	
	
}
