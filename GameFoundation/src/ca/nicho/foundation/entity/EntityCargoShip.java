package ca.nicho.foundation.entity;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityCargoShip extends EntityPlayer{
	private static final Sprite[] sprites = {SpriteSheet.SPRITE_CARGO_1, SpriteSheet.SPRITE_CARGO_2, SpriteSheet.SPRITE_CARGO_3, SpriteSheet.SPRITE_CARGO_4, SpriteSheet.SPRITE_CARGO_5};
	
	public EntityCargoShip(float x, float y, int ID){
		super(x, y, sprites, ID);
		this.inventory = new Entity[5];
		this.speedFactor = 1;
	}
	
}