package model;

import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.Ball;

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

		if(ball.stopped())
			return;


		CollisionDetails info = shortestTimeUntilCollision();
		if (info.getTimeToCollision() > MOVE_TIME) {
			ball = moveBallForTime(ball, MOVE_TIME);
		} else {
			// We've got a collision in tuc
			ball = moveBallForTime(ball, info.getTimeToCollision());
			// Post collision velocity ...
			ball.setVelocity(info.getVelocity());

			// fire onhit method on the gizmo it's hitting

			pm.fireGizmo(info.getHitGizmo());
		}

		ball.applyGravityConstant(MOVE_TIME);
		ball.applyFriction(MOVE_TIME, 0.025, 0.0025);
	}

	public CollisionDetails shortestTimeUntilCollision() {
		List<AbstractGizmo> gizmos = pm.getBoardGizmos();
		Vect velocity = ball.getVelocity();
		double timeToCollision = 0.0;
		double shortestTime = Double.MAX_VALUE;
		Vect newVelocity = new Vect(0, 0);

		AbstractGizmo hitGiz = null;

		for (AbstractGizmo gizmo : gizmos) {
			for (LineSegment line : gizmo.getStoredLines()) {
				timeToCollision = Geometry.timeUntilWallCollision(line, ball.getCircle(), velocity);
				if (timeToCollision < shortestTime) {
					shortestTime = timeToCollision;
					newVelocity = Geometry.reflectWall(line, velocity, 1.0);
					hitGiz = gizmo;
				}
			}
			for (Circle circle : gizmo.getStoredCircles()) {
				timeToCollision = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), velocity);
				if (timeToCollision < shortestTime) {
					shortestTime = timeToCollision;
					newVelocity = Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(),
							velocity,1);
					hitGiz = gizmo;
				}
			}
		}

		return new CollisionDetails(newVelocity, shortestTime, hitGiz);
	}

	public Ball moveBallForTime(Ball ball, double time) {

		double newXPos = 0.0;
		double newYPos = 0.0;
		double xVel = ball.getVelocity().x();
		double yVel = ball.getVelocity().y();

		newXPos = ball.getXPos() + (xVel * time);
		newYPos = ball.getYPos() + (yVel * time);

		ball.setPos(newXPos, newYPos);

		return ball;
	}
}
