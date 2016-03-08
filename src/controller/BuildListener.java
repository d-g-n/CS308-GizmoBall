package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import gizmos.SquareBumper;
import model.ProjectManager;
import view.Board;
import view.BuildGUI;

public class BuildListener implements ActionListener{

	private BuildGUI view;
	private ProjectManager pm;
	private Timer visualTimer;
	public BuildListener(BuildGUI view, Timer visualTimer, ProjectManager pm){
		this.view = view;
		this.visualTimer = visualTimer;
		this.pm = pm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Close")){
			view.disposeFrame();
			visualTimer.start();
		}else if(e.getActionCommand().equals("Triangle")){
			pm.setFocusedButton("Triangle");
		}else if(e.getActionCommand().equals("Square")){
			pm.setFocusedButton("Square");
		}
	}
   }
