package gizmos;

import java.awt.*;

import view.Board;

public class CircularBumper extends AbstractGizmo {

	private double radius;

	public CircularBumper(int x, int y, int w, int h, int degrees) {
		super(x, y, w, h, degrees, Color.blue, // colour of gizmo
				1 // reflection coefficent
		);

		this.radius = Board.BOARD_WIDTH / Board.X_CELLS / 2;

		addPhysicsCircle(xpos + radius, ypos + radius, radius);
	}

}
