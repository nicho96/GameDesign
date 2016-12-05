package ca.nicho.foundation.entity;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityNavyBase extends Entity {

	private static Sprite[] spritesGreen = {SpriteSheet.SPRITE_NAVY_BASE_G_1, SpriteSheet.SPRITE_NAVY_BASE_G_2, SpriteSheet.SPRITE_NAVY_BASE_G_3, SpriteSheet.SPRITE_NAVY_BASE_G_4};
	private static Sprite[] spritesRed = {SpriteSheet.SPRITE_NAVY_BASE_R_1, SpriteSheet.SPRITE_NAVY_BASE_R_2, SpriteSheet.SPRITE_NAVY_BASE_R_3, SpriteSheet.SPRITE_NAVY_BASE_R_4};
	
	public EntityNavyBase(float x, float y, int id) {
		super(x, y, 300, spritesRed, id);
		this.name = "Base";
	}
}

