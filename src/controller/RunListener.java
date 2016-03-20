package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.ProjectManager;
import view.Board;
import view.BuildGUI;

/**
 * The RunListener class is the handler for the events the 
 * buttons around the board create. It also handles the time tick
 * events created by the Timer class.
 *
 */
public class RunListener implements ActionListener {

	private ProjectManager pm;
	private Timer visualTimer;

	public RunListener(ProjectManager model) {
		this.pm = model;
		visualTimer = new Timer((int) (1000 * (1/Board.FRAME_RATE)), this);
		visualTimer.start();
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		if(!pm.gameOver()){
		if (e.getSource().equals(visualTimer)){

			// for every visual update we should have physics_fps / view_fps
			// so if the server is calculating at 1000 fps and the client is drawing at
			// 50 fps we need to take 20 physics steps for every visual update to keep it looking smooth
			// and consistent

			for(int i = 0; i < (int) (Board.PHYSICS_FRAME_RATE / Board.FRAME_RATE); i++){
				pm.moveBall();
			}

			pm.pushVisualUpdate();
	
		} else {
			switch (e.getActionCommand()) {
			case "Play":
				visualTimer.start();
				break;
			case "Exit":
				System.exit(0);
				break;
			case "Stop":
				visualTimer.stop();
				break;
			case "Tick":

				// simulate one second of movement in visual time

				for(int i = 0; i < 1000*(1/Board.FRAME_RATE); i++){
					pm.moveBall();
				}

				pm.pushVisualUpdate();

				break;
			case "Restart":
				pm.clearAllBoardGizmos();
				pm.restartGame();
				break;
			case "Build Mode":
				visualTimer.stop(); 
				BuildGUI buildMode = new BuildGUI(visualTimer,pm);
				pm.setBuildModeOn(true);
				buildMode.showPalette();
				pm.pushVisualUpdate();
				break;
			case "Settings":
				/* Change the settings of the project */ break;
			case "About":
				/* Some general information about the project */ break;
			}
		}
	}else {
		
		int confirmation = JOptionPane.YES_NO_OPTION;
		int result = JOptionPane.showConfirmDialog(null, " HighScore: " + pm.getScore() + " \nDo you want to start Again?",
				"GameOver", confirmation);
		if (result == 0) {
			pm.clearAllBoardGizmos();
			pm.restartGame();
		}else {
			System.exit(0);
		}
	}
	}
}
