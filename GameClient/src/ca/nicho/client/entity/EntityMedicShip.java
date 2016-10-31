package ca.nicho.client.entity;

import ca.nicho.client.SpriteSheet;

public class EntityMedicShip extends EntityPlayer{

	public EntityMedicShip(float x, float y, int ID){
		super(x, y, SpriteSheet.SPRITE_MEDIC, ID);
	}
	
}
