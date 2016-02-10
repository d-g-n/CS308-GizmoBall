package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.RunGUI;

public class RunListener implements ActionListener {

	private RunGUI gui;

	public RunListener(RunGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
