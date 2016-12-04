package ca.nicho.foundation.entity;

import java.util.Map;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class EntityTurret extends Entity {

	private static Sprite[] sprites = {SpriteSheet.SPRITE_TURRET_1, SpriteSheet.SPRITE_TURRET_2, SpriteSheet.SPRITE_TURRET_3, SpriteSheet.SPRITE_TURRET_4, SpriteSheet.SPRITE_TURRET_5, SpriteSheet.SPRITE_TURRET_6, SpriteSheet.SPRITE_TURRET_7, SpriteSheet.SPRITE_TURRET_8,};
	private static Sprite[] sprites_FIRE = {SpriteSheet.SPRITE_TURRET_1_FIRE, SpriteSheet.SPRITE_TURRET_1_FIRE, SpriteSheet.SPRITE_TURRET_2_FIRE, SpriteSheet.SPRITE_TURRET_1_FIRE,SpriteSheet.SPRITE_TURRET_2_FIRE, SpriteSheet.SPRITE_TURRET_1_FIRE, SpriteSheet.SPRITE_TURRET_1_FIRE, SpriteSheet.SPRITE_TURRET_2_FIRE};
	public boolean shotsFired; 
	public EntityTurret(float x, float y, int id) {
		super(x, y, 100, sprites, id);
	}
	
	private int shotTick = 0;
	@Override
	public boolean tick(){
		shotsFired = true;
		if(shotTick == 0){
			for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()){
				Entity ent = set.getValue();
				if(ent.owner != this.owner && !ent.isDead){
					float x = this.locX - ent.locX;
					float y = this.locY - ent.locY;
					double distance = Math.sqrt(x*x + y*y);
					if(distance < 500){
						double rad = Math.atan2(x, y) - Math.PI;
						float dx = (float)Math.sin(rad);
						float dy = (float)Math.cos(rad);
						EntityMissile missile = new EntityMissile(this.locX, this.locY, Game.world.entId++, dx, dy);
						missile.owner = this.owner;
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
