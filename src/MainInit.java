

import gizmos.OuterWall;
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


        pm.addGizmo(new OuterWall(0, 0, 20, 20, 0));


        pm.setBallSpeed(200, -30);

        // init test view and pass ref from model to view

        RunGUI testGUI = new RunGUI(pm);

        pm.addObserver(testGUI);

        pm.loadFile("boards/gizmos.txt");
    }
}
