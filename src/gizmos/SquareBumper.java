package gizmos;


import physics.Vect;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * The SquareBumper class is a representation of a square box
 * bumper or the board.
 * 
 */
public class SquareBumper extends AbstractGizmo {
	private int score;
	
	public SquareBumper(int x, int y, int w, int h) {
		super(x, y, w, h,
				Color.red, // colour of gizmo
				1 // reflection coefficent
		);
		score = 10;
		this.type = "Square";
	}
	
	/**
	 * @see gizmos.AbstractGizmo#setGizShape(double, double)
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
	 * @see gizmos.AbstractGizmo#setGizPhysics(double, double)
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
