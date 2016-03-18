package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ProjectManager;
import view.FileManagerGUI;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

//Implements ActionListener unless a different listener is needed. 
public class MenuFileListener implements ActionListener {
	FileManagerGUI fmGUI;
	ProjectManager pm;

	public MenuFileListener(ProjectManager pm) {
		this.pm = pm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fmGUI = new FileManagerGUI();
		String filePath = fmGUI.getLoadFilePath();
		if (!filePath.isEmpty()) {
			pm.clearAllBoardGizmos();
			pm.loadFile(filePath);
		}
	}
}
