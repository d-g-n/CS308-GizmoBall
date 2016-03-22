package gizmos;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Arrays;
import physics.Vect;

/**
 * The TriangleBumper class represents a triangle bumper object on the board.
 *
 */
public class TriangleBumper extends AbstractGizmo {

	public TriangleBumper(int x, int y, int w, int h) {
		super(x, y, 1, 1,
				Color.yellow, // colour of gizmo
				1// reflection coefficent
		);

		this.type = "Triangle";

	}
	
	
	/**
	 * @see gizmos.AbstractGizmo#setGizShape(double, double)
	 */
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
	/**
	 * @see gizmos.AbstractGizmo#setGizPhysics(double, double)
	 */
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
