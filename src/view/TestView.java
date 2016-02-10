package view;

import gizmos.*;
import model.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

/**
 * purely for testing
 * Created by Declan on 06/02/2016.
 */
public class TestView  {

	GizmoPanel gp;

	public TestView(ProjectManager pm){

		gp = new GizmoPanel(pm);

		gp.setPreferredSize(new Dimension(700, 700));


	}

	public GizmoPanel getGizPanel(){
		return gp;
	}

	public JPanel getBoard(){
		JPanel testFrame = new JPanel();
		testFrame.setLayout(new BorderLayout());
		testFrame.add(gp, BorderLayout.CENTER);

		return testFrame;
	}

}
