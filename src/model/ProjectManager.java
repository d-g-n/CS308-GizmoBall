package model;

import controller.MenuListener;
import controller.RunListener;
import gizmos.AbstractGizmo;
import gizmos.BallActor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ProjectManager extends Observable{
	
	private static CollisionManager cManager;
	private static FileManager fManager;
	private MenuListener menuListener = new MenuListener();
	private List<AbstractGizmo> boardGizmos;
	
	public ProjectManager(){
		cManager = new CollisionManager();
		fManager = new FileManager();
		boardGizmos = new ArrayList<AbstractGizmo>();
	}

	public void addGizmo(AbstractGizmo g){
		boardGizmos.add(g);
	}

	public List<AbstractGizmo> getBoardGizmos(){
		return boardGizmos;
	}

	public void updateBallTest() {
		BallActor ba = new BallActor(0, 0, 0, 0, 0);

		for(AbstractGizmo ag : boardGizmos){
			if(ag.getClass().equals(BallActor.class)){
				ba = (BallActor) ag;
				break;
			}
		}
		// THIS CODE SHOULD BE IN COLLIONMANAGER OR SOMETHING, PURELY ILLUSTRATIONAL
		double xp = ba.getXpos();
		double yp = ba.getYpos();

		if (xp >= 20) {
			xp = 0;
		} else {
			xp = xp + 0.05;
		}

		ba.setPos(xp, ba.getYpos());

		this.setChanged();
		this.notifyObservers();
	}
}
