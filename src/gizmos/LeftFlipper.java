package gizmos;


import physics.Vect;

import java.awt.*;
import java.util.Arrays;

public class LeftFlipper extends AbstractGizmo {

	public LeftFlipper(int x, int y, int w, int h, int degrees) {

		super(x, y, w, h, degrees,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);

		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));

	}
}
