package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import controller.MenuListener;
import gizmos.AbstractGizmo;
import gizmos.Ball;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import physics.Vect;
import view.Board;
import view.RunGUI;

public class ProjectManager extends Observable{
	
	private static CollisionManager cManager;
	private static FileManager fManager;
	private MenuListener menuListener = new MenuListener();
	private List<AbstractGizmo> boardGizmos;
	private Ball ball;
	private static final double INITIAL_BALL_XPOS = (15 * Board.BOARD_WIDTH /Board.CELL_WIDTH);
	private static final double INITIAL_BALL_YPOS = (10 * Board.BOARD_HEIGHT /Board.CELL_HEIGHT);

	public ProjectManager(){
		boardGizmos = new ArrayList<AbstractGizmo>();
		ball = new Ball(INITIAL_BALL_XPOS, INITIAL_BALL_YPOS,0,50);
		cManager = new CollisionManager(this);

		this.loadFile("boards/gizmos.txt");

	}

	public void addGizmo(AbstractGizmo g){
		boardGizmos.add(g);
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
		fManager = new FileManager(fileName, boardGizmos);
		fManager.loadFile();

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
