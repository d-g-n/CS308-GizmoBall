package model;

import controller.MagicKeyListener;
import controller.MenuListener;
import gizmos.AbstractGizmo;
import gizmos.BallActor;
import gizmos.LeftFlipper;
import physics.Angle;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ProjectManager extends Observable{
	
	private static CollisionManager cManager;
	private static FileManager fManager;
	private MenuListener menuListener = new MenuListener();
	private MagicKeyListener keyListener = new MagicKeyListener(this);
	private List<AbstractGizmo> boardGizmos;
	private AbstractGizmo ball;
	
	public ProjectManager(){
		cManager = new CollisionManager(this);
		fManager = new FileManager();
		boardGizmos = new ArrayList<AbstractGizmo>();
		ball = new BallActor(0,0,0,0,0,new Vect(Angle.ZERO,1));
	}

	public void addGizmo(AbstractGizmo g){
		boardGizmos.add(g);
	}

	public void addBallActor(AbstractGizmo ball){
		this.ball = ball;
	}

	public List<AbstractGizmo> getBoardGizmos(){
		return boardGizmos;
	}

	public void timeTick(){
		cManager.collisionUpdate(ball);

		this.setChanged();
		this.notifyObservers();
	}

	public void updateFlipper(String string, int ang) {
		for(AbstractGizmo g : boardGizmos){
			if(g.getClass().equals(LeftFlipper.class)){
			
			g.setGizAngle(ang);
			this.setChanged();
			this.notifyObservers();
			}
		}
		
	}
}
