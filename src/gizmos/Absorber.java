package gizmos;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import physics.Vect;

public class Absorber extends AbstractGizmo {

	Ball boardBall = null;

	public Absorber(int x, int y, int w, int h) {

		super(x, y, w, h, Color.magenta, // colour of gizmo
				0 // reflection coefficent
		);

		addPhysicsPath(Arrays.asList(new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));

		setShape(new Rectangle2D.Double((xpos), (ypos), (width), (height)));

	}

	public void setHeldBall(Ball b) {
		this.boardBall = b;
	}

	/**
	 * This is an example of how to extend abstract behaviour to provide
	 * additional behaviour
	 */
	@Override
	public void onHit(AbstractGizmo hit) {
		// hold the ball in this

		boardBall = ((Ball) hit);

		if (boardBall != null) {

			boardBall.setStopped(true);

			boardBall.setVelocity(new Vect(0, 0));

			boardBall.setPos(xpos + width - boardBall.getRadius() * 2, ypos);

		}

		super.onHit(hit);
	}

	/**
	 * Like above, will want to set the velocity of the ball if the ball is
	 * currently being held
	 */
	@Override
	public void doTrigger() {
		super.doTrigger();

		// if ball is held, chuck it back out

		if (boardBall != null && boardBall.isStopped()) {

			boardBall.setStopped(false);

			boardBall.setVelocity(new Vect(0, -50));

			boardBall = null;

		}

	}

	@Override
	public void setGizShape(double x, double y) {

		setShape(new Rectangle2D.Double((x), (y), (width), (height)));

	}

	@Override
	public void setGizPhysics(double x, double y) {

		addPhysicsPath(Arrays.asList(new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));

	}

	@Override
	public String getType() {
		return "Absorber";
	}
}
