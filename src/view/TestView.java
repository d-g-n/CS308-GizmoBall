package view;

import gizmos.*;
import model.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

/**
 * purely for testing
 * Created by Declan on 06/02/2016.
 */
public class TestView {



	public TestView(){

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

		AbstractGizmo ba = new BallActor(10.5, 5.1, 0, 0, 0);

		pm.addGizmo(ba);



		JFrame testFrame = new JFrame("test panel");
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.setLayout(new BorderLayout());

		GizmoPanel gp = new GizmoPanel(pm);

		gp.setPreferredSize(new Dimension(700, 700));

		testFrame.add(gp, BorderLayout.CENTER);
		testFrame.pack();

		testFrame.setVisible(true);

		java.util.Timer updateT = new java.util.Timer();
		updateT.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				gp.repaint();


				// THIS CODE SHOULD BE IN COLLIONMANAGER OR SOMETHING, PURELY ILLUSTRATIONAL
				double xp = ba.getXpos();
				double yp = ba.getYpos();

				if(xp >= 20){
					xp = 0;
				} else{
					xp = xp + 0.05;
				}

				ba.setPos(xp, ba.getYpos());
			}
		}, 50, 50);
	}
}
