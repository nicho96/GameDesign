package ca.nicho.client.entity;

import ca.nicho.client.ClientStart;
import ca.nicho.client.Game;
import ca.nicho.client.SpriteSheet;
import ca.nicho.client.packet.EntityPacket;

public class EntityPlayer extends Entity {

	public boolean moved = true;
	public double velocity = 1000; //px/s
	
	public EntityPlayer(float x, float y, int id) {
		super(x, y, SpriteSheet.SPRITE_PLAYER, id);
	}

	@Override
	public boolean tick(){
		boolean tmp = moved;
		moved = false;
		return tmp;
	}
	
	public static final double sqrt2 = Math.sqrt(2);
	
	public void move(double deltaX, double deltaY){
		
		double vx = deltaX * ClientStart.tickDelta / 1000 * velocity;
		double vy = deltaY * ClientStart.tickDelta / 1000 * velocity;
				
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
	
}
