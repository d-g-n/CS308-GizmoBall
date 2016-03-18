package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;

import controller.BuildListener;
import model.ProjectManager;

public class BuildGUI implements GBallGui {

	private JFrame frame;
	private BuildListener controller;

	public BuildGUI(Timer visualTimer, ProjectManager pm) {
		frame = new JFrame("Palette");
		controller = new BuildListener(this, visualTimer, pm);

	}

	public void showPalette() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(5, 1));
		frame.setPreferredSize(new Dimension(700, 500));
		addButtons(frame.getContentPane());
		addSliders(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	private void addButtons(Container pane) {
		addAButton("Square", pane);
		addAButton("Circle", pane);
		addAButton("Triangle", pane);
		addAButton("LFlipper", pane);
		addAButton("RFlipper", pane);
		addAButton("Absorber", pane);
		addAButton("Booster", pane);
		addAButton("Death Sqaure", pane);
		addAButton("Teleporter", pane);
		addAButton("Move", pane);
		addAButton("Rotate", pane);
		addAButton("Delete", pane);
		addAButton("Clear Board", pane);
		addAButton("Connect Gizmos", pane);
		addAButton("Disconnect Gizmos", pane);
		addAButton("Key Connect", pane);
		addAButton("Key Disconnect", pane);
		addAButton("Close", pane);
	}

	private void addSliders(Container pane) {
		addGravitySlider(0, 100, 25, pane);
		addFrictionSlider(0, 100, 25, pane);
	}

	private void addGravitySlider(int min, int max, int initial, Container pane) {

		JSlider slider = new JSlider(min, max, initial);
		slider.setName("Gravity");
		slider.addChangeListener(controller);
		pane.add(slider);


		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("None"));
		labelTable.put(new Integer(50), new JLabel("Gravity"));
		labelTable.put(new Integer(100), new JLabel("Very High"));
		slider.setLabelTable(labelTable);
		slider.setMajorTickSpacing(25);

		slider.setPaintLabels(true);
		slider.setPaintTicks(true);

	}

	private void addFrictionSlider(int min, int max, int initial, Container pane) {
		JSlider slider = new JSlider(min, max, initial);
		slider.setName("Friction");
		slider.addChangeListener(controller);
		pane.add(slider);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("None"));
		labelTable.put(new Integer(50), new JLabel("Friction"));
		labelTable.put(new Integer(100), new JLabel("Very High"));
		slider.setLabelTable(labelTable);
		slider.setMajorTickSpacing(25);

		slider.setPaintLabels(true);
		slider.setPaintTicks(true);

	}

	private void addAButton(String title, Container pane) {
		JButton button = new JButton(title);
		button.setPreferredSize(new Dimension(100, 100));
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener(controller);
		pane.add(button);
	}

	public void disposeFrame() {
		frame.dispose();
		frame.setVisible(false);
	}
}
