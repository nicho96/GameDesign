package ca.nicho.foundation.entity;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityWindmill extends Entity {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_WINDMILL_1, SpriteSheet.SPRITE_WINDMILL_2, SpriteSheet.SPRITE_WINDMILL_3};
	
	public int points = 2;
	
	public EntityWindmill(float x, float y, int id){
		super(x, y, 25, sprites, id);
	}
	
}
