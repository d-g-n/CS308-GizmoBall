package gizmos;

import java.awt.*;

public class CircleBumper extends AbstractGizmo {

	public CircleBumper(int x, int y, int width, int height, int degrees) {
		super(x, y, 1, 1, degrees,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);

		this.addPhysicsCircle(
				x + (this.getWidth() / 2),
				y + (this.getHeight() / 2),
				this.getWidth()
		);
	}

}
