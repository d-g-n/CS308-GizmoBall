package gizmos;


import java.awt.*;

public class TriangleBumper extends AbstractGizmo {

	public TriangleBumper(int x, int y, int width, int height, int degrees) {
		super(x, y, 1, 1, degrees,
				Color.yellow, // colour of gizmo
				0.95 // reflection coefficent
		);
	}
}
