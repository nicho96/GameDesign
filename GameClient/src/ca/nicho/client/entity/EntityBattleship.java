package ca.nicho.client.entity;

import ca.nicho.client.Sprite;
import ca.nicho.client.SpriteSheet;

public class EntityBattleship extends EntityPlayer {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_BATTLESHIP, SpriteSheet.SPRITE_MEDIC_1};
	
	public EntityBattleship(float x, float y, int id) {
		super(x, y, sprites, id);
	}
	
}
