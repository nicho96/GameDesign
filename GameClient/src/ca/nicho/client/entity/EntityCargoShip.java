package ca.nicho.client.entity;

import ca.nicho.client.Sprite;
import ca.nicho.client.SpriteSheet;

public class EntityCargoShip extends EntityPlayer{
	private static final Sprite[] sprites = {SpriteSheet.SPRITE_CARGO_1, SpriteSheet.SPRITE_CARGO_2, SpriteSheet.SPRITE_CARGO_3, SpriteSheet.SPRITE_CARGO_4, SpriteSheet.SPRITE_CARGO_5};
	
	public EntityCargoShip(float x, float y, int ID){
		super(x, y, sprites, ID);
		this.inventory = new Entity[5];
		this.speedFactor = 1;
	}
	
}
