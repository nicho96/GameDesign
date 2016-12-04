package ca.nicho.client;

import ca.nicho.client.store.StoreHandler;
import ca.nicho.foundation.Game;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.entity.EntityPlayer;
import ca.nicho.foundation.packet.EntityPacket;
import ca.nicho.foundation.packet.SpawnEntityPacket;
import ca.nicho.foundation.packet.SpawnMissilePacket;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Version;

public class GamePadListener {

	public static Controller analog = null;
	
	//Mappings of an XBOX360 controller
	public static Component LEFT_THUMB_X;
	public static Component LEFT_THUMB_Y;
	public static Component RIGHT_THUMB_X;
	public static Component RIGHT_THUMB_Y;
	public static Component A;
	public static Component B;
	public static Component X;
	public static Component Y;
	public static Component LEFT_TRIGGER;
	public static Component RIGHT_TRIGGER;
	public static Component BACK;
	public static Component START;
	public static Component LEFT_THUMB;
	public static Component RIGHT_THUMB;
	public static Component D_PAD;
			
	public static ComponentWrapper[] wrapped = new ComponentWrapper[16];
	
	public static void init(){
		System.out.println("JInput version: " + Version.getVersion()); 
		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment(); 
		Controller[] cs = ce.getControllers(); 
		for (int i = 0; i < cs.length; i++){
			System.out.println(i + ". " + cs[i].getName() + ", " + cs[i].getType() ); 
			if(cs[i].getType() == Type.GAMEPAD){
				analog = cs[i];
				System.out.println("Using: " + analog.getName());
				break;
			}
		}
		
		if(analog == null){
			System.out.println("No analog controller detected - not using controller configurations.");
			return;
		}
		
		for(Component c : analog.getComponents()){
			System.out.println(c);
		}
	
		//Mappings of an XBOX360 controller
		LEFT_THUMB_X = analog.getComponent(Identifier.Axis.X);
		LEFT_THUMB_Y = analog.getComponent(Identifier.Axis.Y);
		RIGHT_THUMB_X = analog.getComponent(Identifier.Axis.RX);
		RIGHT_THUMB_Y = analog.getComponent(Identifier.Axis.RY);
		A = analog.getComponent(Identifier.Button._0);
		B = analog.getComponent(Identifier.Button._1);
		X = analog.getComponent(Identifier.Button._2);
		Y = analog.getComponent(Identifier.Button._3);
		LEFT_TRIGGER = analog.getComponent(Identifier.Button._4);
		RIGHT_TRIGGER = analog.getComponent(Identifier.Button._5);
		BACK = analog.getComponent(Identifier.Button._6);
		START = analog.getComponent(Identifier.Button._7);
		LEFT_THUMB = analog.getComponent(Identifier.Button._8);
		RIGHT_THUMB = analog.getComponent(Identifier.Button._9);
		D_PAD = analog.getComponent(Identifier.Axis.POV);
				
		wrapped[0] = new ComponentWrapper(LEFT_THUMB_X){
			public void action() {
				// TODO Auto-generated method stuff				
			}
		};
		
		wrapped[1] = new ComponentWrapper(LEFT_THUMB_Y){
			public void action() {
				// TODO Auto-generated method stuff				
			}
		};
		
		wrapped[2] = new ComponentWrapper(RIGHT_THUMB_X){
			public void action() {
				// TODO Auto-generated method stuff				
			}
		};
		
		wrapped[3] = new ComponentWrapper(RIGHT_THUMB_Y){
			public void action() {
				// TODO Auto-generated method stuff				
			}
		};
		
		wrapped[4] = new ComponentWrapper(A){
			public void action() {
				if(reset){
					if(A.getPollData() > 0){
						if(ClientStart.con != null){
							if(ClientStart.map.isOpen){
								ClientStart.map.sendAirstrike();
							}else if(StoreHandler.isOpen){
								ClientStart.store.buy();
							}else{
								EntityPlayer p = Game.world.getPlayer();
								Entity item = p.getCurrent();
								if(item != null){
									ClientStart.con.sendPacket(new SpawnEntityPacket(Game.world.getPlayer().locX, Game.world.getPlayer().locY, Game.world.getPlayer().health, Game.world.getPlayer().getCurrent().sprites[0].type, Game.ownerID));
									p.clearCurrent();
								}
							}
						}else{	
							AudioHandler.PANDEMIC.play();
							String[] split = ClientStart.host_port.split(":");
							if(split.length == 2){
								ClientStart.HOST = split[0];
								ClientStart.PORT = Integer.parseInt(split[1]);
							}else if(split.length == 1){
								ClientStart.HOST = split[0];
								ClientStart.PORT = 1024; //Default port
							}else{
								ClientStart.HOST = "localhost"; //Default host
								ClientStart.PORT = 1024; //Default port
							}
							ClientStart.con = new ClientGameSocket(ClientStart.window);
						}
					}
				}
			}
		};
	
		wrapped[7] = new ComponentWrapper(Y){
			public void action(){
				if(reset){
					if(Y.getPollData() > 0){
						ClientStart.MAIN_GUI_SHOWN = !ClientStart.MAIN_GUI_SHOWN;
					}
				}
			}
		};
		
		wrapped[8] = new ComponentWrapper(LEFT_TRIGGER){
			public void action() {
				if(LEFT_TRIGGER.getPollData() > 0 && reset){
					Game.current = (Game.current + 1) % Game.ships.length;
				}
			}
		};
		
		wrapped[9] = new ComponentWrapper(RIGHT_TRIGGER){
			public void action() {
				
				if(RIGHT_TRIGGER.getPollData() > 0 && reset){
					if(ClientStart.map.isOpen)
						ClientStart.map.sendAirstrike();
					else if(!StoreHandler.isOpen){
						EntityPlayer p = Game.world.getPlayer();
						ClientStart.con.sendPacket(new SpawnMissilePacket(p.locX, p.locY, ClientStart.angX, ClientStart.angY, Game.ownerID));
						AudioHandler.SHOT.play();
					}
				}
			}
		};
		
		wrapped[10] = new ComponentWrapper(BACK){
			public void action() {
				if(BACK.getPollData() > 0 && reset){
					if(StoreHandler.isOpen)
						StoreHandler.isOpen = false;
					else
						ClientStart.store.openStore();
					ClientStart.map.isOpen = false;
				}
			}
		};
		
		wrapped[11] = new ComponentWrapper(START){
			public void action() {
				if(START.getPollData() > 0 && reset){
					ClientStart.map.isOpen = !ClientStart.map.isOpen;
					StoreHandler.isOpen = false;
				}
			}
		};
		
		wrapped[15] = new ComponentWrapper(D_PAD){
			
			public void action() {
				//Other things that don't need to be wrapped
				float dVal = D_PAD.getPollData();
				if(reset){
					if(dVal == 1f){ //LEFT
						if(!StoreHandler.isOpen && !ClientStart.map.isOpen){
							EntityPlayer p = Game.world.getPlayer();
							p.position = (p.position + 1) % p.capacity;
						}
					}
					else if(dVal == 0.5f){ //RIGHT
						if(!StoreHandler.isOpen && !ClientStart.map.isOpen){
							EntityPlayer p = Game.world.getPlayer();
							p.position--;
							if(p.position < 0)
								p.position = p.capacity - 1;
						}
					}
					else if(dVal == 0.75f){ //DOWN
						if(StoreHandler.isOpen){
							ClientStart.store.position = (ClientStart.store.position + 1) % ClientStart.store.costs.size();
						}
					}
					else if(dVal == 0.25f){ //UP
						if(StoreHandler.isOpen){
							ClientStart.store.position--;
							if(ClientStart.store.position < 0)
								ClientStart.store.position = ClientStart.store.costs.size() - 1;
						}
					}
				}
			}
		};
		
	}
	
	public static void tick(){
		if(analog != null){
			analog.poll();
			
			//For things that need to be wrapped, execute their code
			for(ComponentWrapper cw : wrapped){
				if(cw != null){
					cw.tick();
				}
			}
			
			if(!Game.started) //Don't allow user input until game starts
				return;
			
			if(Game.world.getPlayer() != null){
				float x = LEFT_THUMB_X.getPollData();
				float y = LEFT_THUMB_Y.getPollData();
				Game.world.getPlayer().move((Math.abs(x) > 0.2f) ? x : 0, (Math.abs(y) > 0.2f) ? y : 0, ClientStart.tickDelta);
				ClientStart.con.sendPacket(new EntityPacket(Game.world.getPlayer()));
			}
			
			if(ClientStart.map.isOpen){
				float x = RIGHT_THUMB_X.getPollData();
				float y = RIGHT_THUMB_Y.getPollData();
				ClientStart.map.tick((Math.abs(x) > 0.2f) ? 2*x : 0f, (Math.abs(y) > 0.2f) ? 2*y : 0f, ClientStart.tickDelta);
			}else{
				float x = RIGHT_THUMB_X.getPollData();
				float y = RIGHT_THUMB_Y.getPollData();
				if(Math.abs(x) > 0.2 || Math.abs(y) > 0.2){
					double rad = Math.atan2(x, y);
					float dy = (float)Math.sin(rad);
					float dx = (float)Math.cos(rad);
					ClientStart.angX = dy;
					ClientStart.angY = dx;
				}
			}
			
		}
	}
	
}
