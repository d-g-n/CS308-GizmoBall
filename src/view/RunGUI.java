package view;

import model.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class RunGUI implements GBallGui, Observer {

	TestView tv;
	
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
	    
		addAButton("Play", leftPanel);
		addAButton("Stop", leftPanel);
		addAButton("Tick", leftPanel);
		addAButton("Exit", leftPanel);
		
		pane.add(leftPanel, BorderLayout.LINE_START);
		
		tv = new TestView(pm);
		pane.add(tv.getBoard(),BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3,1));
		addAButton("Build Mode", rightPanel);
		addAButton("Settings", rightPanel);
		addAButton("About", rightPanel);
		
		pane.add(rightPanel,BorderLayout.LINE_END);
	}
	
	private void createStatusBar(Container pane){
		JLabel label = new JLabel("Here will be the status label");
		pane.add(label,BorderLayout.PAGE_END);
		
	}

	private void addAButton(String title, Container pane) {
		JButton button = new JButton(title);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		pane.add(button);
		
	}

	private void createAndShowGui(ProjectManager pm) {
		JFrame frame = new JFrame("Gizmoball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setPreferredSize(new Dimension(950,800));
		addComponentsToPane(frame.getContentPane(), pm);
		createMenuBar(frame.getContentPane());
		createStatusBar(frame.getContentPane());
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
	}

	@Override
	public void update(Observable o, Object arg) {
		if(tv == null)
			return;

		tv.getGizPanel().repaint();
	}
}
