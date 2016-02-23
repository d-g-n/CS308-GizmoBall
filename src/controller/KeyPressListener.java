package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import model.ProjectManager;
import physics.Vect;
import view.Board;

public class KeyPressListener implements KeyListener {
	
	private ProjectManager pm;

	public KeyPressListener(ProjectManager model) {
		this.pm = model;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(pm.getBall().stopped()) {
			Vect v = new Vect(0,-(50 * (Board.BOARD_HEIGHT / Board.X_CELLS)));
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
