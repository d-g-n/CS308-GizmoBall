package gizmos;

import physics.Circle;
import physics.Vect;

public class Ball {

	private Vect velocity;
	private double radius;
	private double xpos;
	private double ypos;

	private boolean stopped;

	// x, y coordinates and x,y velocity
	public Ball(double x, double y, double xv, double yv) {
		xpos = x; // Centre coordinates
		ypos = y;
		velocity = new Vect(xv, yv);
		radius = 10;
		stopped = false;
	}

	public Vect getVelocity() {
		return velocity;
	}

	public void setVelocity(Vect v) {
		velocity = v;
	}

	public double getRadius() {
		return radius;
	}

	public Circle getCircle() {
		return new Circle(xpos, ypos, radius);

	}

	// Ball specific methods that deal with double precision.
	public double getXPos() {
		return xpos;
	}

	public double getYPos() {
		return ypos;
	}

	public void setXPos(double x) {
		xpos = x;
	}

	public void setYPos(double y) {
		ypos = y;
	}

	public void stop() {
		stopped = true;
	}

	public void start() {
		stopped = false;
	}

	public boolean stopped() {
		return stopped;
	}
}
