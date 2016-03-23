package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controller.MenuFileListener;
import controller.MagicKeyListener;
import controller.RunListener;
import model.ProjectManager;

public class RunGUI implements Observer {

	private MagicKeyListener keyListener;
	private RunBoardWrapper tv;
	private RunListener runListener;
	public static final int BOARD_WIDTH = 500;
	public static final int BOARD_HEIGHT = 500;
	private JLabel statusLabel;
	private ProjectManager pm;
	private JFrame mainFrame;

	private void createMenuBar(Container pane) {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The file menu");
		menuBar.add(menu);

		JMenuItem fileItem1 = new JMenuItem("Save As...");
		JMenuItem fileItem2 = new JMenuItem("Load...");
		JMenuItem fileItem3 = new JMenuItem("Exit");

		fileItem1.addActionListener(new MenuFileListener(pm));
		fileItem2.addActionListener(new MenuFileListener(pm));
		fileItem3.addActionListener(new MenuFileListener(pm));

		menu.add(fileItem1);
		menu.add(fileItem2);
		menu.add(fileItem3);
		pane.add(menuBar, BorderLayout.PAGE_START);
	}

	private void addComponentsToPane(Container pane, ProjectManager pm) {
		pane.setLayout(new BorderLayout());

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1));
		leftPanel.addKeyListener(keyListener);

		addAButton("Play", new ImageIcon("icons/commands/play.png"), leftPanel);
		addAButton("Stop", new ImageIcon("icons/main/stop.png"), leftPanel);
		addAButton("Tick", new ImageIcon("icons/main/tick.png"), leftPanel);

		pane.add(leftPanel, BorderLayout.LINE_START);

		pane.add(tv.getBoard(), BorderLayout.CENTER);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.addKeyListener(keyListener);
		addAButton("Build Mode", new ImageIcon("icons/main/build.png"), rightPanel);
		//addAButton("Settings", new ImageIcon("icons/main/settings.png"), rightPanel);
		JButton aboutPan = addAButton("About", new ImageIcon("icons/main/about.png"), rightPanel);
		addAButton("Exit", new ImageIcon("icons/main/exit.png"), rightPanel);
		pane.add(rightPanel, BorderLayout.LINE_END);
		pane.addKeyListener(keyListener);

		aboutPan.addActionListener(e -> {
			JOptionPane.showMessageDialog(pane,
							"This is group WK4s' CS308 Gizmoball project, we hope you enjoy using it!\n" +
							"Created by the painstaking labour of Declan Neilson, Eddie Wilkie, Jamie Cribbes, George Cassels and Giorgos Georgiadis"
			);
		});
	}

	private void createStatusBar(Container pane) {
		statusLabel = new JLabel("Score: " + pm.getScore());
		pane.add(statusLabel, BorderLayout.SOUTH);
		pane.addKeyListener(keyListener);
	}

	private JButton addAButton(String title, Icon icon, Container pane) {
		JButton button = new JButton(title);
		button.setIcon(icon);
		button.setVerticalTextPosition(SwingConstants.TOP);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener(runListener);
		button.addKeyListener(keyListener);
		pane.add(button);

		return button;
	}

	private void createAndShowGui(ProjectManager pm) {
		mainFrame = new JFrame("Gizmoball");
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// frame.setPreferredSize(new Dimension(950,800));
		addComponentsToPane(mainFrame.getContentPane(), pm);
		createMenuBar(mainFrame.getContentPane());
		createStatusBar(mainFrame.getContentPane());
		mainFrame.addKeyListener(keyListener);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public RunGUI(ProjectManager pm) {
		/* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException
				| IllegalAccessException ex) {
		}

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui(pm));
		this.pm = pm;
		tv = new RunBoardWrapper(pm);
		keyListener = new MagicKeyListener(pm);
		runListener = new RunListener(pm);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (tv == null)
			return;

		if(mainFrame != null)
			mainFrame.setTitle("Gizmoball - " + (pm.getCurrentBoard() != null ? pm.getCurrentBoard() + " - " : "") + (pm.getTimer().isRunning() ? "RUNNING" : "PAUSED"));

		changeStatusLabel(pm.getStatusLabel());
		tv.getGizPanel().repaint();
	}

	private void changeStatusLabel(String status) {
		if (statusLabel != null)
			statusLabel.setText(status);
	}
}
