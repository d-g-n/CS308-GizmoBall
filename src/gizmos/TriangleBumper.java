package gizmos;


import physics.Vect;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

public class TriangleBumper extends AbstractGizmo {

	public TriangleBumper(int x, int y, int w, int h) {
		super(x, y, 1, 1,
				Color.yellow, // colour of gizmo
				1// reflection coefficent
		);

	}
	
	
	@Override
	public void setGizShape(double x, double y) {
		
		
		Shape shape = new Polygon();
		//Add the three points of the triangle to the shape

				/*
				 *  *
				 *  - -
				 */
		((Polygon) shape).addPoint(
				(int) (x),
				(int) (y)
		);

				/*
				 *  -
				 *  - *
				 */
		((Polygon) shape).addPoint(
				(int) ((x) + (width)),
				(int) ((y) + (height))
		);

				/*
				 *  -
				 *  * -
				 */
		((Polygon) shape).addPoint(
				(int) (x),
				(int) ((y) + (height))
		);

		setShape(shape);
		
	}
	
	@Override
	public void setGizPhysics(double x, double y) {
		
		
		addPhysicsPath(Arrays.asList(
				new Vect(x, y),
				new Vect(x + width, y + height),
				new Vect(x, y + height),
				new Vect(x, y)
		));
		
	}
}
