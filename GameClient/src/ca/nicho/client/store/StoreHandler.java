package ca.nicho.client.store;

import java.util.ArrayList;
import java.util.Map;

import ca.nicho.client.AudioHandler;
import ca.nicho.client.ClientStart;
import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.entity.EntityCarePackage;
import ca.nicho.foundation.entity.EntityMissile;
import ca.nicho.foundation.entity.EntityNavyBase;
import ca.nicho.foundation.entity.EntityPlayer;
import ca.nicho.foundation.entity.EntityRadar;
import ca.nicho.foundation.entity.EntityTurret;
import ca.nicho.foundation.entity.EntityWindmill;
import ca.nicho.foundation.packet.PurchasePacket;

public class StoreHandler {
	
	public static boolean isOpen = false;
	public int position = 0;
	public ArrayList<StoreItem> costs = new ArrayList<StoreItem>();
	
	public StoreHandler(){
		
		costs.add(new StoreItem(new EntityRadar(-1, -1, -1), new Sprite(100), SpriteSheet.SPRITE_RADAR_1, 100));
		costs.add(new StoreItem(new EntityCarePackage(-1, -1, -1), new Sprite(150), SpriteSheet.SPRITE_PACKAGE_1, 150));
		costs.add(new StoreItem(new EntityWindmill(-1, -1, -1), new Sprite(250), SpriteSheet.SPRITE_WINDMILL_1, 250));
		costs.add(new StoreItem(new EntityTurret(-1, -1, -1), new Sprite(250), SpriteSheet.SPRITE_TURRET_1, 250));
		costs.add(new StoreItem(new EntityNavyBase(-1, -1, -1), new Sprite(1000), SpriteSheet.SPRITE_NAVY_BASE_R_1, 1000));
		
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
	
	/**
	 * Will only buy if player has inventory space and can afford
	 */
	public void buy(){
		StoreItem item = getCurrentStoreItem();
		if(item.cost < Game.points && Game.world.getPlayer().addItem(item.entity)){
			Game.points -= item.cost;
			ClientStart.con.sendPacket(new PurchasePacket(item.cost));
			StoreHandler.isOpen = false;
			AudioHandler.PURCHASE.play();
		}else{
			AudioHandler.DENIED.play();
		}
	}
	
	public class StoreItem {
		
		public Entity entity;
		public Sprite costSprite;
		public Sprite itemSprite;
		public int cost;
		
		public StoreItem(Entity entity, Sprite costSprite, Sprite itemSprite, int cost){
			this.entity = entity;
			this.costSprite = costSprite;
			this.itemSprite = itemSprite;
			this.cost = cost;
		}
		
	}
	
	public void openStore(){
		EntityPlayer p = Game.world.getPlayer();
		boolean opened = false;
		for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()){
			Entity e = set.getValue();
			if(e instanceof EntityNavyBase){
				float dx = e.locX - p.locX;
				float dy = e.locY - p.locY;
				double distance = Math.sqrt(dx*dx + dy*dy);
				if(distance < 200 && e.owner == p.owner){
					StoreHandler.isOpen = true;
					opened = true;
					return;
				}
			}
		}
		if(!opened){
			AudioHandler.DENIED.play();
		}
	}
	
}
