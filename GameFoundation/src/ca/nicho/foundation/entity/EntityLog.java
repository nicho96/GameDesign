package ca.nicho.foundation.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityLog extends Entity{
		
	public EntityLog(float x, float y, int id){
		super(x, y, 100, SpriteSheet.SPRITE_LOG_LG, id);
	}

	@Override
	public boolean tick() {
		super.tick();
		return true;
	}

}
