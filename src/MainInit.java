
import gizmos.OuterWall;
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

        pm.addGizmo(new OuterWall(0,0,Board.BOARD_WIDTH,Board.BOARD_HEIGHT,0));



        RunGUI testGUI = new RunGUI(pm);

        pm.addObserver(testGUI);

        pm.loadFile("boards/gizmos.txt");
    }
}
