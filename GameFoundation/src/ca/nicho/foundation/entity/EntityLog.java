package ca.nicho.foundation.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityLog extends Entity{

	private static Sprite[] sprites = {SpriteSheet.SPRITE_LOG_1, SpriteSheet.SPRITE_LOG_2, SpriteSheet.SPRITE_LOG_3};
		
	public EntityLog(float x, float y, int id){
		super(x, y, sprites, id);
	}

	@Override
	public boolean tick() {
		return true;
	}

}
