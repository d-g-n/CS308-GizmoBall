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
		switch (e.getActionCommand()) {
		case "Close":
			view.disposeFrame();
			visualTimer.start();
			break;
		case "Triangle":
			pm.setFocusedButton("Triangle");
			break;
		case "Square":
			pm.setFocusedButton("Square");
			break;
		case "LFlipper":
			pm.setFocusedButton("LFlipper");
			break;
		case "RFlipper":
			pm.setFocusedButton("RFlipper");
			break;
		case "Circle":
			pm.setFocusedButton("Circle");
			break;
		case "Connect Gizmos":
			pm.setFocusedButton("Connect Gizmos");
			break;
		case "Delete":
			pm.setFocusedButton("Delete");
			break;
		case "Move":
			pm.setFocusedButton("Move");
			break;
		case "Absorber":
			pm.setFocusedButton("Absorber");
			break;
		case "Disconnect Gizmos":
			pm.setFocusedButton("Disconnect Gizmos");
			break;
		case "Rotate":
			pm.setFocusedButton("Rotate");
			break;
		}
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
