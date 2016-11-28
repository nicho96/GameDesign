package ca.nicho.foundation.entity;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityWave extends Entity {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_WAVE_1, SpriteSheet.SPRITE_WAVE_2, SpriteSheet.SPRITE_WAVE_3, SpriteSheet.SPRITE_WAVE_4, SpriteSheet.SPRITE_WAVE_5, SpriteSheet.SPRITE_WAVE_6, SpriteSheet.SPRITE_WAVE_7, SpriteSheet.SPRITE_WAVE_8, SpriteSheet.SPRITE_WAVE_9};
	
	public EntityWave(float x, float y, int id){
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
	
	public boolean clientTick(){
		super.clientTick();
		
		if(tickCount == 120){
			this.isDead = true;
			return true;
		}
		tickCount++;
		
		return false;
	}
	
}
