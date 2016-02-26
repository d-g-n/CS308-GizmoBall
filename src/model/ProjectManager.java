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
	
	private static CollisionManager cManager;
	private static FileManager fManager;
	private MenuListener menuListener = new MenuListener();
	private List<AbstractGizmo> boardGizmos;
	private static List<AbstractGizmo> gizToFire;
	private static Ball ball;

	public ProjectManager(){
		boardGizmos = new ArrayList<AbstractGizmo>();
		gizToFire = new ArrayList<>();
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

		g.setName("noname");

		boardGizmos.add(g);
	}

	public List<AbstractGizmo> getBoardGizmos(){
		return boardGizmos;
	}

	public void moveBall(){

		cManager.moveBall();

		for(AbstractGizmo giz : gizToFire){
			giz.doTrigger();
		}

		gizToFire.clear();

		this.setChanged();
		this.notifyObservers();
	}

	public void loadFile(String fileName) {
		fManager = new FileManager(fileName, boardGizmos);
		fManager.loadFile();

		this.setChanged();
		this.notifyObservers();
	}

	public static Ball getBall(){
		return ball;
	}

	public void fireGizmo(AbstractGizmo hitGizmo) {
		hitGizmo.onHit();
	}

	public static void addGizToFire(AbstractGizmo giz){
		gizToFire.add(giz);
	}
}
