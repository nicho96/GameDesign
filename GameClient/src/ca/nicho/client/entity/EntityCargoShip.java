package ca.nicho.client.entity;

import ca.nicho.client.SpriteSheet;

public class EntityCargoShip extends EntityPlayer{

	public EntityCargoShip(float x, float y, int ID){
		super(x, y, SpriteSheet.SPRITE_CARGO, ID);
	}
	
}
