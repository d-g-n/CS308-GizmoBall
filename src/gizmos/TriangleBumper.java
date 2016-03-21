package gizmos;


import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Arrays;

import physics.Vect;

public class TriangleBumper extends AbstractGizmo {

	public TriangleBumper(int x, int y) {
		super(x, y, 1, 1,
				Color.yellow, // colour of gizmo
				1// reflection coefficent
		);

		this.type = "Triangle";

	}
	
	
	@Override
	public void setGizShape(double x, double y) {
		
		
		Shape shape = new Polygon();
		//Add the three points of the triangle to the shape

				/*
				 *  * -
				 *  - 
				 */
		((Polygon) shape).addPoint(
				(int) (x),
				(int) (y)
		);

				/*
				 *  - *
				 *  - 
				 */
		((Polygon) shape).addPoint(
				(int) ((x) + (width)),
				(int) (y)
		);

				/*
				 *  - -
				 *  * 
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
				new Vect(x, y),  // start at the top left
				new Vect(x + width, y), // move to the top right
				new Vect(x, y + height), // move to the bottom left
				new Vect(x, y) // and finally, back to the top left
		));
		
	}

}
