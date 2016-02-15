package gizmos;


import physics.Vect;

import java.awt.*;
import java.util.Arrays;

public class LeftFlipper extends AbstractGizmo {

	public LeftFlipper(int x, int y, int width, int height, int degrees) {

		super(x, y, 2, 2, degrees,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);

		addPhysicsPath(Arrays.asList(
				new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));

	}
}
