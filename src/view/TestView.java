package view;

import gizmos.*;
import model.ProjectManager;
import view.GizmoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * purely for testing
 * Created by Declan on 06/02/2016.
 */
public class TestView {



	public TestView(){

	}
	
	public JPanel getBoard(){

		ProjectManager pm = new ProjectManager();
		pm.addGizmo(new SquareBumper(10, 10, 1, 1, 0));
		pm.addGizmo(new SquareBumper(9, 10, 1, 1, 0));
		pm.addGizmo(new SquareBumper(11, 10, 1, 1, 0));

		pm.addGizmo(new SquareBumper(12, 9, 1, 1, 0));
		pm.addGizmo(new SquareBumper(8, 9, 1, 1, 0));

		pm.addGizmo(new SquareBumper(9, 7, 1, 1, 0));
		pm.addGizmo(new SquareBumper(11, 7, 1, 1, 0));

		pm.addGizmo(new TriangleBumper(1, 1, 1, 1, 0));
		pm.addGizmo(new TriangleBumper(2, 1, 1, 1, 90));

		pm.addGizmo(new CircularBumper(3, 1, 1, 1, 0));

		pm.addGizmo(new LeftFlipper(15, 15, 0, 0, 0));

		pm.addGizmo(new Absorber(1, 18, 18, 1, 0));



		JPanel testFrame = new JPanel();
		testFrame.setLayout(new BorderLayout());

		GizmoPanel gp = new GizmoPanel(pm);

		gp.setPreferredSize(new Dimension(700, 700));

		testFrame.add(gp, BorderLayout.CENTER);
		return testFrame;
	}
}
