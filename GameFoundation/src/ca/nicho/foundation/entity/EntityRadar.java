package ca.nicho.foundation.entity;

import java.util.Map;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityRadar extends Entity {

	public int count = 0;
	private static final Sprite[] sprites = {SpriteSheet.SPRITE_RADAR_1, SpriteSheet.SPRITE_RADAR_2, SpriteSheet.SPRITE_RADAR_3, SpriteSheet.SPRITE_RADAR_4, SpriteSheet.SPRITE_RADAR_5, SpriteSheet.SPRITE_RADAR_6, SpriteSheet.SPRITE_RADAR_7, SpriteSheet.SPRITE_RADAR_8};
	private static final Sprite icon = SpriteSheet.SPRITE_RADAR_ICON;
	public EntityRadar(float x, float y, int ID){
		super(x, y, 100, sprites, ID);
	}

	public Sprite getIcon(){return icon;}
	
	private int tickCount = 0;
	
	@Override
	public boolean clientTick() {
		super.clientTick();
		tickCount = (tickCount + 1) % 10;
		
		if(tickCount != 0 || this.owner != Game.ownerID)
			return false;
		
		count = 0;
		for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()){
			Entity e = set.getValue();
			if(e != this && e.owner != Game.ownerID && !(e instanceof EntityWave) && !(e instanceof EntityMissile)){
				if(e.detector == this || e.detector == null){
					float dx = e.locX - locX;
					float dy = e.locY - locY;
					double distance = Math.sqrt(dx*dx + dy*dy);
				
					if(distance < 800){
						e.detected = true;
						count += 1;
					}else{
						e.detected = false;
					}
				}
			}
		}
		
		return true;
	}
	
}
