package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ProjectManager;
import view.FileManagerGUI;

/**
 * The MenuFileListener handles the events on the menu bar of the application
 * which include file loading and saving buttons.
 *
 */
public class MenuFileListener implements ActionListener {
	FileManagerGUI fmGUI;
	ProjectManager pm;

	public MenuFileListener(ProjectManager pm) {
		this.pm = pm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Save As...":
			fileSave();
			break;

		case "Load...":
			fileLoad();
			break;
		}
	}

	public void fileSave() {
		fmGUI = new FileManagerGUI();
		String filePath = fmGUI.getSaveFilePath();
		if (filePath!= null && !filePath.isEmpty()) {
			pm.saveAs(filePath);
		}
	}

	public void fileLoad() {
		fmGUI = new FileManagerGUI();
		String filePath = fmGUI.getLoadFilePath();
		if (filePath!= null && !filePath.isEmpty()) {
			pm.clearAllBoardGizmos();
			pm.loadFile(filePath);
		}
	}
}
