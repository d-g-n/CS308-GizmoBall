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

		Map<CommandEnums, JPanel> categoryMap = new HashMap<>();

		for(CommandEnums cat : CommandEnums.values()){
			JPanel category = addSection(cat.getCategory(), pane);
			categoryMap.put(cat, category);
		}

		for(Map.Entry<String, CommandMapper.Command> comMap : CommandMapper.getCommandMap().entrySet()){
			CommandMapper.Command c = comMap.getValue();
			addAButton(comMap.getKey(), c.getPrettyName(), new ImageIcon(c.getIconPath()), categoryMap.get(c.getCategory()));
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
