package gizmos;


import physics.Vect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class Absorber extends AbstractGizmo {

	public Absorber(int x, int y, int w, int h, int degrees) {

		super(x, y, w, h, degrees,
				Color.magenta, // colour of gizmo
				0.95 // reflection coefficent
		);


		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));

		setShape(new Rectangle2D.Double(
				(xpos),
				(ypos),
				(width),
				(height)
		));

	}


	/**
	 * This is an example of how to extend abstract behaviour to provide additional behaviour
	 */
	@Override
	public void onHit() {
		super.onHit();

		// hold the ball in this

	}

	/**
	 * Like above, will want to set the velocity of the ball if the ball is currently being held
	 */
	@Override
	public void onCollision() {
		super.onCollision();

		// if ball is held, chuck it back out

	}
}
