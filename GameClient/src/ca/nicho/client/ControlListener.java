package ca.nicho.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlListener implements KeyListener {

	private Key W = new Key();
	private Key A = new Key();
	private Key S = new Key();
	private Key D = new Key();

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyChar() == 'w'){
			W.pressed = true;
		}else if(e.getKeyChar() == 'a'){
			A.pressed = true;
		}else if(e.getKeyChar() == 's'){
			S.pressed = true;
		}else if(e.getKeyChar() == 'd'){
			D.pressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'w'){
			W.pressed = false;
		}else if(e.getKeyChar() == 'a'){
			A.pressed = false;
		}else if(e.getKeyChar() == 's'){
			S.pressed = false;
		}else if(e.getKeyChar() == 'd'){
			D.pressed = false;
		}
	}
	
	public void tick(){
		int deltaX = 0;
		int deltaY = 0;
		if(W.pressed)
			deltaY = -1;
		if(A.pressed)
			deltaX = -1;
		if(S.pressed)
			deltaY = 1;
		if(D.pressed)
			deltaX = 1;
		
		if(Game.world.getPlayer() != null){
			Game.world.getPlayer().move(deltaX, deltaY);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == 't'){
			ClientStart.DEBUG = !ClientStart.DEBUG;
		}
	}
	
	private class Key {
		
		public boolean pressed;
		
	}

}
