package ca.nicho.client.entity;

import ca.nicho.client.SpriteSheet;

public class EntityBattleship extends EntityPlayer {

	public EntityBattleship(float x, float y, int id) {
		super(x, y, SpriteSheet.SPRITE_BATTLESHIP, id);
	}
	
}
