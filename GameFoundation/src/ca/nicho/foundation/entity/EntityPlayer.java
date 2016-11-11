package ca.nicho.foundation.entity;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.packet.EntityPacket;

public class EntityPlayer extends Entity {

	public boolean moved = true;
	public float speedFactor = 1;
	public float velocity = 200; //px/s
	
	public Entity[] inventory;
	public int position = 0;
	
	
	public EntityPlayer(float x, float y, Sprite sprite, int id) {
		super(x, y, sprite, id);
	}
	
	public EntityPlayer(float x, float y, Sprite[] sprites, int id) {
		super(x, y, sprites, id);
	}
	
	public Entity getCurrent(){
		return inventory[position];
	}
	
	public void clearCurrent(){
		inventory[position] = null;
	}

	@Override
	public boolean tick(){
		boolean tmp = moved;
		moved = false;
		return tmp;
	}
	
	public void nextSlot(){
		position = (position + 1) % inventory.length;
	}
	
	public static final double sqrt2 = Math.sqrt(2);
	
	/**
	 * Moves the player to the appropriate location
	 * @param deltaX -1 means left, +1 means right, 0 means no change
	 * @param deltaY -1 mean up, +1 means down, 0 means no change
	 */
	public void move(double deltaX, double deltaY, double tickDelta){
				
		double vx = deltaX * tickDelta / 1000 * velocity * speedFactor;
		double vy = deltaY * tickDelta / 1000 * velocity * speedFactor;
				
		if(vx != 0 && vy != 0){
			vx /= sqrt2;
			vy /= sqrt2;
		}
		
		if((vx != 0 || vy != 0)){
			if(Game.world.checkCollision(this, this.locX + vx, this.locY + vy)){
				//Essential rounding this value, or character will remain stuck in the wall
				this.locX = Math.round(locX);
				this.locY = Math.round(locY);
			}else{
				this.locX += vx;
				this.locY += vy;
				moved = true;
			}
		}
	}
	
	@Override
	public void collision(Entity ent){
		if(ent instanceof EntityMissile){
			this.isDead = true;
		}
	}
	
}