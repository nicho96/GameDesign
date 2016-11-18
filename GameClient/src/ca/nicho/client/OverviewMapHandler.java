package ca.nicho.client;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.packet.SpawnEntityPacket;
import ca.nicho.foundation.tile.Tile;

public class OverviewMapHandler {

	public int speed = 100;
	public double targetX = 0;
	public double targetY = 0;
	public boolean isOpen = false;
	
	public OverviewMapHandler(){
		
	}
	
	public void tick(int deltaX, int deltaY, double tickDelta){
				
		double vx = deltaX * tickDelta / 1000 * speed;
		double vy = deltaY * tickDelta / 1000 * speed;
		
		targetX += vx;
		targetY += vy;
		
		if(targetX > SpriteSheet.SPRITE_MAP_LARGE.width)
			targetX = SpriteSheet.SPRITE_MAP_LARGE.width;
		else if(targetX < 0)
			targetX = 0;
		if(targetY > SpriteSheet.SPRITE_MAP_LARGE.height)
			targetY = SpriteSheet.SPRITE_MAP_LARGE.height;
		else if(targetY < 0)
			targetY = 0;
		
	}
	
	public void sendAirstrike(){
		
		for(int x = -Tile.TILE_DIM * 8; x <= Tile.TILE_DIM * 8; x += Tile.TILE_DIM * 2){
			for(int y = -Tile.TILE_DIM * 8; y <= Tile.TILE_DIM * 8; y += Tile.TILE_DIM * 2){
				ClientStart.con.sendPacket(new SpawnEntityPacket(ClientStart.map.getMapPositionX() + x, ClientStart.map.getMapPositionY() + y, SpriteSheet.ENTITY_TRAIL, Game.ownerID));
			}
		}
	}
	
	public int getMapPositionX(){
		return(int)(targetX / SpriteSheet.SPRITE_MAP_LARGE.width * Game.world.MAP_WIDTH * Tile.TILE_DIM);
	}
	
	public int getMapPositionY(){
		return(int)(targetY / SpriteSheet.SPRITE_MAP_LARGE.height * Game.world.MAP_HEIGHT * Tile.TILE_DIM);
	}
}
