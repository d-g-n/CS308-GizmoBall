package model;

import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.Ball;

import gizmos.Flipper;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import view.Board;

public class CollisionManager extends Observable {

	private ProjectManager pm;
	private Ball ball;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
		this.ball = pm.getBall();
	}

	public void moveBall() {

		if(ball.isStopped())
			return;

		CollisionDetails info = shortestTimeUntilCollision();
		if (info.getTimeToCollision() > Board.MOVE_TIME) {
			ball = moveBallForTime(ball, Board.MOVE_TIME);
		} else {


			// We've got a collision in tuc
			ball = moveBallForTime(ball, info.getTimeToCollision());

			// Post collision velocity ...
			ball.setVelocity(info.getVelocity());

			// fire onhit method on the gizmo it's hitting

			pm.fireGizmo(info.getHitGizmo());

		}




	}

	public CollisionDetails shortestTimeUntilCollision() {
		List<AbstractGizmo> gizmos = pm.getBoardGizmos();
		Vect velocity = ball.getVelocity();
		double timeToCollision = 0.0;
		double shortestTime = Double.MAX_VALUE;
		Vect newVelocity = new Vect(0, 0);

		AbstractGizmo hitGiz = null;

		for (AbstractGizmo gizmo : gizmos) {

			//if(gizmo.getAngularVelocity() == 0.0) { // this has issues
				for (LineSegment line : gizmo.getStoredLines()) {

					timeToCollision = Geometry.timeUntilWallCollision(
							line,
							ball.getCircle(),
							velocity
					);

					if (timeToCollision < shortestTime) {

						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectWall(
								line,
								velocity,
								gizmo.getReflectionCoefficient()
						);
						hitGiz = gizmo;

					}
				}
				for (Circle circle : gizmo.getStoredCircles()) {

					timeToCollision = Geometry.timeUntilCircleCollision(
							circle,
							ball.getCircle(),
							velocity
					);

					if (timeToCollision < shortestTime) {

						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectCircle(
								circle.getCenter(),
								ball.getCircle().getCenter(),
								velocity,
								gizmo.getReflectionCoefficient()
						);
						hitGiz = gizmo;

					}
				}
			/* else { // do rotating wall stuff

				for (LineSegment line : gizmo.getStoredLines()) {

					timeToCollision = Geometry.timeUntilRotatingWallCollision(
							line,
							gizmo.getRotateAroundPoint(),
							Math.toRadians(gizmo.getAngularVelocity()),
							ball.getCircle(),
							velocity
					);

					if (timeToCollision < shortestTime) {

						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectRotatingWall(
								line,
								gizmo.getRotateAroundPoint(),
								Math.toRadians(gizmo.getAngularVelocity()),
								ball.getCircle(),
								velocity,
								gizmo.getReflectionCoefficient()
						);
						hitGiz = gizmo;

					}
				}
				for (Circle circle : gizmo.getStoredCircles()) {

					timeToCollision = Geometry.timeUntilRotatingCircleCollision(
							circle,
							gizmo.getRotateAroundPoint(),
							Math.toRadians(gizmo.getAngularVelocity()),
							ball.getCircle(),
							velocity
					);

					if (timeToCollision < shortestTime) {

						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectRotatingCircle(
								circle,
								gizmo.getRotateAroundPoint(),
								Math.toRadians(gizmo.getAngularVelocity()),
								ball.getCircle(),
								velocity,
								gizmo.getReflectionCoefficient()
						);
						hitGiz = gizmo;

					}



				}


			}*/
		}

		return new CollisionDetails(newVelocity, shortestTime, hitGiz);
	}

	public Ball moveBallForTime(Ball ball, double time) {

		ball.applyGravityConstant(time, 25);
		ball.applyFriction(time, 0.025, 0.025);

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
