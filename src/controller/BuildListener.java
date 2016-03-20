package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

	public BuildListener(BuildGUI view, Timer visualTimer, ProjectManager pm) {
		this.view = view;
		this.visualTimer = visualTimer;
		this.pm = pm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String statusLabel = "";
		switch (e.getActionCommand()) {
		case "Close":
			view.disposeFrame();
			visualTimer.start();
			break;
		case "Ball":
			pm.setFocusedButton("Ball");
			statusLabel = "Now click anywhere inside the canvas to draw the ball";
			pm.setStatusLabel(statusLabel);
			break;
		case "Triangle":
			pm.setFocusedButton("Triangle");
			statusLabel = "Now click anywhere inside the canvas to draw the triangle";
			pm.setStatusLabel(statusLabel);
			break;
		case "Square":
			pm.setFocusedButton("Square");
			statusLabel = "Now click anywhere inside the canvas to draw the square";
			pm.setStatusLabel(statusLabel);
			break;
		case "LFlipper":
			pm.setFocusedButton("LFlipper");
			statusLabel = "Now click anywhere inside the canvas to draw the left flipper";
			pm.setStatusLabel(statusLabel);
			break;
		case "RFlipper":
			pm.setFocusedButton("RFlipper");
			statusLabel = "Now click anywhere inside the canvas to draw the right flipper";
			pm.setStatusLabel(statusLabel);
			break;
		case "Circle":
			pm.setFocusedButton("Circle");
			statusLabel = "Now click anywhere inside the canvas to draw the circle";
			pm.setStatusLabel(statusLabel);
			break;
		case "Booster":
			pm.setFocusedButton("Booster");
			statusLabel = "Now click anywhere inside the canvas to draw the booster";
			pm.setStatusLabel(statusLabel);
			break;
		case "Death Sqaure":
			pm.setFocusedButton("Death Sqaure");
			statusLabel = "Now click anywhere inside the canvas to draw the death square";
			pm.setStatusLabel(statusLabel);
			break;
		case "Teleporter":
			pm.setFocusedButton("Teleporter");
			statusLabel = "Now click anywhere inside the canvas to draw the teleporter";
			pm.setStatusLabel(statusLabel);
			break;
		case "Connect Gizmos":
			pm.setFocusedButton("Connect Gizmos");
			statusLabel = "Click on the two gizmos you want to connect";
			pm.setStatusLabel(statusLabel);
			break;
		case "Delete":
			pm.setFocusedButton("Delete");
			statusLabel = "Click on the gizmo you want to delete ";
			pm.setStatusLabel(statusLabel);
			break;
		case "Reload Board":
			pm.setFocusedButton("Reload Board");
			statusLabel = "Restoring Board...";
			pm.setStatusLabel(statusLabel);
			int confirmation1 = JOptionPane.YES_NO_OPTION;
			int result1 = JOptionPane.showConfirmDialog(null, "Are you sure you want to restore the board?",
					"Warning", confirmation1);
			if (result1 == 0) {
				pm.clearAllBoardGizmos();
				pm.restartGame();
			}
			
			break;
		case "Move":
			pm.setFocusedButton("Move");
			statusLabel = "Click the gizmo you want to move and then click on the new position";
			pm.setStatusLabel(statusLabel);
			break;
		case "Absorber":
			pm.setFocusedButton("Absorber");
			statusLabel = "Click on the two points you want to draw the absorber";
			pm.setStatusLabel(statusLabel);
			break;
		case "Disconnect Gizmos":
			pm.setFocusedButton("Disconnect Gizmos");
			statusLabel = "Click on the two gizmos you want to disconnect";
			pm.setStatusLabel(statusLabel);
			break;	
		case "Key Connect":
			pm.setFocusedButton("Key Connect");
			statusLabel = "Click on the Gizmo you want to conenct a key to; then press the keyboard key";
			pm.setStatusLabel(statusLabel);
			break;
		case "Key Disconnect":
			pm.setFocusedButton("Key Disconnect");
			statusLabel = "Click on the Gizmo you want to disconnect a key from; then press the keyboard key";
			pm.setStatusLabel(statusLabel);
			break;
		case "Rotate":
			pm.setFocusedButton("Rotate");
			statusLabel = "Click on the gizmo you want to rotate";
			pm.setStatusLabel(statusLabel);
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

	@Override
	public void stateChanged(ChangeEvent c) {

		JSlider source = (JSlider) c.getSource();

		if (source.getName() == "Gravity") {

			pm.setGravity(source.getValue());

		} else if (source.getName() == "Friction") {
			double fric = source.getValue() / 1000.0;
			pm.setFriction(fric,fric);

		}

	}
}
