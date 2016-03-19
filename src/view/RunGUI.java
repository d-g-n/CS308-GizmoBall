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

import controller.MenuFileListener;
import controller.MagicKeyListener;
import controller.RunListener;
import model.ProjectManager;

public class RunGUI implements GBallGui, Observer {

	private MagicKeyListener keyListener;
	private TestView tv;
	private RunListener runListener;
	public static final int BOARD_WIDTH = 500;
	public static final int BOARD_HEIGHT = 500;
	private JLabel statusLabel;
	private ProjectManager pm ;
	
	private void createMenuBar(Container pane){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
				"The file menu");
		menuBar.add(menu);
		
		JMenuItem fileItem1 = new JMenuItem("Save As...");
		JMenuItem fileItem2 = new JMenuItem("Load...");		
		JMenuItem fileItem3 = new JMenuItem("Exit");
		
		fileItem1.addActionListener(new MenuFileListener(pm));
		fileItem2.addActionListener(new MenuFileListener(pm));
		
		menu.add(fileItem1);
		menu.add(fileItem2);
		menu.add(fileItem3);
		pane.add(menuBar,BorderLayout.PAGE_START);
	}
	private void addComponentsToPane(Container pane, ProjectManager pm) {
		pane.setLayout(new BorderLayout());

	    JPanel leftPanel = new JPanel();
	    leftPanel.setLayout(new GridLayout(4,1));
	    leftPanel.addKeyListener(keyListener);

		addAButton("Play", leftPanel);
		addAButton("Stop", leftPanel);
		addAButton("Tick", leftPanel);
		addAButton("Restart", leftPanel);
		
		pane.add(leftPanel, BorderLayout.LINE_START);
		
		pane.add(tv.getBoard(),BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		rightPanel.addKeyListener(keyListener);
		addAButton("Build Mode", rightPanel);
		addAButton("Settings", rightPanel);
		addAButton("About", rightPanel);
		addAButton("Exit", rightPanel);
		
		pane.add(rightPanel,BorderLayout.LINE_END);
		pane.addKeyListener(keyListener);
	}
	
	private void createStatusBar(Container pane){
		statusLabel = new JLabel("Here will be the status label");
		pane.add(statusLabel,BorderLayout.PAGE_END);
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
		//frame.setPreferredSize(new Dimension(950,800));
		addComponentsToPane(frame.getContentPane(), pm);
		createMenuBar(frame.getContentPane());
		createStatusBar(frame.getContentPane());
		frame.addKeyListener(keyListener);
		frame.pack();
        frame.setVisible(true);
	}


	public RunGUI(ProjectManager pm){
		/* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException | IllegalAccessException ex) {
		}

		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui(pm);
			}
		});
		this.pm = pm;
		tv = new TestView(pm);
		keyListener = new MagicKeyListener(pm);
		runListener = new RunListener(pm);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(tv == null)
			return;
		changeStatusLabel(pm.getStatusLabel());
		tv.getGizPanel().repaint();
	}
	
	private void changeStatusLabel(String status){
		if(statusLabel != null)
			statusLabel.setText(status);
	}
}
