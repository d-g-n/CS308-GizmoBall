package gizmos;

import model.GizmoConstants;
import physics.Vect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

/**
 * Created by Declan on 22/03/2016.
 */
public class Spinner extends AbstractGizmo {

	public Spinner(int x, int y) {

		super(x, y, 2, 2,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);

		// flipper specific things


		angleVel = Math.toRadians(1080 * GizmoConstants.MOVE_TIME);

	}

	/**
	 * @see gizmos.AbstractGizmo#setGizShape
	 */
	@Override
	public void setGizShape(double x, double y) {
		setShape(new RoundRectangle2D.Double(
				(xpos),
				(ypos) + (1) - ((height) * 0.25)/2,
				(width),
				(height) * 0.25,
				0.25,
				0.25
		));

	}

	/**
	 * @see gizmos.AbstractGizmo#setGizPhysics
	 */
	@Override
	public void setGizPhysics(double x, double y) {

		double localy = (ypos) + (1) - ((height) * 0.25)/2;

		addPhysicsPath(Arrays.asList(
				new Vect(xpos, localy),
				new Vect(xpos + (width ), localy),
				new Vect(xpos + (width), localy + (height*0.25)),
				new Vect(xpos, localy + (height*0.25)),
				new Vect(xpos, localy)
		));


	}

	@Override
	public void doPhysicsCalculations(){

		AffineTransform at = new AffineTransform();


		at.rotate(angleVel, rotateAroundPoint.x(), rotateAroundPoint.y());

		super.rotatePhysicsAroundPoint(rotateAroundPoint, Math.toDegrees(angleVel));


		Shape path = at.createTransformedShape(super.getShape());

		super.setShape(path);


	}
}
