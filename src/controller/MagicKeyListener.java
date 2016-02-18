package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT :
			angle = 0;
			while(angle < 90){
				angle += 2;
			pm.updateFlipper("left",angle);
			System.out.println(angle);
			}
			break;
		case KeyEvent.VK_RIGHT : 
			angle = 0;
			while(angle < 90){
				angle += 2;
			pm.updateFlipper("right",-angle);
			System.out.println(-angle);
			}
			break;
			
		}
	}
		
	

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT :
			angle = 0;
			while(angle < 90){
				angle+= 2;
				System.out.println(-angle);
				pm.updateFlipper("left",-angle);
				
			}
			break;
		case KeyEvent.VK_RIGHT : 
			angle = 0;
			while(angle < 90){
				angle += 2;
			pm.updateFlipper("right",angle);
			System.out.println(angle);
			}
			break;
			
		}
	}


}
