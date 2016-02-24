package gizmos;


import physics.Vect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class SquareBumper extends AbstractGizmo {

	public SquareBumper(int x, int y, int w, int h) {
		super(x, y, w, h,
				Color.red, // colour of gizmo
				1 // reflection coefficent
		);
		
		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));


		setShape(new Rectangle2D.Double(
				(xpos),
				(ypos),
				(width),
				(height)
		));
	}

}
