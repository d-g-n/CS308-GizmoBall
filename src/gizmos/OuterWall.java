package gizmos;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import physics.Vect;

/**
 * The OuterWall is the representation of the surrounding walls of the
 * board. They are considered as a gizmo as they take part in the collisions
 * too.
 *
 */
public class OuterWall extends AbstractGizmo {

	public OuterWall(int x, int y, int w, int h) {
		super(x, y, w, h, Color.black, // colour of gizmo
				1 // reflection coefficent
		);

		this.setName("OuterWalls");

		this.type = "OuterWall";
	}

	/**
	 * @see gizmos.AbstractGizmo#setGizShape(double, double)
	 */
	@Override
	public void setGizShape(double x, double y) {
		setShape(new Rectangle2D.Double((xpos), (ypos), (0), (0)));
	}

	/**
	 * @see gizmos.AbstractGizmo#setGizPhysics(double, double)
	 */
	@Override
	public void setGizPhysics(double x, double y) {
		addPhysicsPath(Arrays.asList(new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));
	}

}
