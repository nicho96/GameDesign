package ca.nicho.server;

import ca.nicho.client.Game;
import ca.nicho.client.packet.PointPacket;
import ca.nicho.server.world.ServerWorld;

public class ServerGame extends Game {

	public static int p1Points = 0;
	public static int p2Points = 0;
	
	public static void initWorld(){
		if(Game.world == null)
			Game.world = new ServerWorld();
	}
	
	public static int pointTicks = 0;
	public static void updatePoints(){
		if(pointTicks == 0){
			p1Points++;
			if(ServerStart.con1 != null)
				ServerStart.con1.sendPacket(new PointPacket(p1Points));
			p2Points++;
			if(ServerStart.con2 != null)
				ServerStart.con2.sendPacket(new PointPacket(p2Points));
		}
		pointTicks = (pointTicks + 1) % 25;
	}
	
}
