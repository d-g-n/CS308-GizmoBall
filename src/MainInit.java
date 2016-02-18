import gizmos.Absorber;
import gizmos.Ball;
import gizmos.CircularBumper;
import gizmos.LeftFlipper;
import gizmos.OuterWall;
import gizmos.RightFlipper;
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

        // init model
        ProjectManager pm = new ProjectManager();

        pm.addGizmo(new OuterWall(0,0,Board.BOARD_WIDTH,Board.BOARD_HEIGHT,0));
        
        pm.addGizmo(new SquareBumper(10, 10, 1, 1, 0));
        pm.addGizmo(new SquareBumper(9, 10, 1, 1, 0));
        pm.addGizmo(new SquareBumper(11, 10, 1, 1, 0));

		pm.addGizmo(new LeftFlipper(10, 15, 0.5, 2, 0));
		pm.addGizmo(new RightFlipper(15, 15, 0.5, 2, 0));
        
        ///pm.setBallSpeed(200, -200);
        
        // init test view and pass ref from model to view

        RunGUI testGUI = new RunGUI(pm);

        pm.addObserver(testGUI);
    }

}
