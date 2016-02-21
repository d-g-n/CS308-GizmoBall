package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import model.ProjectManager;
import physics.Vect;

public class KeyPressListener implements KeyListener {
	
	private ProjectManager pm;

	public KeyPressListener(ProjectManager model) {
		this.pm = model;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(pm.getBall().stopped()) {
			Vect v = new Vect(-50,-485);
			pm.getBall().start();
			pm.getBall().setVelocity(v);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
