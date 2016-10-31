package ca.nicho.client;

import net.java.games.input.Component;

public abstract class ComponentWrapper {
	
	public Component c;
	public float polled;
	boolean reset = true;
	
	public ComponentWrapper(Component comp){
		this.c = comp;
	}
	
	public void tick(){
		polled = c.getPollData();
		if(polled == 0)
			reset = true;
		action();
		if(polled != 0)
			reset = false;
	}
	
	public abstract void action();
	
}