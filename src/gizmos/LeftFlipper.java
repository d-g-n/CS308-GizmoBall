package gizmos;


import java.awt.*;

public class LeftFlipper extends AbstractGizmo {

	public LeftFlipper(int x, int y, int width, int height, int degrees) {

		super(x, y, 2, 2, degrees,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);
	}
}
