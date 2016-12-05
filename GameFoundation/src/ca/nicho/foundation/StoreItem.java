package ca.nicho.foundation;

import ca.nicho.foundation.entity.Entity;

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