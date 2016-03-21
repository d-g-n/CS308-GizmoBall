package gizmos;

import physics.Vect;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * The Absorber class implements the AbstractGizmo class
 * which represents an absorber of the board.
 * 
 */
public class Absorber extends AbstractGizmo {

	Ball boardBall = null;

	public Absorber(int x, int y, int w, int h) {

		super(x, y, w, h,
				Color.magenta, // colour of gizmo
				0 // reflection coefficent
		);

		this.type = "Absorber";

	}

	/*
	 * Set a ball to be held in the absorber
	 */
	public void setHeldBall(Ball b){
		this.boardBall = b;
	}



	/**
	 * @see gizmos.AbstractGizmo#onHit
	 */
	@Override
	public void onHit(AbstractGizmo hit) {
		// hold the ball in this

		boardBall = ((Ball) hit);
		if(boardBall != null) {
			boardBall.setStopped(true);
			boardBall.setVelocity(new Vect(0, 0));
			boardBall.setPos(xpos + width - boardBall.getRadius() * 2, ypos);
		}
		super.onHit(hit);
	}

	/**
	 * @see gizmos.AbstractGizmo#doTrigger
	 */
	@Override
	public void doTrigger() {
		super.doTrigger();
		// if ball is held, chuck it back out
		if(boardBall != null && boardBall.isStopped()){
			boardBall.setStopped(false);
			boardBall.setVelocity(new Vect(0, -50));
			boardBall = null;
		}

	}
	
	/**
	 * @see gizmos.AbstractGizmo#setGizShape
	 */
	@Override
	public void setGizShape(double x, double y) {
		setShape(new Rectangle2D.Double(
				(x),
				(y),
				(width),
				(height)
		));
	}
	
	/**
	 * @see gizmos.AbstractGizmo#setGizPhysics
	 */
	@Override
	public void setGizPhysics(double x, double y) {
		addPhysicsPath(Arrays.asList(
				new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));
		
	}
}
