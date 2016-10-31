package ca.nicho.client.store;

import java.util.HashMap;

import ca.nicho.client.entity.Entity;
import ca.nicho.client.entity.EntityMissile;
import ca.nicho.client.entity.EntityRadar;

public class StoreHandler {
	
	public HashMap<Entity, Integer> costs = new HashMap<Entity, Integer>(); //Entity ID : Cost
	
	public StoreHandler(){
		
		//Bogus data will not be used
		costs.put(new EntityRadar(-1, -1, -1), 100);
		costs.put(new EntityMissile(-1, -1, -1), 100);
		
	}
	
}
