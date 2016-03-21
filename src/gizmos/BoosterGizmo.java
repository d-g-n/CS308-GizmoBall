package gizmos;


import physics.Vect;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

public class BoosterGizmo extends AbstractGizmo {
	
	Ball boardBall = null;

	public BoosterGizmo(int x, int y, int w, int h) {
		super(x, y, 1, 1,
				Color.green, // colour of gizmo
				1// reflection coefficent
		);

		this.type = "Booster";

	}
	
	/**
	 * @See {@link gizmos.AbstractGizmo#setGizShape(double, double)}
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

	/**
	 * @see gizmos.AbstractGizmo#onHit(AbstractGizmo)
	 */
	@Override
	public void onHit(AbstractGizmo hit) {
		boardBall = ((Ball) hit);
		if(boardBall != null) {
			boardBall.setVelocity(new Vect(50, boardBall.getVelocity().y()));
		}
		super.onHit(hit);
	}

}
