package gizmos;

import physics.Vect;
import view.Board;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class Flipper extends AbstractGizmo {
	boolean flipperMoving, rotateClockwise;
	double flipRotation;

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
				0.25,
				0.25
		));

		// flipper specific things

		rotateAroundPoint = new Vect(xpos + (width * 0.125), ypos + (height * 0.125));
		flipperMoving = false;
		angleVel = 1080 * Board.MOVE_TIME;
		flipRotation = 180; // because it starts pointing down and the pivot point is above it i guess

		// FOR DEBUGGING, ADD ALL FLIPPERS TO THEIR OWN LISTENER LIST TO ENABLE AUTO FLIPPING

		this.addGizmoListener(this);
	}

	// note to whoever, this is fired whenever a button that's linked to this gizmo is pressed or if
	// the ball touches another gizmo that's linked to this gizmo
	// to debug all flippers are linked to themselves as in the Flipper class
	@Override
	public void doTrigger(){
		this.flipperMoving = true;
	}

	public void flipClockwise(int toDegrees){
		if (flipRotation >= toDegrees) {

			rotateClockwise = false;
			flipperMoving = false;
			flipRotation = toDegrees; // make it start going in reverse or something

		} else {

			AffineTransform at = new AffineTransform();

			if((flipRotation + angleVel) > toDegrees)
				angleVel = toDegrees - flipRotation;

			// note anglevel is the degrees to rotate this draw iteration

			at.rotate(Math.toRadians(angleVel), rotateAroundPoint.x(), rotateAroundPoint.y());

			super.rotatePhysicsAroundPoint(rotateAroundPoint, angleVel);


			Shape path = at.createTransformedShape(super.getShape());

			super.setShape(path);

			flipRotation += angleVel; // because it rotates counterclockwise

		}
	}

	public void flipAntiClockwise(int toDegrees){
		if (flipRotation <= toDegrees) {

			rotateClockwise = true;
			flipperMoving = false;
			flipRotation = toDegrees; // make it start going in reverse or something

		} else {

			AffineTransform at = new AffineTransform();

			if((flipRotation - angleVel) < toDegrees)
				angleVel = flipRotation - toDegrees;

			at.rotate(Math.toRadians(-angleVel), rotateAroundPoint.x(), rotateAroundPoint.y());

			super.rotatePhysicsAroundPoint(rotateAroundPoint, -angleVel);


			Shape path = at.createTransformedShape(super.getShape());

			super.setShape(path);

			flipRotation -= angleVel; // because it rotates counterclockwise

		}
	}

	@Override
	public double getAngularVelocity(){
		return (rotateClockwise ? angleVel : -angleVel);
	}


}
