package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import controller.BuildListener;
import model.ProjectManager;

public class BuildGUI implements GBallGui {

	private JFrame frame;
	private BuildListener controller;

	public BuildGUI(Timer visualTimer,ProjectManager pm) {
		frame = new JFrame("Palette");
		controller = new BuildListener(this, visualTimer,pm);
		
	}

	public void showPalette() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(5,1));
		frame.setPreferredSize(new Dimension(200,500));
		addButtons(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}
	
	private void addButtons(Container pane){
		addAButton("Square", pane);
		addAButton("Circle", pane);
		addAButton("Triangle", pane);
		addAButton("LFlipper", pane);
		addAButton("RFlipper", pane);
		addAButton("Connect", pane);
		addAButton("Rotate",pane);
		addAButton("Close", pane);
	}
	
	private void addAButton(String title, Container pane) {
		JButton button = new JButton(title);
		button.setPreferredSize(new Dimension(100,100));
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener(controller);
		pane.add(button);
	}
	
	public void disposeFrame(){
		frame.dispose();
		frame.setVisible(false);
	}
}
