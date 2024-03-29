package gizmos;

import physics.Circle;
import physics.Vect;

import java.awt.*;

public class Ball extends AbstractGizmo {

	private Vect velocity;
	private final double radius = 0.25;

	private boolean stopped;

	/**
	 * The Ball class represents the ball on the board,
	 * where the user can retrieve all the information
	 * about the state of the ball.
	 */
	public Ball(double x, double y, Vect initialVelocity) {

		super(x, y, 0.5, 0.5,
				Color.white, // colour of gizmo
				0 // reflection coefficent
		);

		velocity = initialVelocity;
		stopped = false;

		this.type = "Ball";

	}

	public void setVelocity(Vect v) {
		velocity = v;
	}

	public void setStopped(boolean stopped) { this.stopped = stopped; }

	public void applyGravityConstant(double tickTime, double gravity) {
		
		Vect gravityApplied = new Vect(velocity.x(), velocity.y() + ((gravity) * tickTime));
		
		this.setVelocity(gravityApplied);
	}
	
	public void applyFriction(double tickTime, double mu, double mu2) {
		
		Vect frictionApplied = new Vect(velocity.x() * (1 - (mu * tickTime)) - (mu2 * velocity.x()) * tickTime, velocity.y() * (1 - (mu * tickTime)) - (mu2 * velocity.y()) * tickTime);
		
		
		this.setVelocity(frictionApplied);
		
		
	}

	public boolean isStopped() {
		return stopped;
	}

	public Vect getVelocity() {
		return velocity;
	}

	public Circle getCircle() {
		return new Circle(xpos, ypos, radius);
	}

	/**
	 * @see gizmos.AbstractGizmo#getShape
	 */
	@Override
	public Shape getShape(){
		return getCircle().toEllipse2D();
	}

	public double getRadius() {
		return radius;
	}
}