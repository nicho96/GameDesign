package ca.nicho.client.entity;

import ca.nicho.client.Sprite;
import ca.nicho.client.SpriteSheet;

public class EntityMedicShip extends EntityPlayer{

	private static final Sprite[] sprites = {SpriteSheet.SPRITE_MEDIC_1, SpriteSheet.SPRITE_MEDIC_2, SpriteSheet.SPRITE_MEDIC_3, SpriteSheet.SPRITE_MEDIC_4};
	
	public EntityMedicShip(float x, float y, int ID){
		super(x, y, sprites, ID);
	}
	
}
