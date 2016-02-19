package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import model.ProjectManager;

public class MagicKeyListener implements KeyListener {
	private ProjectManager pm;
	
	int angle = 0;
	
	public MagicKeyListener(ProjectManager pm){
		this.pm = pm;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		System.out.println("Pressed");
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT :
			angle = 0;
			while(angle < 3){
			pm.updateFlipper("left",-30);
			angle++;
			//System.out.println(angle);
			}
			break;
		case KeyEvent.VK_RIGHT : 
			angle = 0;
			while(angle < 3){
			pm.updateFlipper("right",30);
			angle++;
			}
			break;
			
		}
	}
		
	@Override
	public void keyReleased(KeyEvent e) {
		
		
		System.out.println("released");
		int key = e.getKeyCode(); 
		switch(key){
		case KeyEvent.VK_LEFT :
			angle = 0;
			while(angle < 3){
				pm.updateFlipper("left",30);
				angle++;
			}
			break;
		case KeyEvent.VK_RIGHT : 
			int angle = 0;
			while(angle < 3){
			pm.updateFlipper("right",-30);
			angle++;
			}
			break;
			
		}
	}


}
