package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import model.ProjectManager;

public class RunListener implements ActionListener {

	private ProjectManager model;
	private Timer timer;

	public RunListener(ProjectManager model) {
		this.model = model;
		timer = new Timer(50, this);
		timer.start();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == timer) {
			model.moveBall();
		} else {
			switch (e.getActionCommand()) {
			case "Exit":
				System.exit(0);
				break;
			case "Stop":
				/* Kill execution; */ break;
			case "Tick":
				/* Update only one frame */ break;
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
