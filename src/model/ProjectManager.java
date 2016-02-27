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
	private static Ball ball;

	public ProjectManager(){
		boardGizmos = new ArrayList<>();
		gizmoKeyPressMap = new HashMap<>();
		cManager = new CollisionManager(this);

		// HARDCODED GIZMO DEFS (mind the outer walls are never supposed to actually be in 0 -> 19)

		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22 along x

		this.setChanged();
		this.notifyObservers();

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

		this.setChanged();
		this.notifyObservers();
	}

	public void loadFile(String fileName) {
		fManager = new FileManager(this, fileName);
		fManager.loadFile();

		this.setChanged();
		this.notifyObservers();
	}

	public void setBall(Ball ball){
		this.ball = ball;
	}

	public static Ball getBall(){
		return ball;
	}

}
