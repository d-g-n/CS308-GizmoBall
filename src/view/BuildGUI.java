package view;

import java.awt.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controller.BuildListener;
import controller.CommandMapper;
import model.ProjectManager;

/**
 * The BuildGUI class creates a window for the palette of the build mode.
 *
 */
public class BuildGUI {

	private JFrame frame;
	private BuildListener controller;

	public BuildGUI(Timer visualTimer, ProjectManager pm) {
		frame = new JFrame("Build Mode");
		controller = new BuildListener(this, visualTimer, pm);
		frame.addWindowListener(controller);

	}

	public void showPalette() {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		//frame.setPreferredSize(new Dimension(700, 650));
		addComponents(frame.getContentPane());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void addComponents(Container pane) {

		Map<CommandEnums, JPanel> categoryMap = new HashMap<>();

		for (CommandEnums cat : CommandEnums.values()) {
			JPanel category = addSection(cat.getCategory(), pane);
			categoryMap.put(cat, category);
		}

		for (Map.Entry<String, CommandMapper.Command> comMap : CommandMapper.getCommandMap().entrySet()) {
			CommandMapper.Command c = comMap.getValue();
			addAButton(comMap.getKey(), c.getPrettyName(), new ImageIcon(c.getIconPath()),
					categoryMap.get(c.getCategory()));
		}

		addGravitySlider(0, 100, 25, categoryMap.get(CommandEnums.CATEGORY_OTHER));
		addFrictionSlider(0, 100, 25, categoryMap.get(CommandEnums.CATEGORY_OTHER));

	}

	private void addAButton(String actionCommand, String title, Icon icon, Container pane) {
		JButton button = new JButton(title);
		button.setIcon(icon);
		button.setVerticalTextPosition(SwingConstants.TOP);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setActionCommand(actionCommand);

		button.setPreferredSize(new Dimension(100, 100));
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener(controller);
		pane.add(button);
	}

	private JPanel addSection(String sectionName, Container pane) {

		JPanel sectPan = new JPanel(new GridLayout(2, 3));

		sectPan.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.black), sectionName));

		pane.add(sectPan);

		return sectPan;
	}

	private void addGravitySlider(int min, int max, int initial, Container pane) {

		JSlider slider = new JSlider(min, max, initial);
		slider.setName("Gravity");
		slider.addChangeListener(controller);


		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("None (0)"));
		labelTable.put(new Integer(25), new JLabel("Default (25)"));
		labelTable.put(new Integer(50), new JLabel("High (50)"));
		labelTable.put(new Integer(75), new JLabel("Very High (75)"));
		labelTable.put(new Integer(100), new JLabel("Extreme (100)"));
		slider.setLabelTable(labelTable);
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(25);
		slider.setSnapToTicks(true);

		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		JPanel sectPan = new JPanel(new BorderLayout());

		sectPan.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.black), "Gravity"));

		sectPan.add(slider);

		pane.add(sectPan);

	}

	private void addFrictionSlider(int min, int max, int initial, Container pane) {
		JSlider slider = new JSlider(min, max, initial);
		slider.setName("Friction");
		slider.addChangeListener(controller);


		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("None (0)"));
		labelTable.put(new Integer(25), new JLabel("Default (0.025)"));
		labelTable.put(new Integer(50), new JLabel("High (0.050)"));
		labelTable.put(new Integer(75), new JLabel("Very High (0.075)"));
		labelTable.put(new Integer(100), new JLabel("Extreme (0.10)"));
		slider.setLabelTable(labelTable);
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(25);
		slider.setSnapToTicks(true);

		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		JPanel sectPan = new JPanel(new BorderLayout());

		sectPan.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.black), "Friction"));

		sectPan.add(slider);

		pane.add(sectPan);

	}

	public void disposeFrame() {
		frame.dispose();
		frame.setVisible(false);
	}
}
