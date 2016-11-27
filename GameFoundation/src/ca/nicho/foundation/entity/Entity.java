package ca.nicho.foundation.entity;

import ca.nicho.foundation.Sprite;

public abstract class Entity {
		
	public int current = 0;
	public Sprite[] sprites;
	public float locX;
	public float locY;
	public int id;
	public boolean isDead = false;
	public boolean detected = false;
	public byte owner = -1;
	
	public Entity(float x, float y, Sprite sprite, int id){
		this(x, y, new Sprite[1], id);
		sprites[0] = sprite;
	}
	
	public Entity(float x, float y, Sprite[] sprites, int id){
		this.id = id;
		this.sprites = sprites;
		this.locX = x;
		this.locY = y;
	}
	
	/**
	 * Update the entity. Currently only used by the server
	 * @return true if there is a reason to update the clients with updated information about this entity
	 */
	public abstract boolean tick();
	
	private int spriteTick = 1;
	public boolean clientTick(){
		if((spriteTick = (spriteTick + 1) % 2) == 0){
			current = (current + 1) % sprites.length;
		}
		
		return false;
	}
	
	public void collision(Entity ent){};
	
	public void flip(Entity ent){
		Sprite[] sprites = ent.sprites;
		
		for(int i = 0; i < sprites.length; i++){
			int[] data = sprites[0].data;
			
		}
	}
}
