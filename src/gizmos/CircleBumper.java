package gizmos;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * The CircleBumper class represents the circular bumpers
 * on the board.
 *
 */
public class CircleBumper extends AbstractGizmo {

	private double radius;

	public CircleBumper(int x, int y, int w, int h) {
		super(x, y, w, h,
				Color.blue, // colour of gizmo
				1 // reflection coefficent
		);

		this.type = "Circle";

	}
	
	/**
	 * @see gizmos.AbstractGizmo#setGizShape(double, double)
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
	 * @see gizmos.AbstractGizmo#setGizPhysics(double, double)
	 */
	@Override
	public void setGizPhysics(double x, double y) {
		this.radius = 0.5;
		addPhysicsCircle(x + radius, y + radius, radius);
	}
}
