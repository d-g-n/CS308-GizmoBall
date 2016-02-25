package gizmos;

import physics.Vect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class Flipper extends AbstractGizmo {
	boolean flipperMoving;
	double angleVel, flipRotation;

	public Flipper(int x, int y, double xmod) {

		super(x, y, 2, 2,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);

		xpos = xpos + (xmod * width);

		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos),
				new Vect(xpos + (width * 0.25), ypos),
				new Vect(xpos + (width * 0.25), ypos + height),
				new Vect(xpos, ypos + height),
				new Vect(xpos, ypos)
		));

		setShape(new RoundRectangle2D.Double(
				(xpos),
				(ypos),
				(width) * 0.25,
				(height),
				100,
				25
		));

		// flipper specific things

		flipperMoving = false;
		angleVel = 1080 * 0.025;
		flipRotation = 180; // because it starts pointing down and the pivot point is above it i guess

		// FOR DEBUGGING, ADD ALL FLIPPERS TO THEIR OWN LISTENER LIST TO ENABLE AUTO FLIPPING

		this.addGizmoListener(this);
	}


}
