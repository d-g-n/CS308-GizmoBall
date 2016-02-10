package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.RunGUI;

public class RunListener implements ActionListener{

	private RunGUI gui;
	
	public RunListener(RunGUI gui){
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Exit": System.exit(0); break;
		}
	}
   
   }
