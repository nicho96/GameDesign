package ca.nicho.client.entity;

import ca.nicho.client.Sprite;
import ca.nicho.client.SpriteSheet;

public class EntityBattleship extends EntityPlayer {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_BATTLE_1, SpriteSheet.SPRITE_BATTLE_2, SpriteSheet.SPRITE_BATTLE_3, SpriteSheet.SPRITE_BATTLE_4};
	
	public EntityBattleship(float x, float y, int id) {
		super(x, y, sprites, id);
		this.inventory = new Entity[2];
		this.inventory[0] = new EntityRadar(-1, -1, -1);
	}
	
}
