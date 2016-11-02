package ca.nicho.client;

import ca.nicho.client.packet.SpawnEntityPacket;
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
				//Shoot missile
				if(A.getPollData() > 0 && reset){
					ClientStart.con.sendPacket(new SpawnEntityPacket(Game.world.getPlayer().locX + 20, Game.world.getPlayer().locY, SpriteSheet.ENTITY_MISSILE, Game.ownerID));
				}			
			}
		};
		
		wrapped[15] = new ComponentWrapper(D_PAD){
			
			public void action() {
				//Other things that don't need to be wrapped
				float dVal = D_PAD.getPollData();
				if(dVal == 1f)
					Game.current = (Game.current - 1 > 0) ? Game.current - 1 : Game.ships.length - 1;
				else if(dVal == 0.5f)
					Game.current = (Game.current + 1) % Game.ships.length;
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
			
			
			if(Game.world.getPlayer() != null){
				float x = RIGHT_THUMB_X.getPollData();
				float y = RIGHT_THUMB_Y.getPollData();
				Game.world.getPlayer().move((Math.abs(x) > 0.2f) ? x : 0, (Math.abs(y) > 0.2f) ? y : 0);
			}
			
		}
	}
	
}
