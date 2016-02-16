package model;

import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.Ball;
import gizmos.BallActor;
import gizmos.CircularBumper;
import gizmos.OuterWall;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class CollisionManager extends Observable {

	public final double MOVE_TIME = 0.01;
	private ProjectManager pm;
	private Ball ball;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
		this.ball = pm.getBall();
	}

	public void moveBall() {
		CollisionDetails info = shortestTimeUntilCollision();
		if (info.getTimeToCollision() > MOVE_TIME) {
			ball = moveBallForTime(ball, MOVE_TIME);
		} else {
			// We've got a collision in tuc
			ball = moveBallForTime(ball, info.getTimeToCollision());
			// Post collision velocity ...
			ball.setVelocity(info.getVelocity());
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
					timeToCollision = Geometry.timeUntilWallCollision(line, ball.getCircle(), ball.getVelocity());
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectWall(line, velocity, 1.0);
					}
				}
			} else if (gizmo instanceof CircularBumper) {
				for (Circle circle : gizmo.getStoredCircles()) {
					timeToCollision = Geometry.timeUntilCircleCollision(circle,
												ball.getCircle(), velocity);
					//System.out.println(timeToCollision);
					if (timeToCollision < shortestTime) {
						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectCircle(circle.getCenter(), 
															ball.getCircle().getCenter(),
															ball.getVelocity());
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
