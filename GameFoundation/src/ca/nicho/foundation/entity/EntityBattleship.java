package ca.nicho.foundation.entity;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityBattleship extends EntityPlayer {

	private static Sprite[] sprites_ALIVE = {SpriteSheet.SPRITE_BATTLE_1, SpriteSheet.SPRITE_BATTLE_2, SpriteSheet.SPRITE_BATTLE_3, SpriteSheet.SPRITE_BATTLE_4};
	private static Sprite[] sprites_DEAD = {SpriteSheet.SPRITE_BATTLE_1_DEAD, SpriteSheet.SPRITE_BATTLE_2_DEAD, SpriteSheet.SPRITE_BATTLE_3_DEAD, SpriteSheet.SPRITE_BATTLE_4_DEAD};
	
	public EntityBattleship(float x, float y, int id) {
		super(x, y, 500, sprites_ALIVE, id);
		this.capacity = 2;
		this.speedFactor = 1.4f;
		this.name = "Battle Ship";
	}
	
	@Override
	public boolean clientTick(){
		if(isDead){
			super.sprites = sprites_DEAD;
		}else{
			super.sprites = sprites_ALIVE;
		}
		return super.clientTick();
	}
	
}
