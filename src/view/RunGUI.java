package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.MagicKeyListener;
import controller.MenuListener;
import controller.RunListener;
import model.ProjectManager;

public class RunGUI implements GBallGui, Observer {

	private MagicKeyListener keyListener;
	private TestView tv;
	private RunListener runListener;
	private MenuListener menuListener;
	public static final int BOARD_WIDTH = 500;
	public static final int BOARD_HEIGHT = 500;

	public RunGUI(ProjectManager pm) {
		/* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException
				| IllegalAccessException ex) {
		}

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui(pm);
			}
		});
		tv = new TestView(pm);
		keyListener = new MagicKeyListener(pm);
		runListener = new RunListener(pm);
		menuListener = new MenuListener(pm);
	}

	private void createMenuBar(Container pane) {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The file menu");
		menuBar.add(menu);
		addMenuItem("Load...", menu);
		addMenuItem("Exit", menu);
		pane.add(menuBar, BorderLayout.PAGE_START);
	}

	private void addMenuItem(String title, JMenu menu) {
		JMenuItem menuItem = new JMenuItem(title);
		menu.add(menuItem);
		menuItem.addActionListener(menuListener);
	}

	private void addComponentsToPane(Container pane, ProjectManager pm) {
		pane.setLayout(new BorderLayout());

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4, 1));
		leftPanel.addKeyListener(keyListener);

		addAButton("Play", leftPanel);
		addAButton("Stop", leftPanel);
		addAButton("Tick", leftPanel);
		addAButton("Exit", leftPanel);

		pane.add(leftPanel, BorderLayout.LINE_START);

		pane.add(tv.getBoard(), BorderLayout.CENTER);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.addKeyListener(keyListener);
		addAButton("Build Mode", rightPanel);
		addAButton("Settings", rightPanel);
		addAButton("About", rightPanel);

		pane.add(rightPanel, BorderLayout.LINE_END);
		pane.addKeyListener(keyListener);
	}

	private void createStatusBar(Container pane) {
		JLabel label = new JLabel("Here will be the status label");
		pane.add(label, BorderLayout.PAGE_END);
		pane.addKeyListener(keyListener);

	}

	private void addAButton(String title, Container pane) {
		JButton button = new JButton(title);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener(runListener);
		button.addKeyListener(keyListener);
		pane.add(button);

	}

	private void createAndShowGui(ProjectManager pm) {
		JFrame frame = new JFrame("Gizmoball");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setPreferredSize(new Dimension(950,800));
		addComponentsToPane(frame.getContentPane(), pm);
		createMenuBar(frame.getContentPane());
		createStatusBar(frame.getContentPane());
		frame.addKeyListener(keyListener);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (tv == null)
			return;
		tv.getGizPanel().repaint();
	}
}
