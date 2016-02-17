package gizmos;

import java.awt.*;

import view.Board;

public class CircularBumper extends AbstractGizmo {

	public CircularBumper(int x, int y, int width, int height, int degrees) {
		super(x, y, 1, 1, degrees,
				Color.blue, // colour of gizmo
				1 // reflection coefficent
		);
		
		double radius = Board.BOARD_WIDTH / Board.X_CELLS / 2;

		addPhysicsCircle( (x * Board.BOARD_WIDTH / Board.X_CELLS) + radius,
				(y * Board.BOARD_HEIGHT / Board.Y_CELLS) + radius,
				radius
		);
	}

}
