package gizmos;


import java.awt.*;

public class OuterWall extends AbstractGizmo {

	public OuterWall(int x, int y, int width, int height, int degrees) {
		super(x, y, width, height, degrees,
				Color.black, // colour of gizmo
				0.95 // reflection coefficent
		);
	}
}
