package view;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class RunGUI implements GBallGui {

	
	private static void createMenuBar(Container pane){
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
	private static void addComponentsToPane(Container pane) {
		pane.setLayout(new BorderLayout());
		
	    JPanel leftPanel = new JPanel();
	    leftPanel.setLayout(new GridLayout(4,1));
	    
		addAButton("Play", leftPanel);
		addAButton("Stop", leftPanel);
		addAButton("Tick", leftPanel);
		addAButton("Exit", leftPanel);
		
		pane.add(leftPanel,BorderLayout.LINE_START);
		
		TestView t = new TestView();
		pane.add(t.getBoard(),BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3,1));
		addAButton("Build Mode", rightPanel);
		addAButton("Settings", rightPanel);
		addAButton("About", rightPanel);
		
		pane.add(rightPanel,BorderLayout.LINE_END);
	}
	
	private static void createStatusBar(Container pane){
		JLabel label = new JLabel("Here will be the status label");
		pane.add(label,BorderLayout.PAGE_END);
		
	}

	private static void addAButton(String title, Container pane) {
		JButton button = new JButton(title);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		pane.add(button);
		
	}

	private static void createAndShowGui() {
		JFrame frame = new JFrame("Gizmoball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600,500));
		addComponentsToPane(frame.getContentPane());
		createMenuBar(frame.getContentPane());
		createStatusBar(frame.getContentPane());
		frame.pack();
        frame.setVisible(true);
	}

	public static void main(String[] args){
		/* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
	}
}
