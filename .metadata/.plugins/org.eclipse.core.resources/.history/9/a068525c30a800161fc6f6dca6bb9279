package ca.nicho.foundation.world;

import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.entity.EntityBattleship;
import ca.nicho.foundation.entity.EntityCargoShip;
import ca.nicho.foundation.entity.EntityEnemy;
import ca.nicho.foundation.entity.EntityMedicShip;
import ca.nicho.foundation.entity.EntityMissile;
import ca.nicho.foundation.entity.EntityPlayer;
import ca.nicho.foundation.entity.EntityRadar;
import ca.nicho.foundation.packet.EntityPacket;
import ca.nicho.foundation.tile.Tile;

public class World implements Runnable{

	public int entId;
	public ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();
	
	public int p1SpawnX;
	public int p1SpawnY;
	
	public int p2SpawnX;
	public int p2SpawnY;
	
	public static final int MAP_WIDTH = 100;
	public static final int MAP_HEIGHT = 100;
	public Tile[] map;
	
	public World(){
		map = new Tile[MAP_WIDTH * MAP_HEIGHT];
		this.setTileByCoord(2, 2, Tile.TILE_WOOD);
		this.setTileByCoord(30, 2, Tile.TILE_STONE);
		System.out.println("World: World has been created!");
	}
	
	/**
	 * Called when the server sends a packet to the client containing entity information.
	 * This method will parse out the data and convert it into an entity object
	 * @param packet the entity packet to be parsed
	 */
	public void entityUpdatePacketRecieved(EntityPacket packet){
		if(entities.containsKey(packet.id)){
			//Entity exists, update it's values. If not, create a new entity.
			Entity ent = entities.get(packet.id);
			ent.locX = packet.x;
			ent.locY = packet.y;
		}else{
			Entity ent = null;
			switch(packet.type){
				case SpriteSheet.ENTITY_PLAYER: //Entity player is not a valid packet anymore, but default to a battleship sprite
					EntityPlayer player = new EntityPlayer(packet.x, packet.y, SpriteSheet.SPRITE_BATTLESHIP, packet.id);
					//if(Game.player == null){
						//Game.player = player;
					//}
					ent = player;
					break;
				case SpriteSheet.ENTITY_ENEMY:
					System.out.println(packet.id);
					ent = new EntityEnemy(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_BATTLESHIP:
					ent = new EntityBattleship(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_MEDIC:
					ent = new EntityMedicShip(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_CARGO:
					ent = new EntityCargoShip(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_MISSILE:
					ent = new EntityMissile(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_RADAR:
					ent = new EntityRadar(packet.x, packet.y, packet.id);
					break;
				
			}
			if(ent != null){
				System.out.println(packet.owner);
				ent.owner = packet.owner;
				spawnEntity(ent);
			}else{
				System.out.println("World: Tried to spawn a null entity (packet malformed?) " + packet.type);
			}
		}
	}
	
	/**
	 * Spawn an entity into the world
	 * @param ent the entity to be spawned
	 */
	public void spawnEntity(Entity ent){
		entities.put(ent.id, ent);
	}
	
	/**
	 * Set a tile by index position (in array, convenient for packets)
	 * @param pos the index position of the tile
	 * @param tile the tile type
	 */
	public void setTileByPos(int pos, Tile tile){
		map[pos] = tile;
	}
	
	/**
	 * Set a tile by location (x, y). Convenient for physical tile placement without the need for conversion to pos
	 * @param x
	 * @param y
	 * @param tile the tile to be placed
	 */
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
	
	/**
	 * Kill an entity in the world by its integer ID
	 * @param id the id of the entity
	 */
	public void killEntity(int id){
		entities.remove(id);
	}
	
	/**
	 * Check the collision of a specified entity and a tile
	 * @param ent the entity in question
	 * @param posX
	 * @param posY
	 * @return true if there is a collision with a tile
	 */
	public boolean checkCollision(Entity ent, double posX, double posY){
		return getTileAtLocation(posX, posY) != null ||
				getTileAtLocation(posX, posY + ent.sprites[ent.current].height) != null ||
				getTileAtLocation(posX +  ent.sprites[ent.current].width, posY) != null ||
				getTileAtLocation(posX + ent.sprites[ent.current].width, posY + ent.sprites[ent.current].height) != null;
	}
	
	/**
	 * Check entity collision between e1 and some entity
	 * @param e1 the entity checking for collisions
	 */
	public void checkEntityCollision(Entity e1){
		for(Map.Entry<Integer, Entity> ent2 : entities.entrySet()){
			Entity e2 = ent2.getValue();
			if(e1 == e2)
				continue;
			//May want to consider changing to a stand-alone algorithm, this is for convenience
			Rectangle r1 = new Rectangle((int)e1.locX, (int)e1.locY, (int)e1.sprites[e1.current].width, (int)e1.sprites[e1.current].height);
			Rectangle r2 = new Rectangle((int)e2.locX, (int)e2.locY, (int)e2.sprites[e2.current].width, (int)e2.sprites[e2.current].height);
			if(r1.intersects(r2)){
				e1.collision(e2);
				e2.collision(e1);
			}
		}
	}
	
	public void load(String name){
		
		File f = new File("level/" + name);
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			
			int width = in.readInt();
			int height = in.readInt();
			
			System.out.println(width);
			System.out.println(height);
			
			map = new Tile[width * height];
			
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					int type = in.readInt();
					if(type == SpriteSheet.TILE_P1_SPAWN){
						this.p1SpawnX = x * Tile.TILE_DIM;
						this.p1SpawnY = y * Tile.TILE_DIM;
					}else if(type == SpriteSheet.TILE_P2_SPAWN){
						this.p2SpawnX = x * Tile.TILE_DIM;
						this.p2SpawnY = y * Tile.TILE_DIM;
					}
					map[x + y * width] = Tile.getTileByID(type);
				}
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get the player entity instance from the world
	 * @return the player entity
	 */
	public EntityPlayer getPlayer(){
		return (Game.ships != null) ? (EntityPlayer) entities.get(Game.ships[Game.current]) : null;
	}
	
	@Override
	public void run(){
		
		long last = System.currentTimeMillis();
		while(true){
			long current = System.currentTimeMillis();
			if(current - last > 100){
				last = current;
				tick();
			}
		}
		
	}
	
	private void tick(){
		for(Map.Entry<Integer, Entity> set : entities.entrySet()){
			Entity e = set.getValue();
			e.clientTick();
		}
	}
	
}
