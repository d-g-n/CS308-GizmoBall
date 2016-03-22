package gizmos;

import physics.Vect;
import view.Board;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class Flipper extends AbstractGizmo {
	boolean flipperMoving, rotateClockwise;
	double flipRotation, localxpos;

	public Flipper(int x, int y) {

		super(x, y, 2, 2,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);

		// flipper specific things


		flipperMoving = false;
		angleVel = 1080*Board.MOVE_TIME;
		flipRotation = 180; // because it starts pointing down and the pivot point is above it i guess

	}

	@Override
	public void setGizShape(double x, double y) {

		localxpos = xpos + ((this.getClass().equals(LeftFlipper.class) ? 0 : 0.75) * width); // this is really bad

		rotateAroundPoint = new Vect(localxpos + (width * 0.125), ypos + (height * 0.125));

		setShape(new RoundRectangle2D.Double(
				(localxpos),
				(ypos),
				(width) * 0.25,
				(height),
				0.25,
				0.25
		));

	}

	@Override
	public void setGizPhysics(double x, double y) {

		localxpos = xpos + ((this.getClass().equals(LeftFlipper.class) ? 0 : 0.75) * width); // really really really bad

		rotateAroundPoint = new Vect(localxpos + (width * 0.125), ypos + (height * 0.125));

		addPhysicsPath(Arrays.asList(
				new Vect(localxpos, ypos),
				new Vect(localxpos + (width * 0.25), ypos),
				new Vect(localxpos + (width * 0.25), ypos + height),
				new Vect(localxpos, ypos + height),
				new Vect(localxpos, ypos)
		));

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

			double localAngVel = angleVel;

			AffineTransform at = new AffineTransform();

			if((flipRotation + localAngVel) > toDegrees)
				localAngVel = toDegrees - flipRotation;

			// note anglevel is the degrees to rotate this draw iteration

			at.rotate(Math.toRadians(localAngVel), rotateAroundPoint.x(), rotateAroundPoint.y());

			super.rotatePhysicsAroundPoint(rotateAroundPoint, localAngVel);


			Shape path = at.createTransformedShape(super.getShape());

			super.setShape(path);

			flipRotation += localAngVel; // because it rotates counterclockwise

		}
	}

	public void flipAntiClockwise(int toDegrees){
		if (flipRotation <= toDegrees) {

			rotateClockwise = true;
			flipperMoving = false;
			flipRotation = toDegrees; // make it start going in reverse or something

		} else {

			double localAngVel = angleVel;

			AffineTransform at = new AffineTransform();

			if((flipRotation - localAngVel) < toDegrees)
				localAngVel = flipRotation - toDegrees;

			at.rotate(Math.toRadians(-localAngVel), rotateAroundPoint.x(), rotateAroundPoint.y());

			super.rotatePhysicsAroundPoint(rotateAroundPoint, -localAngVel);


			Shape path = at.createTransformedShape(super.getShape());

			super.setShape(path);

			flipRotation -= localAngVel; // because it rotates counterclockwise

		}
	}

	@Override
	public double getAngularVelocity(){
		return (flipperMoving ? Math.toRadians(rotateClockwise ? angleVel : -angleVel) : 0.0);
	}


}
