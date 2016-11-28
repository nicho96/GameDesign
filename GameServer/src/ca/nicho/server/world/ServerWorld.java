package ca.nicho.server.world;

import java.util.Map;

import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.entity.EntityExplosion;
import ca.nicho.foundation.entity.EntityMissile;
import ca.nicho.foundation.entity.EntityNavyBase;
import ca.nicho.foundation.entity.EntityRadar;
import ca.nicho.foundation.entity.EntityTrail;
import ca.nicho.foundation.entity.EntityWindmill;
import ca.nicho.foundation.packet.EntityPacket;
import ca.nicho.foundation.packet.KillEntityPacket;
import ca.nicho.foundation.packet.TilePacket;
import ca.nicho.foundation.tile.Tile;
import ca.nicho.foundation.world.World;
import ca.nicho.server.ServerGame;
import ca.nicho.server.ServerStart;

public class ServerWorld extends World{

	public GameClock clock;

	public ServerWorld(){
		super();

		this.load("level.lev");
		
		System.out.println("P1 Spawn: " + p1SpawnX + ", " + p1SpawnY + " - P2 Spawn: " + p2SpawnX + ", " + p2SpawnY);
		
		clock = new GameClock();
	}
	
	/**
	 * Start the game clock
	 */
	public void startClock(){
		new Thread(clock).start();
	}
	
	/**
	 * Spawn an entity into the world
	 * @param ent entity to be spawned
	 */
	
	public void spawnEntity(Entity ent){
		entities.put(ent.id, ent);
		EntityPacket packet = new EntityPacket(ent);
		ServerStart.sendGlobalPacket(packet);
		//TODO change to not send all entity data to existing connections
		/*for(Map.Entry<Integer, Entity> set : entities.entrySet()){
			EntityPacket packet = new EntityPacket(set.getValue());
			ServerStart.sendGlobalPacket(packet);
		}*/
	}
	
	public void entityUpdatePacketRecieved(EntityPacket packet){
		Entity ent = null;
		if(entities.containsKey(packet.id)){
			//Entity exists, update it's values. If not, create a new entity.
			ent = entities.get(packet.id);
			ent.locX = packet.x;
			ent.locY = packet.y;
			ent.owner = packet.owner;
			if(ent != null){
				ServerStart.sendGlobalPacket(new EntityPacket(ent));
			}
			
		}else{
			ent = null;
			switch(packet.type){
				case SpriteSheet.ENTITY_MISSILE:
					ent = new EntityMissile(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_RADAR:
					ent = new EntityRadar(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_TRAIL:
					ent = new EntityTrail(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_WINDMILL:
					EntityWindmill wind = new EntityWindmill(packet.x, packet.y, packet.id);
					ent = wind;
					ServerGame.windmills.add(wind);
					break;
				case SpriteSheet.ENTITY_NAVY_BASE:
					ent = new EntityNavyBase(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_EXPLOSION:
					ent = new EntityExplosion(packet.x, packet.y, packet.id);
				/*case SpriteSheet.ENTITY_WAVE:
					ent = new EntityWave(packet.x, packet.y, packet.id);
					break;
				case SpriteSheet.ENTITY_CARE_PACKAGE:
					ent = new EntityCarePackage(packet.x, packet.y, packet.id);
					break;*/
			}
			if(ent != null){
				ent.owner = packet.owner;
				spawnEntity(ent);
			}else{
				System.out.println("World: Tried to spawn a null entity (packet malformed?)");
			}
		}
		
	}
	
	public void setTileByPos(int pos, Tile tile){
		super.setTileByPos(pos, tile);
		TilePacket packet = new TilePacket(pos, tile);
		ServerStart.sendGlobalPacket(packet);
	}
	
	@Override
	public void entityDamaged(Entity e){
		ServerStart.sendGlobalPacket(new EntityPacket(e));
	}

	private void tick(){
		for(Map.Entry<Integer, Entity> ent : entities.entrySet()){
			Entity e = ent.getValue();
			if(e.tick()){
				if(ent.getValue().isDead){
					ServerStart.sendGlobalPacket(new KillEntityPacket(ent.getKey()));
					this.killEntity(ent.getKey());
				}else{
					ServerStart.sendGlobalPacket(new EntityPacket(ent.getValue()));
				}
			}
		}
		ServerGame.updatePoints();
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
