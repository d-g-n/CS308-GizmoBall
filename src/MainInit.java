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
    public static void main(String[] args){

    	int totalWidth=20;
        // init model
        ProjectManager pm = new ProjectManager();

        pm.addGizmo(new OuterWall(0,0,totalWidth,0,0));
        pm.addGizmo(new OuterWall(0,totalWidth,0,totalWidth,0));
        pm.addGizmo(new OuterWall(0,0,0,totalWidth,0));
        pm.addGizmo(new OuterWall(totalWidth,0,totalWidth,0,0));
        
//        pm.addGizmo(new SquareBumper(10, 10, 1, 1, 0));
//        pm.addGizmo(new SquareBumper(9, 10, 1, 1, 0));
//        pm.addGizmo(new SquareBumper(11, 10, 1, 1, 0));
//
//        pm.addGizmo(new SquareBumper(12, 9, 1, 1, 0));
//        pm.addGizmo(new SquareBumper(8, 9, 1, 1, 0));
//
//        pm.addGizmo(new SquareBumper(9, 7, 1, 1, 0));
//        pm.addGizmo(new SquareBumper(11, 7, 1, 1, 0));

//        pm.addGizmo(new TriangleBumper(1, 1, 1, 1, 0));
//        pm.addGizmo(new TriangleBumper(2, 1, 1, 1, 90));
//
//        pm.addGizmo(new CircularBumper(10, 1, 1, 1, 0));

          pm.addGizmo(new LeftFlipper(15, 15, 0, 0, -90));

//        pm.addGizmo(new Absorber(1, 18, 18, 1, 0));

       AbstractGizmo ba = new BallActor(10.5, 5.1, 0, 0, 0, new Vect(Angle.ZERO,1));

       pm.addBallActor(ba);
       pm.addGizmo(ba);

        java.util.Timer updateT = new java.util.Timer();
        updateT.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                pm.timeTick();
            }
        }, 1/20, 100);

        // init test view and pass ref from model to view

        RunGUI testGUI = new RunGUI(pm);
        pm.addObserver(testGUI);
    }
}
