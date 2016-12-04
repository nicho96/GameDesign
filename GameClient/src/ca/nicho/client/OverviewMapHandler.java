package ca.nicho.client;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.packet.SpawnEntityPacket;
import ca.nicho.foundation.tile.Tile;

public class OverviewMapHandler {

	public int speed = 100;
	public int insetX = 39;
	public int insetY = 48;
	public double targetX = insetX;
	public double targetY = insetY;
	public boolean isOpen = false;
	
	public void tick(float f, float g, double tickDelta){
		
		double vx = f * tickDelta / 1000 * speed;
		double vy = g * tickDelta / 1000 * speed;
		
		targetX += vx;
		targetY += vy;
		
		if(targetX > SpriteSheet.SPRITE_MAP_LARGE.width - insetX)
			targetX = SpriteSheet.SPRITE_MAP_LARGE.width - insetX;
		else if(targetX < insetX)
			targetX = insetX;
		if(targetY > SpriteSheet.SPRITE_MAP_LARGE.height - insetY)
			targetY = SpriteSheet.SPRITE_MAP_LARGE.height - insetY;
		else if(targetY < insetY)
			targetY = insetY;
		
	}
	
	public void sendAirstrike(){
		
		for(int x = -Tile.TILE_DIM * 8; x <= Tile.TILE_DIM * 8; x += Tile.TILE_DIM * 2){
			for(int y = -Tile.TILE_DIM * 8; y <= Tile.TILE_DIM * 8; y += Tile.TILE_DIM * 2){
				ClientStart.con.sendPacket(new SpawnEntityPacket(ClientStart.map.getMapPositionX() + x, ClientStart.map.getMapPositionY() + y, 5, SpriteSheet.ENTITY_EXPLOSION, Game.ownerID));
			}
		}
	}
	
	public int getMapPositionX(){
		return(int)((targetX - insetX) / (SpriteSheet.SPRITE_MAP_LARGE.width - 2 * insetX) * Game.world.MAP_WIDTH * Tile.TILE_DIM);
	}
	
	public int getMapPositionY(){
		return(int)((targetY - insetY) / (SpriteSheet.SPRITE_MAP_LARGE.height - 2 * insetY ) * Game.world.MAP_HEIGHT * Tile.TILE_DIM);
	}
}
