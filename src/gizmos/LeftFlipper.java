package gizmos;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class LeftFlipper extends Flipper {

	public LeftFlipper(int x, int y) {

		super(x, y, 0);



	}

	// note to whoever, this is fired whenever a button that's linked to this gizmo is pressed or if
	// the ball touches another gizmo that's linked to this gizmo
	// to debug all flippers are linked to themselves as in the Flipper class
	@Override
	public void doTrigger(){
		this.flipperMoving = true;
	}

	@Override
	public Shape getShape() {

		if(flipperMoving){

			if (flipRotation <= 90) {

				flipperMoving = false;
				flipRotation = 90; // make it start going in reverse or something

			} else {

				AffineTransform at = new AffineTransform();

				at.rotate(Math.toRadians(-angleVel), xpos + (width * 0.125), ypos + (height * 0.125));


				Shape path = at.createTransformedShape(super.getShape());

				super.setShape(path);

				flipRotation -= angleVel; // because it rotates counterclockwise

			}

		}

		return super.getShape();
	}




}
