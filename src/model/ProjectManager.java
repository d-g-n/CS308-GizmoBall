package model;

import java.util.*;

import controller.MenuListener;
import gizmos.*;
import physics.Vect;
import view.Board;
import view.RunGUI;

public class ProjectManager extends Observable{
	
	private CollisionManager cManager;
	private FileManager fManager;
	private MenuListener menuListener = new MenuListener();
	private List<AbstractGizmo> boardGizmos;
	private Map<Map.Entry<String, Integer>, AbstractGizmo> gizmoKeyPressMap;
	private static List<Ball> ballList;
	private String focusedButton;
	private boolean buildModeOn = false;

	public ProjectManager(){
		boardGizmos = new ArrayList<>();
		gizmoKeyPressMap = new HashMap<>();
		ballList = new ArrayList<>();
		cManager = new CollisionManager(this);
		focusedButton = "Square";
		// HARDCODED GIZMO DEFS (mind the outer walls are never supposed to actually be in 0 -> 19)

		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22 along x

		this.setChanged();
		this.notifyObservers();

	}

	public String getFocusedButton() {
		return focusedButton;
	}

	public void setFocusedButton(String focusedButton) {
		this.focusedButton = focusedButton;
	}

	public void addKeyConnect(String gizName, int keyNum, String onDownOrUp){
		AbstractGizmo giz = getGizmoByName(gizName);

		gizmoKeyPressMap.put(new AbstractMap.SimpleEntry<String, Integer>(onDownOrUp, keyNum), giz);
	}

	public void addGizmo(AbstractGizmo g){

		// ideally we'll give it a random name here but irght now
		// also need to do square checking in here to prevent overlapping gizmos

		boardGizmos.add(g);
	}

	public AbstractGizmo getGizmoByName(String name){
		for(AbstractGizmo giz : boardGizmos){
			if(giz.getName().equals(name))
				return giz;
		}

		return null;
	}

	public List<AbstractGizmo> getBoardGizmos(){
		return boardGizmos;
	}
	public Map<Map.Entry<String, Integer>, AbstractGizmo> getKeyConnects() { return gizmoKeyPressMap; }

	public void moveBall(){

		cManager.moveBall();

		for (AbstractGizmo giz : boardGizmos){
			giz.getShape(); // have to simulate a getshape operation per tick to seperate physics from visual
			// to be honest should probably seperate the stuff in getShape to like a .updatePhysics object or something
			// and have getShape just be a dummy return object
		}

	}

	public void pushVisualUpdate(){
		this.setChanged();
		this.notifyObservers();
	}

	public void loadFile(String fileName) {
		fManager = new FileManager(this, fileName);
		fManager.loadFile();

		this.setChanged();
		this.notifyObservers();
	}

	public void addBall(Ball ball){
		ballList.add(ball);
	}

	public static List<Ball> getBall(){
		return ballList;
	}

	public boolean isBuildModeOn() {
		return buildModeOn;
	}

	public void setBuildModeOn(boolean buildModeOn) {
		this.buildModeOn = buildModeOn;
	}
	
	public void clearAllBoardGizmos () {
		boardGizmos.clear();
		
		// this is required along with boardGizmos.clear() as otherwise we have invisible balls roaming around the board
		ProjectManager.ballList.clear();
		
		// re add the outwalls, maybe a silly way of doing things, could change it
		
		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22 along x
	}

}
