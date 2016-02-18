import java.util.TimerTask;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.BallActor;
import gizmos.CircularBumper;
import gizmos.LeftFlipper;
import gizmos.OuterWall;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import model.ProjectManager;
import physics.Angle;
import physics.Vect;
import view.RunGUI;

/**
 * Created by gkb13160 on 10/02/16.
 */
public class MainInit {
	public static void main(String[] args) {

		int totalWidth = 20;
		// init model
		ProjectManager pm = new ProjectManager();

		pm.addGizmo(new OuterWall(0, 0, totalWidth, 0, 0));
		pm.addGizmo(new OuterWall(0, totalWidth, 0, totalWidth, 0));
		pm.addGizmo(new OuterWall(0, 0, 0, totalWidth, 0));
		pm.addGizmo(new OuterWall(totalWidth, 0, totalWidth, 0, 0));

		pm.addGizmo(new LeftFlipper(10, 10, 2, 2, 90));

		AbstractGizmo ba = new BallActor(10.5, 5.1, 0, 0, 0, new Vect(Angle.ZERO, 1));

		pm.addBallActor(ba);
		pm.addGizmo(ba);

		java.util.Timer updateT = new java.util.Timer();
		updateT.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				pm.timeTick();
			}
		}, 50, 50);

		// init test view and pass ref from model to view

		RunGUI testGUI = new RunGUI(pm);
		pm.addObserver(testGUI);
	}
}
