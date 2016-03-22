package view;

import java.awt.*;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controller.BuildListener;
import model.ProjectManager;

/**
 * The BuildGUI class creates a window for the palette of the build mode.
 *
 */
public class BuildGUI implements GBallGui {

	private JFrame frame;
	private BuildListener controller;

	public BuildGUI(Timer visualTimer, ProjectManager pm) {
		frame = new JFrame("Palette");
		controller = new BuildListener(this, visualTimer, pm);
		frame.addWindowListener(controller);

	}

	public void showPalette() {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		//frame.setPreferredSize(new Dimension(600, 625));
		addComponents(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}



	private void addComponents(Container pane) {

		JPanel gizmoSection = addSection("Add Gizmos", pane);

		JPanel commandsSection = addSection("Commands", pane);

		JPanel settingsSection = addSection("Settings", pane);


		addAButton("Ball", gizmoSection);
		addAButton("Square", gizmoSection);
		addAButton("Circle", gizmoSection);
		addAButton("Triangle", gizmoSection);
		addAButton("LFlipper", gizmoSection);
		addAButton("RFlipper", gizmoSection);
		addAButton("Absorber", gizmoSection);
		addAButton("Booster", gizmoSection);
		addAButton("Death Sqaure", gizmoSection);
		addAButton("Teleporter", gizmoSection);

		addAButton("Move", commandsSection);
		addAButton("Rotate", commandsSection);
		addAButton("Delete", commandsSection);
		addAButton("Clear Board", commandsSection);
		addAButton("Connect Gizmos", commandsSection);
		addAButton("Disconnect Gizmos", commandsSection);
		addAButton("Key Connect", commandsSection);
		addAButton("Key Disconnect", commandsSection);
		addAButton("Reload Board", commandsSection);
		addAButton("Dynamic Play",commandsSection);

		addGravitySlider(0, 100, 25, settingsSection);
		addFrictionSlider(0, 100, 25, settingsSection);
	}

	private JPanel addSection(String sectionName, Container pane){

		JPanel sectPan = new JPanel(new GridLayout(2, 5));

		sectPan.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.black), sectionName));

		pane.add(sectPan);

		return sectPan;
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
