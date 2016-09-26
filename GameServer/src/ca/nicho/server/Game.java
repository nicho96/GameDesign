package ca.nicho.server;

import ca.nicho.server.world.ServerWorld;

public class Game {

	public static ServerWorld world;
	
	public static void initWorld(){
		if(world == null){
			world = new ServerWorld();
		}
	}
	
}
