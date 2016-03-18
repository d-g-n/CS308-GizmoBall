import model.ProjectManager;
import view.RunGUI;

/**
 * Created by gkb13160 on 10/02/16.
 */
public class MainInit {
    public static void main(String[] args){

        // init model
        ProjectManager pm = new ProjectManager();
        // init test view and pass ref from model to view
        RunGUI testGUI = new RunGUI(pm);
        pm.addObserver(testGUI);
    }
}
