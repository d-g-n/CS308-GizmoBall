package model;

import controller.MagicKeyListener;
import controller.MenuListener;
import gizmos.AbstractGizmo;
import gizmos.BallActor;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import physics.Angle;
import physics.Vect;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.Ball;
import physics.Vect;
import view.Board;
import view.RunGUI;

public class ProjectManager extends Observable {

	private static CollisionManager cManager;
	private static FileManager fManager;
	private MenuListener menuListener = new MenuListener();
	private MagicKeyListener keyListener = new MagicKeyListener(this);
	private List<AbstractGizmo> boardGizmos;
	private Ball ball;
	private static final double INITIAL_BALL_XPOS = (15 * Board.BOARD_WIDTH / Board.CELL_WIDTH);
	private static final double INITIAL_BALL_YPOS = (10 * Board.BOARD_HEIGHT / Board.CELL_HEIGHT);

	public ProjectManager() {
		fManager = new FileManager();
		boardGizmos = new ArrayList<AbstractGizmo>();
		ball = new Ball(INITIAL_BALL_XPOS, INITIAL_BALL_YPOS, 50, -50);
		cManager = new CollisionManager(this);
	}

	public void addGizmo(AbstractGizmo g) {
		boardGizmos.add(g);
	}

	public void addBallActor(Ball ball) {
		this.ball = ball;
	}

	public List<AbstractGizmo> getBoardGizmos() {
		return boardGizmos;
	}

	public void moveBall() {
		cManager.moveBall();
		this.setChanged();
		this.notifyObservers();
	}

	public void updateFlipper(String string, int ang) {
		if (string.equals("left")) {
			for (AbstractGizmo g : boardGizmos) {
				if (g.getClass().equals(LeftFlipper.class)) {
					g.rotate(ang);
				}
			}
		} else {

			for (AbstractGizmo g : boardGizmos) {
				if (g.getClass().equals(RightFlipper.class)) {
					g.rotate(ang);
				}
			}
		}
		this.setChanged();
		this.notifyObservers();
	}

	public void setBallSpeed(int x, int y) {
		ball.setVelocity(new Vect(x, y));
	}

	public Ball getBall() {
		return ball;
	}
}
