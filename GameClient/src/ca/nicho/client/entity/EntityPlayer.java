package ca.nicho.client.entity;

import ca.nicho.client.ClientStart;
import ca.nicho.client.Game;
import ca.nicho.client.Sprite;
import ca.nicho.client.packet.EntityPacket;

public class EntityPlayer extends Entity {

	public boolean moved = true;
	public float speedFactor = 1;
	public float velocity = 200; //px/s
	
	public Entity[] inventory;
	
	public EntityPlayer(float x, float y, Sprite sprite, int id) {
		super(x, y, sprite, id);
	}
	
	public EntityPlayer(float x, float y, Sprite[] sprites, int id) {
		super(x, y, sprites, id);
	}

	@Override
	public boolean tick(){
		boolean tmp = moved;
		moved = false;
		return tmp;
	}
	
	public static final double sqrt2 = Math.sqrt(2);
	
	/**
	 * Moves the player to the appropriate location
	 * @param deltaX -1 means left, +1 means right, 0 means no change
	 * @param deltaY -1 mean up, +1 means down, 0 means no change
	 */
	public void move(double deltaX, double deltaY){
				
		double vx = deltaX * ClientStart.tickDelta / 1000 * velocity * speedFactor;
		double vy = deltaY * ClientStart.tickDelta / 1000 * velocity * speedFactor;
				
		if(vx != 0 && vy != 0){
			vx /= sqrt2;
			vy /= sqrt2;
		}
		
		if((vx != 0 || vy != 0) && ClientStart.con != null){
			if(Game.world.checkCollision(this, this.locX + vx, this.locY + vy)){
				//Essential rounding this value, or character will remain stuck in the wall
				this.locX = Math.round(locX);
				this.locY = Math.round(locY);
			}else{
				this.locX += vx;
				this.locY += vy;
				ClientStart.con.sendPacket(new EntityPacket(this));
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
