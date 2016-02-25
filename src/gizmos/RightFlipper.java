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

		rotateClockwise = true;

	}

	@Override
	public Shape getShape() {

		if(flipperMoving){

			if(rotateClockwise) {
				flipClockwise(270);
			} else {
				flipAntiClockwise(180);
			}

		}

		return super.getShape();
	}


}
