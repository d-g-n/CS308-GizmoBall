import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import model.ProjectManager;
import view.GizmoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * purely for testing
 * Created by Declan on 06/02/2016.
 */
public class MainMethod {

	public static void main(String[] args){
		new MainMethod();
	}


	public MainMethod(){

		ProjectManager pm = new ProjectManager();
		pm.addGizmo(new SquareBumper(10, 10, 1, 1, 0));
		pm.addGizmo(new SquareBumper(9, 10, 1, 1, 0));
		pm.addGizmo(new SquareBumper(11, 10, 1, 1, 0));

		pm.addGizmo(new SquareBumper(12, 9, 1, 1, 0));
		pm.addGizmo(new SquareBumper(8, 9, 1, 1, 0));

		pm.addGizmo(new SquareBumper(9, 7, 1, 1, 0));
		pm.addGizmo(new SquareBumper(11, 7, 1, 1, 0));

		pm.addGizmo(new TriangleBumper(1, 1, 1, 1, 0));
		pm.addGizmo(new TriangleBumper(2, 1, 1, 1, 1));



		JFrame testFrame = new JFrame("test panel");
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.setLayout(new BorderLayout());

		GizmoPanel gp = new GizmoPanel(pm);

		gp.setPreferredSize(new Dimension(700, 700));

		testFrame.add(gp, BorderLayout.CENTER);
		testFrame.pack();

		testFrame.setVisible(true);
	}
}
