package model;

import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.Ball;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * The CollisionManager class provides core functionality to identify collisions
 * of the balls on the board with its gizmos.
 *
 * */
public class CollisionManager extends Observable {

	private ProjectManager pm;

	private double SETTINGS_GRAVITY = 25;
	private double SETTINGS_FRICTION_MU = 0.025;
	private double SETTINGS_FRICTION_MU2 = 0.025;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
	}

	/**
	  * The moveBall method is fired every time tick to move the ball taking into
	  * account any collisions
	  */
	public void moveBall() {

		for(AbstractGizmo g : pm.getBoardGizmos()) {

			if(!g.getClass().equals(Ball.class))
				continue;

			Ball ball = (Ball) g;

			if (ball.isStopped())
				continue;

			CollisionDetails info = shortestTimeUntilCollision(ball);

			if (info.getTimeToCollision() <= GizmoConstants.MOVE_TIME) {
				// collision going to occur so fire any gizmos now so that the angular velocity of flippers and other
				// moving things will be posted to the next shortesttimecall

				// fire onhit method on the gizmo it's hitting


				// maybe just change the onHit method to always take in the Ball object hitting it then we can just
				// choose not to use the ball object if we don't want to

				info.getHitGizmo().onHit(ball);
				pm.updateScore(info.getHitGizmo());
			}


			//info = shortestTimeUntilCollision(ball);
			if (info.getTimeToCollision() > GizmoConstants.MOVE_TIME) {

				moveBallForTime(ball, GizmoConstants.MOVE_TIME);

			} else {

				// We've got a collision in tuc

				moveBallForTime(ball, info.getTimeToCollision());

				Vect resultV;


				if(info.getHitGizmo().getClass().equals(Ball.class)){
					resultV = info.getVelocity();
				} else {
					resultV = Geometry.applyReflectionCoeff(ball.getVelocity(), info.getVelocity(), info.getHitGizmo().getReflectionCoefficient());
				}

				ball.setVelocity(resultV);

				if(info.getHitGizmo().getClass().equals(Ball.class)) {
					((Ball) info.getHitGizmo()).setVelocity(info.getOtherVelocity());
				}

				// a collision has happened so trigger the superclass onhit

				((AbstractGizmo)info.getHitGizmo()).sendTriggers();

			}



		}


	}

	/**
	  * Find the shortest time until the ball collides with another gizmo
	  *
	  * @param ball
	  *            the moving ball
	  * @return a CollisionsDetails object which contains the new velocity, the
	  *         old velocity, the time until the collision and the gizmo that
	  *         will hit
	  */
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

	/**
	  * Move the ball for a specified time period
	  * @param ball
	  * 				the ball to move
	  * @param time
	  * 				the time period to move the ball
	  * @return
	  * 		the updated ball object
	  */
	public Ball moveBallForTime(Ball ball, double time) {

		double newXPos = 0.0;
		double newYPos = 0.0;
		double xVel = ball.getVelocity().x();
		double yVel = ball.getVelocity().y();

		newXPos = ball.getXPos() + (xVel * time);
		newYPos = ball.getYPos() + (yVel * time);

		ball.setPos(newXPos, newYPos);

		ball.applyGravityConstant( time, SETTINGS_GRAVITY);
		ball.applyFriction( time, SETTINGS_FRICTION_MU * time, SETTINGS_FRICTION_MU2 * GizmoConstants.X_CELLS);

		return ball;
	}

	/**
	 * Set the gravity of the board
	 * @param grav
	 */
	public void setGravity(double grav){
		this.SETTINGS_GRAVITY = grav;
	}

	/**
	 * Set the new friction of the board
	 * @param mu
	 * @param mu2
	 */
	public void setFriction(double mu, double mu2){
		this.SETTINGS_FRICTION_MU = mu;
		this.SETTINGS_FRICTION_MU2 = mu2;
	}

	/**
	 * Get the gravity of the board
	 * @return
	 */
	public double getGravity() {
		return this.SETTINGS_GRAVITY;
	}

	/**
	 * Get the mu friction of the board
	 * @return
	 */
	public double getMuFriction() {
		return this.SETTINGS_FRICTION_MU;
	}

	/**
	 * Get the mu2 friction of the board
	 * @return
	 */
	public double getMu2Friction() {
		return this.SETTINGS_FRICTION_MU2;
	}

	/**
	  * The CollisionDetails class contains all the information about a future collision
	  *
	  */
	public class CollisionDetails {

		private Vect velocity, velocity2;
		private double time;
		private AbstractGizmo hitGizmo;

		public CollisionDetails(Vect velocity1, Vect velocity2, double time, AbstractGizmo hitGiz){
			this.velocity = velocity1;
			this.velocity2 = velocity2;
			this.time = time;
			this.hitGizmo = hitGiz;
		}

		public Vect getVelocity(){
			return velocity;
		}
		public Vect getOtherVelocity(){
			return velocity2;
		}

		public double getTimeToCollision(){
			return time;
		}

		public AbstractGizmo getHitGizmo() { return hitGizmo; }
	}
}
