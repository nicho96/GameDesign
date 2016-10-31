package ca.nicho.client;

import java.util.Map;

import ca.nicho.client.entity.Entity;
import ca.nicho.client.world.World;

public class Game {

	public static int[] ships;
	public static int current;
	
	public static World world;
	
	public static void initWorld(){
		if(world == null){
			world = new World();
		}
	}
	
	/**
	 * Used to determine if an entity ID is a player's ship
	 * @param id The entity ID in question
	 * @return true if it is a player's ship, false if not
	 */
	public static boolean isIDPlayer(int id){
		if(ships == null)
			return false;
		
		for(int i : ships){
			if(i == id){
				return true;
			}
		}
		return false;
		
	}
	
}
