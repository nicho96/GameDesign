package ca.nicho.client.store;

import java.util.HashMap;

import ca.nicho.client.Sprite;
import ca.nicho.client.SpriteSheet;
import ca.nicho.client.entity.Entity;
import ca.nicho.client.entity.EntityBattleship;
import ca.nicho.client.entity.EntityMissile;
import ca.nicho.client.entity.EntityRadar;

public class StoreHandler {
	
	public static boolean isOpen = false;
	
	public HashMap<Entity, Sprite> costs = new HashMap<Entity, Sprite>(); //Entity ID : Cost
	
	public StoreHandler(){
		
		costs.put(new EntityRadar(-1, -1, -1), new Sprite(100));
		costs.put(new EntityMissile(-1, -1, -1), new Sprite(125));
		costs.put(new EntityBattleship(-1, -1, -1), new Sprite(150));
		
	}
	
}
