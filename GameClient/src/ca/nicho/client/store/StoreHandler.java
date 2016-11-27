package ca.nicho.client.store;

import java.util.ArrayList;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.entity.EntityBattleship;
import ca.nicho.foundation.entity.EntityCarePackage;
import ca.nicho.foundation.entity.EntityMissile;
import ca.nicho.foundation.entity.EntityRadar;
import ca.nicho.foundation.entity.EntityWindmill;

public class StoreHandler {
	
	public static boolean isOpen = false;
	
	public int position = 0;
	public ArrayList<StoreItem> costs = new ArrayList<StoreItem>();
	
	public StoreHandler(){
		
		costs.add(new StoreItem(new EntityRadar(-1, -1, -1), new Sprite(100), 100));
		costs.add(new StoreItem(new EntityMissile(-1, -1, -1), new Sprite(125), 125));
		//costs.add(new StoreItem(new EntityBattleship(-1, -1, -1), new Sprite(150), 150));
		costs.add(new StoreItem(new EntityCarePackage(-1, -1, -1), new Sprite(150), 150));
		costs.add(new StoreItem(new EntityWindmill(-1, -1, -1), new Sprite(250), 250));
	}
	
	public void next(){
		position = (position + 1)  % costs.size();
	}
	
	public StoreItem getCurrentStoreItem(){
		return costs.get(position);
	}
	
	public boolean canAfford(int entityType){
		for(StoreItem item : costs){
			if(item.entity.sprites[0].type == entityType){
				if(item.cost - Game.points > 0){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	public class StoreItem {
		
		public Entity entity;
		public Sprite sprite;
		public int cost;
		
		public StoreItem(Entity entity, Sprite sprite, int cost){
			this.entity = entity;
			this.sprite = sprite;
			this.cost = cost;
		}
		
	}
	
}
