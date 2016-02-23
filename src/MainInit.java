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
import view.Board;
import view.RunGUI;

/**
 * Created by gkb13160 on 10/02/16.
 */
public class MainInit {
    public static void main(String[] args){

    	int totalWidth=20;
        // init model
        ProjectManager pm = new ProjectManager();
        pm.loadFile("boards/gizmos.txt");

        RunGUI testGUI = new RunGUI(pm);

        pm.addObserver(testGUI);
    }
}
