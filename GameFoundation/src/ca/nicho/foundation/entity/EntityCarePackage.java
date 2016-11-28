package ca.nicho.foundation.entity;

import java.util.Map;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityCarePackage extends Entity {

	public int count = 0;
	private static final Sprite[] sprites = {SpriteSheet.SPRITE_PACKAGE_1, SpriteSheet.SPRITE_PACKAGE_2, SpriteSheet.SPRITE_PACKAGE_3, SpriteSheet.SPRITE_PACKAGE_4, SpriteSheet.SPRITE_PACKAGE_5};
	
	public EntityCarePackage(float x, float y, int ID){
		super(x, y, 100, sprites, ID);
	}

	
	private int tickCount = 0;
	
	@Override
	public boolean tick(){
		return false;
	}
	
	@Override
	public boolean clientTick() {
		super.clientTick();
		tickCount = (tickCount + 1) % 10;
		
		if(tickCount != 0 || this.owner != Game.ownerID)
			return false;
		
		count = 0;
		for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()){
			Entity e = set.getValue();
			if(e != this && e.owner != Game.ownerID){
				float dx = e.locX - locX;
				float dy = e.locY - locY;
				double distance = Math.sqrt(dx*dx + dy*dy);
				
				if(distance < 200){
					e.detected = true;
					count += 1;
				}else{
					e.detected = false;
				}
			}
		}
		
		return true;
	}
	
}
