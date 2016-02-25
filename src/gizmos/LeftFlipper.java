package gizmos;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class LeftFlipper extends Flipper {

	public LeftFlipper(int x, int y) {

		super(x, y, 0);

		rotateClockwise = false;

	}


	@Override
	public Shape getShape() {

		if(flipperMoving){

			if(!rotateClockwise) {
				flipAntiClockwise(90);
			} else {
				flipClockwise(180);
			}

		}

		return super.getShape();
	}




}
