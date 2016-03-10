package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ProjectManager;
import view.FileManagerGUI;

//Implements ActionListener unless a different listener is needed. 
public class MenuListener implements ActionListener {
	FileManagerGUI fmGUI;
	ProjectManager pm;

	public MenuListener(ProjectManager pm) {
		this.pm = pm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Load...":
			fmGUI = new FileManagerGUI();
			String filePath = fmGUI.getLoadFilePath();
			if (!filePath.isEmpty()) {
				pm.clearAllBoardGizmos();
				pm.loadFile(filePath);
			}
			break;
		case "Exit":
			System.exit(0);
		}

	}

}
