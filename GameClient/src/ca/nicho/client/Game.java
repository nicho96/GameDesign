package ca.nicho.client;

import ca.nicho.client.entity.EntityPlayer;
import ca.nicho.client.world.World;

public class Game {

	public static int playerID;
	public static EntityPlayer player;
	
	public static World world;
	
	public static void initWorld(){
		if(world == null){
			world = new World();
		}
	}
	
}
