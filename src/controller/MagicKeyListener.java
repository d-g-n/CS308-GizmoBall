package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.ProjectManager;

public class MagicKeyListener implements KeyListener {
	private ProjectManager pm;
	
	public MagicKeyListener(ProjectManager pm){
		this.pm = pm;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT :

			break;
		case KeyEvent.VK_RIGHT : 

			break;
		}
	}
		
	@Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode(); 	
		switch(key){
		case KeyEvent.VK_LEFT :

			break;
		case KeyEvent.VK_RIGHT : 

			break;
			
		}
	}


}
