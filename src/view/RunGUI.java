package view;

import controller.MagicKeyListener;
import controller.RunListener;
import model.ProjectManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class RunGUI implements GBallGui, Observer {

	private TestView tv;
	private RunListener runListener;
	private MagicKeyListener keyListener;
	public static final int BOARD_WIDTH = 1000;
	public static final int BOARD_HEIGHT = 1000;
	
	private void createMenuBar(Container pane){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
				"The file menu");
		menuBar.add(menu);
		JMenuItem fileItem1 = new JMenuItem("Load...");
		JMenuItem fileItem2 = new JMenuItem("Exit");
		menu.add(fileItem1);
		menu.add(fileItem2);
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
		addAButton("Exit", leftPanel);
		
		pane.add(leftPanel, BorderLayout.LINE_START);
		
		tv = new TestView(pm);
		pane.add(tv.getBoard(),BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3,1));
		rightPanel.addKeyListener(keyListener);
		addAButton("Build Mode", rightPanel);
		addAButton("Settings", rightPanel);
		addAButton("About", rightPanel);
		
		pane.add(rightPanel,BorderLayout.LINE_END);
		pane.addKeyListener(keyListener);
	}
	
	private void createStatusBar(Container pane){
		JLabel label = new JLabel("Here will be the status label");
		pane.add(label,BorderLayout.PAGE_END);
		pane.addKeyListener(keyListener);
		
	}

	private void addAButton(String title, Container pane) {
		JButton button = new JButton(title);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener(runListener);
		button.addKeyListener(keyListener);
		pane.add(button);
		pane.addKeyListener(keyListener);
		
	}

	private void createAndShowGui(ProjectManager pm) {
		JFrame frame = new JFrame("Gizmoball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(710,600));
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
		
		runListener = new RunListener(this);
		keyListener = new MagicKeyListener(pm);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(tv == null)
			return;
		tv.getGizPanel().repaint();
	}
}
