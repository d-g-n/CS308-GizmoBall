package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.Ball;
import physics.Vect;
import view.Board;

public class ProjectManager extends Observable{
	
	private static CollisionManager cManager;
	private static FileManager fManager;
	private List<AbstractGizmo> boardGizmos;
	private Ball ball;
	
	public ProjectManager(){
		fManager = new FileManager();
		boardGizmos = new ArrayList<AbstractGizmo>();
		ball = new Ball(18 * Board.CELL_WIDTH,5 * Board.CELL_WIDTH,100,100);
		cManager = new CollisionManager(this);
	}

	public void addGizmo(AbstractGizmo g){
		boardGizmos.add(g);
	}

	public void addBallActor(Ball ball){
		this.ball = ball;
	}

	public List<AbstractGizmo> getBoardGizmos(){
		return boardGizmos;
	}

	public void moveBall(){
		cManager.moveBall();
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setBallSpeed(int x, int y) {
		ball.setVelocity(new Vect(x, y));
	}
	
	public Ball getBall(){
		return ball;
	}
}
