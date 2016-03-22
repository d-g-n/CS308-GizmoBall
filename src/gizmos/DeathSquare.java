package gizmos;


import physics.Vect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class DeathSquare extends AbstractGizmo {
	
	Ball boardBall = null;

	/**
	 * The DeathSquare class represents the special squares on the board
	 * which kill the ball when they get hit by it.
	 *
	 */
	public DeathSquare(int x, int y) {
		super(x, y, 1, 1,
				Color.GRAY, // colour of gizmo
				0 // reflection coefficent
		);

		this.type = "DeathSquare";

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

	/**
	 * @see gizmos.AbstractGizmo#onHit
	 */
	@Override
	public void onHit(AbstractGizmo hit) {

		boardBall = ((Ball) hit);

		if(boardBall != null) {
			boardBall.setVelocity(new Vect(0,0));
		}

	}
	
	
	
	

}
