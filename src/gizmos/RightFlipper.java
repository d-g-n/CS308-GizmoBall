package gizmos;


import physics.Vect;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.*;
import java.util.Arrays;

public class RightFlipper extends Flipper {


	public RightFlipper(int x, int y) {
		super(x, y, 0.75);

	}

	@Override
	public void doTrigger(){
		this.flipperMoving = true;
	}

	@Override
	public Shape getShape() {

		if(flipperMoving){

			if (flipRotation >= 270) {

				flipperMoving = false;
				flipRotation = 270; // make it start going in reverse or something

			} else {

				AffineTransform at = new AffineTransform();

				if((flipRotation + angleVel) > 270)
					angleVel = 270 - flipRotation;

				// note anglevel is the degrees to rotate this draw iteration

				at.rotate(Math.toRadians(angleVel), xpos + (width * 0.125), ypos + (height * 0.125));

				super.rotatePhysicsAroundPoint(xpos + (width * 0.125), ypos + (height * 0.125), angleVel);


				Shape path = at.createTransformedShape(super.getShape());

				super.setShape(path);

				flipRotation += angleVel; // because it rotates counterclockwise

			}

		}

		// set the physics to map to the bounding box of the 2d shape



		return super.getShape();
	}


}
