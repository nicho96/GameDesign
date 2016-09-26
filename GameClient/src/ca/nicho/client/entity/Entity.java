package ca.nicho.client.entity;

import ca.nicho.client.SpriteSheet.Sprite;

public abstract class Entity {
		
	public Sprite sprite;
	public float locX;
	public float locY;
	public int id;
	
	public Entity(float x, float y, Sprite sprite, int id){
		this.id = id;
		this.sprite = sprite;
		this.locX = x;
		this.locY = y;
	}
	
	/**
	 * Update the entity. Currently only used by the server
	 * @return true if there is a reason to update the clients with updated information about this entity
	 */
	public abstract boolean tick();
	
}
