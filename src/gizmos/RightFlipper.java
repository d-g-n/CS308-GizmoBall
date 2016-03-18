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
		super(x, y);

		rotateClockwise = true;

	}

	@Override
	public void doPhysicsCalculations() {

		if(flipperMoving){

			if(rotateClockwise) {
				flipClockwise(270);
			} else {
				flipAntiClockwise(180);
			}

		}
	}


}
