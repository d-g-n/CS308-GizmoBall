package view;

import java.awt.*;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controller.BuildListener;
import model.ProjectManager;

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
		// frame.setPreferredSize(new Dimension(600, 625));
		addComponents(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	private void addComponents(Container pane) {

		JPanel gizmoSection = addSection("Add Gizmos", pane);

		JPanel commandsSection = addSection("Commands", pane);

		JPanel settingsSection = addSection("Settings", pane);

		addAButton("Ball", new ImageIcon("icons/gizmos/ball.png"), gizmoSection);
		addAButton("Square", new ImageIcon("icons/gizmos/square.png"), gizmoSection);
		addAButton("Circle", new ImageIcon("icons/gizmos/circle.png"), gizmoSection);
		addAButton("Triangle", new ImageIcon("icons/gizmos/triangle.png"), gizmoSection);
		addAButton("Absorber", new ImageIcon("icons/gizmos/absorber.png"), gizmoSection);
		addAButton("LFlipper", new ImageIcon("icons/gizmos/leftflipper.png"), gizmoSection);
		addAButton("RFlipper", new ImageIcon("icons/gizmos/rightflipper.png"), gizmoSection);
		addAButton("Booster", new ImageIcon("icons/gizmos/booster.png"), gizmoSection);
		addAButton("Death Sqaure", new ImageIcon("icons/gizmos/deathsquare.png"), gizmoSection);
		addAButton("Teleporter", new ImageIcon("icons/gizmos/teleporter.png"), gizmoSection);

		addAButton("Move", new ImageIcon("icons/commands/move.png"), commandsSection);
		addAButton("Rotate", new ImageIcon("icons/commands/rotate.png"), commandsSection);
		addAButton("Delete", new ImageIcon("icons/commands/delete.png"), commandsSection);
		addAButton("Clear Board", new ImageIcon("icons/commands/clear.png"), commandsSection);
		addAButton("Reload Board", new ImageIcon("icons/commands/reload.png"), commandsSection);
		addAButton("Connect Gizmos", new ImageIcon("icons/commands/connect.png"), commandsSection);
		addAButton("Disconnect Gizmos", new ImageIcon("icons/commands/disconnect.png"), commandsSection);
		addAButton("Key Connect", new ImageIcon("icons/commands/keyconnect.png"), commandsSection);
		addAButton("Key Disconnect", new ImageIcon("icons/commands/keydisconnect.png"), commandsSection);
		addAButton("Dynamic Play", new ImageIcon("icons/commands/play.png"), commandsSection);

		addGravitySlider(0, 100, 25, settingsSection);
		addFrictionSlider(0, 100, 25, settingsSection);
	}

	private void addAButton(String title, Icon icon, Container pane) {
		JButton button = new JButton(title);
		button.setIcon(icon);
		button.setVerticalTextPosition(SwingConstants.TOP);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		button.setPreferredSize(new Dimension(100, 100));
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener(controller);
		pane.add(button);
	}

	private JPanel addSection(String sectionName, Container pane) {

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

	public void disposeFrame() {
		frame.dispose();
		frame.setVisible(false);
	}
}
