package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

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
	private static Ball ball;

	public ProjectManager(){
		boardGizmos = new ArrayList<AbstractGizmo>();
		ball = new Ball(5, 7, new Vect(0, 0));
		cManager = new CollisionManager(this);

		// HARDCODED GIZMO DEFS (mind the outer walls are never supposed to actually be in 0 -> 19)

		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22 along x

		addGizmo(ball);

		this.setChanged();
		this.notifyObservers();

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

	public static Ball getBall(){
		return ball;
	}

}
