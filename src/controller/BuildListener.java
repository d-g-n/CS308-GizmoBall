package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gizmos.SquareBumper;
import model.ProjectManager;
import view.Board;
import view.BuildGUI;

public class BuildListener implements ActionListener, ChangeListener {

	private BuildGUI view;
	private ProjectManager pm;
	private Timer visualTimer;
	private HashMap<String,String> gizmoMap;

	public BuildListener(BuildGUI view, Timer visualTimer, ProjectManager pm) {
		this.view = view;
		this.visualTimer = visualTimer;
		this.pm = pm;
		this.gizmoMap = new HashMap<>();
		fillMap();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String statusLabel = "";
		String action = e.getActionCommand();
		switch (action) {
		default:
			pm.setFocusedButton(action);
			pm.setStatusLabel(gizmoMap.get(action));
			break;
		case "Close":
			pm.dynamicModeOff();
			pm.setFocusedButton("");
			pm.setStatusLabel("Score: " + pm.getScore() + " HighScore " + pm.getHighScore() + " Lives: " + pm.getLives());
			view.disposeFrame();
			visualTimer.start();
			break;
		case "Dynamic Play":
			pm.dynamicModeOn();
			pm.setStatusLabel("You can now add Gizmos in real time!");
			visualTimer.start();
			break;
		case "Reload Board":
			statusLabel = "Board Restored";
			pm.setStatusLabel(statusLabel);
			int confirmation1 = JOptionPane.YES_NO_OPTION;
			int result1 = JOptionPane.showConfirmDialog(null, "Are you sure you want to restore the board?",
					"Warning", confirmation1);
			if (result1 == 0) {
				pm.clearAllBoardGizmos();
				pm.restartGame();
			}
			break;
		case "Clear Board":
			pm.setFocusedButton("Clear Board");
			statusLabel = "Clearing Board..";
			pm.setStatusLabel(statusLabel);
			int confirmation = JOptionPane.YES_NO_OPTION;
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the entire board?",
					"Warning", confirmation);
			if (result == 0) {
				pm.clearAllBoardGizmos();
			}
			break;
		}
		pm.pushVisualUpdate();
	}
	
	public void fillMap(){
		String label = "Now click anywhere inside the canvas to draw the ";
		gizmoMap.put("Ball",label + "ball");
		gizmoMap.put("Triangle", label + "triangle");
		gizmoMap.put("Square", label + "square");
		gizmoMap.put("LFlipper", label + "left flipper");
		gizmoMap.put("RFlipper", label + "right flipper");
		gizmoMap.put("Circle", label + "circle");
		gizmoMap.put("Death Square", label + "death square");
		gizmoMap.put("Teleporter", label + "teleporter");
		gizmoMap.put("Booster", label + "booster");
		gizmoMap.put("Connect Gizmos", "Click on the two gizmos you want to connect");
		gizmoMap.put("Rotate", "Click on the gizmo you want to rotate");
		gizmoMap.put("Key Connect", "Click on the Gizmo you want to connect a key to; then press the keyboard key");
		gizmoMap.put("Key Disconnect","Click on the Gizmo you want to disconnect a key from; then press the keyboard key");
		gizmoMap.put("Absorber", "Click on the two points you want to draw the absorber");
		gizmoMap.put("Disconnect Gizmos", "Click on the two gizmos you want to disconnect");
		gizmoMap.put("Delete", "Click on a gizmo to delete it");
		gizmoMap.put("Move", "Click the gizmo you want to move and then click on the new position");
		
	}

	@Override
	public void stateChanged(ChangeEvent c) {

		JSlider source = (JSlider) c.getSource();

		if (source.getName() == "Gravity") {

			pm.setGravity(source.getValue());

		} else if (source.getName() == "Friction") {

			pm.setFriction(source.getValue() / 1000.0);

		}

	}
}
