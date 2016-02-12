package gizmos;


import java.awt.*;

public class RightFlipper extends AbstractGizmo {

	public RightFlipper(int x, int y, int width, int height, int degrees) {
		super(x, y, 2, 2, degrees,
				Color.magenta, // colour of gizmo
				0.95 // reflection coefficent
		);
	}
}
