package model;

import java.util.List;
import java.util.Observable;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.Ball;
import gizmos.BallActor;
import gizmos.CircularBumper;
import gizmos.OuterWall;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import view.Board;

public class CollisionManager extends Observable {

	public final double MOVE_TIME = 0.02;
	private ProjectManager pm;
	private Ball ball;
	private boolean absorb = false;
	double absorberX;
	double absorberY;
	double absorberHeight;
	double absorberWidth;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
		this.ball = pm.getBall();
	}

	public void moveBall() {
		CollisionDetails info = shortestTimeUntilCollision();
		if (info.getTimeToCollision() > MOVE_TIME) {
			ball = moveBallForTime(ball, MOVE_TIME);
			ball.applyGravityConstant(MOVE_TIME);
			ball.applyFriction(MOVE_TIME, 0.025, 0.025* (Board.BOARD_WIDTH / Board.X_CELLS));
		} else {
			// We've got a collision in tuc
			ball = moveBallForTime(ball, info.getTimeToCollision());
			// Post collision velocity ...
			ball.setVelocity(info.getVelocity());
			ball.applyGravityConstant(MOVE_TIME);
			ball.applyFriction(MOVE_TIME, 0.025, 0.025 * (Board.BOARD_WIDTH / Board.X_CELLS));
			if (absorb) {
				ball.setXPos( (absorberWidth + absorberX - 0.5) * Board.BOARD_WIDTH / Board.X_CELLS);
				ball.setYPos(absorberY * Board.BOARD_HEIGHT / Board.Y_CELLS);
				ball.stop();
			}

			
		}
	}

	public CollisionDetails shortestTimeUntilCollision() {
		List<AbstractGizmo> gizmos = pm.getBoardGizmos();
		Vect velocity = ball.getVelocity();
		double timeToCollision = 0.0;
		double shortestTime = Double.MAX_VALUE;
		Vect newVelocity = new Vect(0, 0);

		for (AbstractGizmo gizmo : gizmos) {
			if (gizmo instanceof OuterWall) {
				for (LineSegment line : gizmo.getStoredLines()) {
					timeToCollision = Geometry.timeUntilWallCollision(line, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectWall(line, velocity, 1.0);
						absorb = false;
					}
				}
			} else if (gizmo instanceof CircularBumper) {
				for (Circle circle : gizmo.getStoredCircles()) {
					timeToCollision = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(),
								velocity,1);
						absorb = false;
					}
				}
			} else if (gizmo instanceof TriangleBumper) {
				for (LineSegment line : gizmo.getStoredLines()) {
					timeToCollision = Geometry.timeUntilWallCollision(line, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectWall(line, velocity, 1.0);
						absorb = false;
					}
				}
				for (Circle circle : gizmo.getStoredCircles()) {
					timeToCollision = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(),
								velocity,1);
					}
				}
			} else if (gizmo instanceof SquareBumper) {
				for (LineSegment line : gizmo.getStoredLines()) {
					timeToCollision = Geometry.timeUntilWallCollision(line, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectWall(line, velocity, 1.0);
						absorb = false;
					}
				}
				for (Circle circle : gizmo.getStoredCircles()) {
					timeToCollision = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(),
								velocity,1);
					}
				}
				
			} else if (gizmo instanceof Absorber) {
				Absorber a = (Absorber) gizmo;
				for (LineSegment line : gizmo.getStoredLines()) {
					timeToCollision = Geometry.timeUntilWallCollision(line, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						Vect zero = new Vect(0.0,0.0);
						newVelocity = zero;
						absorb = true;
						absorberX = a.getXpos();
						absorberY = a.getYpos();
						absorberHeight = a.getHeight();
						absorberWidth = a.getWidth();
						
					}
				}
				for (Circle circle : gizmo.getStoredCircles()) {
					timeToCollision = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), velocity);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						Vect zero = new Vect(0.0,0.0);
						newVelocity = zero;
						absorb = true;
					}
				}
				
				
				
			}
		}

		return new CollisionDetails(newVelocity, shortestTime);
	}

	public Ball moveBallForTime(Ball ball, double time) {
		double newXPos = 0.0;
		double newYPos = 0.0;
		double xVel = ball.getVelocity().x();
		double yVel = ball.getVelocity().y();
		newXPos = ball.getXPos() + (xVel * time);
		newYPos = ball.getYPos() + (yVel * time);
		ball.setXPos(newXPos);
		ball.setYPos(newYPos);
		return ball;
	}
}