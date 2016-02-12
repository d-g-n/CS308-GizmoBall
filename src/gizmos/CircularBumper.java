package gizmos;

import java.awt.*;

public class CircularBumper extends AbstractGizmo {

	public CircularBumper(int x, int y, int width, int height, int degrees) {
		super(x, y, 1, 1, degrees,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);
	}

}
