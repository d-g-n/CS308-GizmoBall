package model;

import java.util.List;
import java.util.Observable;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.Ball;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import view.Board;

public class CollisionManager extends Observable {

	private ProjectManager pm;
	private List<Ball> ballList;

	private double SETTINGS_GRAVITY = 25;
	private double SETTINGS_FRICTION_MU = 0.025;
	private double SETTINGS_FRICTION_MU2 = 0.025;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
		this.ballList = pm.getBall();
	}

	public void moveBall() {

		if(ballList.size() == 0){
			this.ballList = pm.getBall();
			return;
		}

		for(Ball ball : ballList) {

			if (ball.isStopped())
				return;

			CollisionDetails info = shortestTimeUntilCollision(ball);

			if (info.getTimeToCollision() <= Board.MOVE_TIME) {
				// collision going to occur so fire any gizmos now so that the angular velocity of flippers and other
				// moving things will be posted to the next shortesttimecall

				// fire onhit method on the gizmo it's hitting


				// maybe just change the onHit method to always take in the Ball object hitting it then we can just
				// choose not to use the ball object if we don't want to
				if(info.getHitGizmo().getClass().equals(Absorber.class)) { // must be a better way to do this
					((Absorber)info.getHitGizmo()).setHeldBall(ball);
				}

				info.getHitGizmo().onHit(ball);
			}


			info = shortestTimeUntilCollision(ball);
			if (info.getTimeToCollision() > Board.MOVE_TIME) {

				moveBallForTime(ball, Board.MOVE_TIME);

			} else {

				// We've got a collision in tuc

				moveBallForTime(ball, info.getTimeToCollision());

				ball.setVelocity(info.getVelocity());

				if(info.getHitGizmo().getClass().equals(Ball.class)) {
					((Ball) info.getHitGizmo()).setVelocity(info.getOtherVelocity());
				}
			}


		}


	}

	public CollisionDetails shortestTimeUntilCollision(Ball ball) {
		List<AbstractGizmo> gizmos = pm.getBoardGizmos();
		Vect velocity = ball.getVelocity();
		double timeToCollision = 0.0;
		double shortestTime = Double.MAX_VALUE;
		Vect newVelocity = new Vect(0, 0);
		Vect otherVelocity = new Vect(0, 0);

		AbstractGizmo hitGiz = null;

		for (AbstractGizmo gizmo : gizmos) {

			if(gizmo.equals(ball)) // skip collision checking if the gizmo to collide with is itself
				continue;

			if(gizmo.getClass().equals(Ball.class)){ // if the collidable is another ball

				Ball otherB = ((Ball) gizmo);

				timeToCollision = Geometry.timeUntilBallBallCollision(
						ball.getCircle(),
						ball.getVelocity(),
						otherB.getCircle(),
						otherB.getVelocity()
				);

				if (timeToCollision < shortestTime) {

					shortestTime = timeToCollision;
					Geometry.VectPair vectPair = Geometry.reflectBalls(
							ball.getCenter(),
							1.0,
							ball.getVelocity(),
							otherB.getCenter(),
							1.0,
							otherB.getVelocity()
					);

					newVelocity = vectPair.v1;

					otherVelocity = vectPair.v2;

					hitGiz = gizmo;

				}

			} else if(gizmo.getAngularVelocity() == 0.0) { // this has issues
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
			} else { // do rotating wall stuff



				for (LineSegment line : gizmo.getStoredLines()) {

					timeToCollision = Geometry.timeUntilRotatingWallCollision(
							line,
							gizmo.getRotateAroundPoint(),
							gizmo.getAngularVelocity(),
							ball.getCircle(),
							velocity
					);

					if (timeToCollision < shortestTime) {

						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectRotatingWall(
								line,
								gizmo.getRotateAroundPoint(),
								gizmo.getAngularVelocity(),
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
							gizmo.getAngularVelocity(),
							ball.getCircle(),
							velocity
					);

					if (timeToCollision < shortestTime) {

						shortestTime = timeToCollision;
						newVelocity = Geometry.reflectRotatingCircle(
								circle,
								gizmo.getRotateAroundPoint(),
								gizmo.getAngularVelocity(),
								ball.getCircle(),
								velocity,
								gizmo.getReflectionCoefficient()
						);
						hitGiz = gizmo;

					}



				}


			}
		}

		return new CollisionDetails(newVelocity, otherVelocity, shortestTime, hitGiz);
	}

	public Ball moveBallForTime(Ball ball, double time) {



		double newXPos = 0.0;
		double newYPos = 0.0;
		double xVel = ball.getVelocity().x();
		double yVel = ball.getVelocity().y();

		newXPos = ball.getXPos() + (xVel * time);
		newYPos = ball.getYPos() + (yVel * time);

		ball.setPos(newXPos, newYPos);

		ball.applyGravityConstant( time, SETTINGS_GRAVITY);
		ball.applyFriction( time, SETTINGS_FRICTION_MU * time, SETTINGS_FRICTION_MU2 * Board.X_CELLS);

		return ball;
	}

	public void setGravity(double grav){
		this.SETTINGS_GRAVITY = grav;
	}

	public void setFriction(double mu, double mu2){
		this.SETTINGS_FRICTION_MU = mu;
		this.SETTINGS_FRICTION_MU2 = mu2;
	}
}
