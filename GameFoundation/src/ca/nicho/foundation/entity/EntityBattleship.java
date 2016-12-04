package ca.nicho.foundation.entity;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityBattleship extends EntityPlayer {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_BATTLE_1, SpriteSheet.SPRITE_BATTLE_2, SpriteSheet.SPRITE_BATTLE_3, SpriteSheet.SPRITE_BATTLE_4};
	private static Sprite[] sprites_DEAD = {SpriteSheet.SPRITE_BATTLE_1_DEAD, SpriteSheet.SPRITE_BATTLE_2_DEAD, SpriteSheet.SPRITE_BATTLE_3_DEAD, SpriteSheet.SPRITE_BATTLE_4_DEAD};
	
	public EntityBattleship(float x, float y, int id) {
		super(x, y, 500, sprites, id);
		this.capacity = 2;
		this.speedFactor = 1.4f;
		
	}
	
	
	int cooldownTick = 0;
	@Override
	public void damage(int amount){
		if(cooldownTick == 0){
			health -= amount;
			if(health < 0 && origHealth > 0){
				this.isDead = true;
				sprites = EntityBattleship.sprites_DEAD;
			}
			Game.world.entityDamaged(this);
			cooldownTick = 30;
		}
	}
}
