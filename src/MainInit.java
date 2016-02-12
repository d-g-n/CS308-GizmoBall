import gizmos.*;
import model.ProjectManager;
import physics.Angle;
import physics.Vect;
import view.RunGUI;

import java.util.TimerTask;

/**
 * Created by gkb13160 on 10/02/16.
 */
public class MainInit {
    public static void main(String[] args){

        // init model
        ProjectManager pm = new ProjectManager();

        // note: walls should be outside the standard 20x20 playing area
        // top wall
      /*  pm.addGizmo(new OuterWall(-1,-1,22,1,0));
        // bottom wall
        pm.addGizmo(new OuterWall(-1,21,22,1,0));
        // left wall
        pm.addGizmo(new OuterWall(-1,0,1,20,0));
        // right wall
        pm.addGizmo(new OuterWall(21,0,1,20,0));*/
        
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

        pm.addGizmo(new LeftFlipper(15, 15, 2, 2, 90));

        pm.addGizmo(new RightFlipper(10, 15, 2, 2, 0));

        pm.addGizmo(new Absorber(1, 18, 18, 1, 0));

        AbstractGizmo ba = new BallActor(10.5, 5.1, 0, 0, 0, new Vect(Angle.ZERO,1));

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
