package gizmos;

import physics.Circle;
import physics.Vect;
import view.Board;

import java.awt.*;

public class Ball extends AbstractGizmo {

	private Vect velocity;
	private final double radius = 0.25 * (Board.BOARD_WIDTH / Board.X_CELLS);

	private boolean stopped;

	// x, y coordinates and x,y velocity
	public Ball(double x, double y, Vect initialVelocity) {

		super(x, y, 0, 0,
				Color.white, // colour of gizmo
				0 // reflection coefficent
		);

		velocity = initialVelocity;
		stopped = false;

	}

	public void setVelocity(Vect v) {
		velocity = v;
	}

	public void setStopped(boolean stopped) { this.stopped = stopped; }

	public void applyGravityConstant(double tickTime) {
		
		Vect gravityApplied = new Vect(velocity.x(), velocity.y() + ((Board.convertLtoPix(25)) * tickTime));
		
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

	@Override
	public Shape getShape(){
		return getCircle().toEllipse2D();
	}

	public double getRadius() {
		return radius;
	}
}