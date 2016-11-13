package ca.nicho.foundation.entity;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityTrail extends Entity {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_TRAIL};
	
	public EntityTrail(float x, float y, int id){
		super(x, y, sprites, id);
	}

	public int tickCount = 0;
	public boolean tick() {
		if(tickCount == 100){
			this.isDead = true;
			return true;
		}
		tickCount++;
		return false;
	}
	
}
