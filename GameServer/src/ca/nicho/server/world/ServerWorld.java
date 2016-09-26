package ca.nicho.server.world;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ca.nicho.client.SpriteSheet;
import ca.nicho.client.entity.Entity;
import ca.nicho.client.entity.EntityBattleship;
import ca.nicho.client.entity.EntityEnemy;
import ca.nicho.client.entity.EntityPlayer;
import ca.nicho.client.packet.EntityPacket;
import ca.nicho.client.packet.TilePacket;
import ca.nicho.client.tile.Tile;
import ca.nicho.server.ServerStart;

public class ServerWorld {

	public GameClock clock;
	public int entId;
	public ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();
	
	public static final int MAP_WIDTH = 50;
	public static final int MAP_HEIGHT = 30;
	public Tile[] map;
	
	public ServerWorld(){
		map = new Tile[MAP_WIDTH * MAP_HEIGHT];
		
		for(int y = 0; y < MAP_HEIGHT; y++){
			for(int x = 0; x < MAP_WIDTH; x++){
				if(y == 0 || y == 29 || x == 0 || x == 49) map[y * MAP_WIDTH + x] = Tile.TILE_STONE;
			}
		}
		
		//this.spawnEntity(new EntityEnemy(200, 200, 200));
		this.spawnEntity(new EntityBattleship(200, 400, 300));
		clock = new GameClock();
	}
	
	public void startClock(){
		new Thread(clock).start();
	}
	
	public void spawnEntity(Entity ent){
		entities.put(ent.id, ent);
		//TODO change to not send all entity data to existing connections
		for(Map.Entry<Integer, Entity> set : entities.entrySet()){
			EntityPacket packet = new EntityPacket(set.getValue());
			ServerStart.sendGlobalPacket(packet);
		}
	}
	
	public void entityUpdatePacketRecieved(EntityPacket packet){
		Entity ent = null;
		if(entities.containsKey(packet.id)){
			//Entity exists, update it's values. If not, create a new entity.
			ent = entities.get(packet.id);
			ent.locX = packet.x;
			ent.locY = packet.y;
			
			if(ent != null){
				ServerStart.sendGlobalPacket(new EntityPacket(ent));
			}
			
		}else{
			ent = null;
			switch(packet.type){
				case SpriteSheet.ENTITY_PLAYER:
					ent = new EntityPlayer(packet.x, packet.y, packet.id);
					break;
			}
			if(ent != null){
				spawnEntity(ent);
			}else{
				System.out.println("World: Tried to spawn a null entity (packet malformed?)");
			}
		}
		
	}
	
	public void setTileByPos(int pos, Tile tile){
		map[pos] = tile;
		TilePacket packet = new TilePacket(pos, tile);
		ServerStart.sendGlobalPacket(packet);
	}
	
	public void setTileByCoord(int x, int y, Tile tile){
		int pos = y * MAP_WIDTH + x;
		setTileByPos(pos, tile);
	}
	
	public void killEntity(int id){
		entities.remove(id);
	}
	
	private void tick(){
		for(Map.Entry<Integer, Entity> ent : entities.entrySet()){
			if(ent.getValue().tick()){
				ServerStart.sendGlobalPacket(new EntityPacket(ent.getValue()));
			}
		}
	}
	
	private class GameClock implements Runnable{
		
		public boolean running = true;
		
		@Override
		public void run(){
			
			long last = System.currentTimeMillis();
			while(running){
				long current = System.currentTimeMillis();
				if(current - last > 30){
					last = current;
					tick();
				}
			}
		}
		
	}

}
