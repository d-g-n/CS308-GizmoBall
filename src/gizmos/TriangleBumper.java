package gizmos;


import physics.Vect;

import java.awt.*;
import java.util.Arrays;

public class TriangleBumper extends AbstractGizmo {

	public TriangleBumper(int x, int y, int w, int h) {
		super(x, y, 1, 1,
				Color.yellow, // colour of gizmo
				1// reflection coefficent
		);
		
		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos),
				new Vect(xpos + width, ypos + height),
				new Vect(xpos, ypos + height),
				new Vect(xpos, ypos)
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
