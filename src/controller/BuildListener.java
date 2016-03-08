package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
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
		}else if(e.getActionCommand().equals("Circle")){
			pm.setFocusedButton("Circle");
		} else if(e.getActionCommand().equals("LFlipper")){
			pm.setFocusedButton("LFlipper");
		}else if(e.getActionCommand().equals("RFlipper")){
			pm.setFocusedButton("RFlipper");
		}else if(e.getActionCommand().equals("Add Ball")){
			pm.setFocusedButton("Add Ball");
		}else if(e.getActionCommand().equals("Add Absorber")){
			pm.setFocusedButton("Add Absorber");
		}else if(e.getActionCommand().equals("Clear Board")){
			
			// this might be bad.. is this considered view stuff in the controller? Or is a JOptionPane okay..
			int confirmation = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog (null, "Are you sure you want to clear the entire board?","Warning",confirmation);

            if (result == 0) {
			pm.setFocusedButton("Clear Board");
			pm.clearAllBoardGizmos();
			pm.pushVisualUpdate();
            }
		}
	}
   }
