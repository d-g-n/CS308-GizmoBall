package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import model.ProjectManager;

public class RunListener implements ActionListener {

	private ProjectManager pm;
	private Timer timer;

	public RunListener(ProjectManager model) {
		this.pm = model;
		timer = new Timer(25, this);
		timer.start();
	}

	
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == timer) {
			pm.moveBall();
			pm.updateFlipper("right");
			pm.updateFlipper("left");
			
	
		} else {
			switch (e.getActionCommand()) {
			case "Play":
				timer.start();break;
			case "Exit":
				System.exit(0);
				break;
			case "Stop":
				timer.stop(); break;
			case "Tick":
				pm.moveBall(); break;
			case "Build Mode":
				/* Switch to build mode view */ break;
			case "Settings":
				/* Change the settings of the project */ break;
			case "About":
				/* Some general information about the project */ break;
			}
		}
	}

}
