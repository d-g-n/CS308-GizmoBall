package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import view.BuildGUI;

public class BuildListener implements ActionListener{

	private BuildGUI view;

	private Timer visualTimer;
	
	public BuildListener(BuildGUI view, Timer visualTimer){
		this.view = view;
		this.visualTimer = visualTimer;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Close")){
			view.disposeFrame();
			visualTimer.start();
		}
	}

   }
