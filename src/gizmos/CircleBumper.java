package gizmos;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import physics.Vect;
import view.Board;

public class CircleBumper extends AbstractGizmo {

	private final double radius = 0.5;

	/**
	 * The CircleBumper class represents the circular bumpers
	 * on the board.
	 *
	 */
	public CircleBumper(int x, int y) {
		super(x, y, 1, 1,
				Color.blue, // colour of gizmo
				1 // reflection coefficent
		);

		this.type = "Circle";

	}

	/**
	 * @see gizmos.AbstractGizmo#setGizShape
	 */
	@Override
	public void setGizShape(double x, double y) {
		
		
		setShape(new Ellipse2D.Double(
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

		addPhysicsCircle(x + radius, y + radius, radius);
		
	}

}
