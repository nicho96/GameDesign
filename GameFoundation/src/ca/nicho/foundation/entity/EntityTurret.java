package ca.nicho.foundation.entity;

import java.util.Map;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityTurret extends Entity {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_TURRET};
	
	public EntityTurret(float x, float y, int id) {
		super(x, y, 100, sprites, id);
	}
	
	private int shotTick = 0;
	public boolean tick(){
		
		if(shotTick == 0){
			for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()){
				Entity ent = set.getValue();
				if(ent.owner != this.owner){
					float x = this.locX - ent.locX;
					float y = this.locY - ent.locY;
					double distance = Math.sqrt(x*x + y*y);
					if(distance < 500){
						double rad = Math.atan2(x, y) - Math.PI;
						float dx = (float)Math.sin(rad);
						float dy = (float)Math.cos(rad);
						System.out.println("TARGETING: " + ent.id);
						EntityMissile missile = new EntityMissile(this.locX, this.locY, Game.world.entId++, dx, dy);
						Game.world.spawnEntity(missile);
						break;
					}
				}
			}
		}
		shotTick = (shotTick + 1) % 10;
		return super.tick();
	}
	
}
