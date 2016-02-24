package gizmos;


import physics.Vect;

import java.awt.*;
import java.util.Arrays;

public class TriangleBumper extends AbstractGizmo {

	public TriangleBumper(int x, int y, int w, int h, int degrees) {
		super(x, y, w, h, degrees,
				Color.yellow, // colour of gizmo
				1// reflection coefficent
		);
		
		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos), // start at top left
				new Vect(xpos, ypos + height), // move to top right
				new Vect(xpos + width, ypos), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));

		Shape shape = new Polygon();
		//Add the three points of the triangle to the shape

				/*
				 *  *
				 *  - -
				 */
		((Polygon) shape).addPoint(
				(int) (xpos),
				(int) (ypos)
		);

				/*
				 *  -
				 *  - *
				 */
		((Polygon) shape).addPoint(
				(int) ((xpos) + (width)),
				(int) ((ypos) + (height))
		);

				/*
				 *  -
				 *  * -
				 */
		((Polygon) shape).addPoint(
				(int) (xpos),
				(int) ((ypos) + (height))
		);

		setShape(shape);
	}
}
