package ca.nicho.foundation.entity;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityMedicShip extends EntityPlayer{

	private static final Sprite[] sprites = {SpriteSheet.SPRITE_MEDIC_1, SpriteSheet.SPRITE_MEDIC_2, SpriteSheet.SPRITE_MEDIC_3, SpriteSheet.SPRITE_MEDIC_4};
	private static final Sprite[] sprites_DEAD = {SpriteSheet.SPRITE_MEDIC_1_DEAD, SpriteSheet.SPRITE_MEDIC_2_DEAD, SpriteSheet.SPRITE_MEDIC_3_DEAD, SpriteSheet.SPRITE_MEDIC_4_DEAD};
	private static final Sprite[] sprites_HEAL = {SpriteSheet.SPRITE_MEDIC_1_HEAL, SpriteSheet.SPRITE_MEDIC_2_HEAL, SpriteSheet.SPRITE_MEDIC_3_HEAL, SpriteSheet.SPRITE_MEDIC_4_HEAL};
	
	public EntityMedicShip(float x, float y, int ID){
		super(x, y, 150, sprites, ID);
		this.capacity = 0;
		this.speedFactor = 1.5f;
	}
	
}
