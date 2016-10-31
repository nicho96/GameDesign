package ca.nicho.client.entity;

import java.util.Map;

import ca.nicho.client.Game;
import ca.nicho.client.SpriteSheet;

public class EntityRadar extends Entity {

	public EntityRadar(float x, float y, int ID){
		super(x, y, SpriteSheet.SPRITE_RADAR, ID);
	}

	
	private int tickCount = 0;
	
	@Override
	public boolean tick(){
		return false;
	}
	
	@Override
	public boolean clientTick() {
		
		tickCount = (tickCount + 1) % 10;
		
		if(tickCount != 0)
			return false;
		
		for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()){
			Entity e = set.getValue();
			if(e != this){
				float dx = e.locX - locX;
				float dy = e.locY - locY;
				double distance = Math.sqrt(dx*dx + dy*dy);
				if(distance < 200){
					e.detected = false;
				}else{
					e.detected = true;
				}
			}
		}
		
		return true;
	}
	
}
