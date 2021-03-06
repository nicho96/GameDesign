package ca.nicho.server;

import java.util.ArrayList;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Logger;
import ca.nicho.foundation.entity.EntityWindmill;
import ca.nicho.foundation.packet.LogPacket;
import ca.nicho.foundation.packet.PointPacket;
import ca.nicho.foundation.packet.StartGamePacket;
import ca.nicho.server.world.ServerWorld;

public class ServerGame extends Game implements Logger {

	public static int p1Points = 250;
	public static int p2Points = 250;
	
	public int p1ship1 = -1;
	public int p1ship2 = -1;
	public int p1ship3 = -1;

	public int p2ship1 = -1;
	public int p2ship2 = -1;
	public int p2ship3 = -1;

	public static ArrayList<EntityWindmill> windmills = new ArrayList<EntityWindmill>();
	
	public static void initWorld(){
		if(Game.world == null)
			Game.world = new ServerWorld();
		Game.logger = new ServerGame();
	}
	
	public static int pointTicks = 0;
	public static void updatePoints(){
		if(pointTicks == 0){
			updateEntityPoints();
			p1Points++;
			if(ServerStart.con1 != null)
				ServerStart.con1.queue.add(new PointPacket(p1Points));
			p2Points++;
			if(ServerStart.con2 != null)
				ServerStart.con2.queue.add(new PointPacket(p2Points));
		}
		pointTicks = (pointTicks + 1) % 25;
	}
	
	private static void updateEntityPoints(){
		for(EntityWindmill ent : windmills){
			if(ent.owner == 1 && !ent.isDead){
				ServerGame.p1Points += ent.points;
			}else if(ent.owner == 2 && !ent.isDead){
				ServerGame.p2Points += ent.points;
			}
		}
	}
	
	public static void tryStartGame(){
		System.out.println("TRYING");
		if(ServerStart.con1 != null && ServerStart.con2 != null && !Game.started && ServerStart.con1.ready && ServerStart.con2.ready){
			ServerStart.sendGlobalPacket(new StartGamePacket());
			Game.started = true;
			System.out.println("SUCCEEDING");
		}
	}

	@Override
	public void sendMessage(String msg, int owner) {
		if(owner == 1){
			if(ServerStart.con1 != null){
				ServerStart.con1.queue.add(new LogPacket(msg));
			}
		}else if(owner == 2){
			if(ServerStart.con2 != null){
				ServerStart.con2.queue.add(new LogPacket(msg));
			}
		}
	}

	@Override
	public void sendGlobalMessage(String msg) {
		ServerStart.sendGlobalPacket(new LogPacket(msg));
	}
	
}
