package ca.nicho.client.world;

import java.util.concurrent.ConcurrentHashMap;

import ca.nicho.client.Game;
import ca.nicho.client.SpriteSheet;
import ca.nicho.client.entity.Entity;
import ca.nicho.client.entity.EntityBattleship;
import ca.nicho.client.entity.EntityEnemy;
import ca.nicho.client.entity.EntityPlayer;
import ca.nicho.client.packet.EntityPacket;
import ca.nicho.client.tile.Tile;

public class World {

	public int entId;
	public ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();
	
	public static final int MAP_WIDTH = 50;
	public static final int MAP_HEIGHT = 30;
	public Tile[] map;
	
	public World(){
		map = new Tile[MAP_WIDTH * MAP_HEIGHT];
		this.setTileByCoord(2, 2, Tile.TILE_WOOD);
		this.setTileByCoord(30, 2, Tile.TILE_STONE);
		System.out.println("World: World has been created!");
	}
	
	public void entityUpdatePacketRecieved(EntityPacket packet){
		if(entities.containsKey(packet.id)){
			//Entity exists, update it's values. If not, create a new entity.
			Entity ent = entities.get(packet.id);
			ent.locX = packet.x;
			ent.locY = packet.y;
		}else{
			Entity ent = null;
			switch(packet.type){
				case SpriteSheet.ENTITY_PLAYER:
					EntityPlayer player = new EntityPlayer(packet.x, packet.y, packet.id);
					if(Game.player == null){
						System.out.println("Player Entity ID: " + player.id);
						Game.playerID = player.id;
						Game.player = player;
					}
					ent = player;
					break;
				case SpriteSheet.ENTITY_ENEMY:
					ent = new EntityEnemy(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_BATTLESHIP:
					ent = new EntityBattleship(packet.x, packet.y, packet.id);
					break;
			}
			if(ent != null){
				spawnEntity(ent);
			}else{
				System.out.println("World: Tried to spawn a null entity (packet malformed?)");
			}
		}
	}
	
	public void spawnEntity(Entity ent){
		entities.put(ent.id, ent);
	}
	
	public void setTileByPos(int pos, Tile tile){
		map[pos] = tile;
	}
	
	public void setTileByCoord(int x, int y, Tile tile){
		int pos = y * MAP_WIDTH + x;
		setTileByPos(pos, tile);
	}
	
	/**
	 * Get a tile at a specified PIXEL location (not tile location)
	 * @param x
	 * @param y
	 * @return the tile at that location, null if there are no tiles
	 */
	public Tile getTileAtLocation(double x, double y){
		int tileX = (int)x / Tile.TILE_DIM;
		int tileY = (int)y / Tile.TILE_DIM;
		return map[tileY * MAP_WIDTH + tileX];
	}
	
	public void killEntity(int id){
		entities.remove(id);
	}
	
	public boolean checkCollision(Entity ent, double posX, double posY){
		return getTileAtLocation(posX, posY) != null ||
				getTileAtLocation(posX, posY + ent.sprite.height) != null ||
				getTileAtLocation(posX +  ent.sprite.width, posY) != null ||
				getTileAtLocation(posX + ent.sprite.width, posY + ent.sprite.height) != null;
	}
	
	public EntityPlayer getPlayer(){
		return (EntityPlayer) entities.get(Game.playerID);
	}
	
}
