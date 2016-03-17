package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		}
		pm.pushVisualUpdate();
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
