import gizmos.Absorber;
import gizmos.Ball;
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

        // init model
        ProjectManager pm = new ProjectManager();

        pm.addGizmo(new OuterWall(0,0,Board.BOARD_WIDTH,Board.BOARD_HEIGHT,0));
        
        pm.addGizmo(new SquareBumper(10, 10, 1, 1, 0));
        pm.addGizmo(new SquareBumper(9, 10, 1, 1, 0));
        pm.addGizmo(new SquareBumper(11, 10, 1, 1, 0));

        pm.addGizmo(new SquareBumper(12, 9, 1, 1, 0));
        pm.addGizmo(new SquareBumper(8, 9, 1, 1, 0));

        pm.addGizmo(new SquareBumper(9, 7, 1, 1, 0));
        pm.addGizmo(new SquareBumper(11, 7, 1, 1, 0));

        pm.addGizmo(new TriangleBumper(1, 1, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(2, 1, 1, 1, 90));
        pm.addGizmo(new TriangleBumper(12, 16, 1, 1, 0));

        pm.addGizmo(new CircularBumper(10, 1, 1, 1, 0));
        pm.addGizmo(new CircularBumper(17, 10, 1, 1, 0));
        pm.addGizmo(new CircularBumper(10, 14, 1, 1, 0));
        pm.addGizmo(new CircularBumper(11, 1, 1, 1, 0));

        
        pm.addGizmo(new SquareBumper(1, 13, 1, 1, 0));
        pm.addGizmo(new SquareBumper(1, 12, 1, 1, 0));
        pm.addGizmo(new SquareBumper(11, 9, 1, 1, 0));


        pm.addGizmo(new Absorber(1, 18, 18, 1, 0));  
        
        
  /*      pm.addGizmo(new TriangleBumper(1, 1, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(2, 1, 1, 1, 90));
        pm.addGizmo(new TriangleBumper(2, 2, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(1, 2, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(1, 1, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(2, 1, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(3, 1, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(4, 1, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(12, 1, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(12, 1, 1, 1, 90));
        pm.addGizmo(new TriangleBumper(12, 2, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(12, 7, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(12, 2, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(12, 3, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(12, 5, 1, 1, 0));
        pm.addGizmo(new TriangleBumper(12, 11, 1, 1, 0));
       
        */
        
        pm.setBallSpeed(200, -200);
        
        // init test view and pass ref from model to view

        RunGUI testGUI = new RunGUI(pm);

        pm.addObserver(testGUI);
    }
}
