package gizmos;


import physics.Vect;
import view.Board;

import java.awt.*;
import java.util.Arrays;

public class SquareBumper extends AbstractGizmo {

	public SquareBumper(int x, int y, int width, int height, int degrees) {
		super(x, y, 1, 1, degrees,
				Color.red, // colour of gizmo
				0.95 // reflection coefficent
		);
		
		
		
		double pixelsY = (y * Board.BOARD_HEIGHT / Board.Y_CELLS);
		double pixelsX = (x * Board.BOARD_WIDTH / Board.X_CELLS);
		double localWidth = (Board.BOARD_WIDTH / Board.X_CELLS);
		double localHeight = Board.BOARD_HEIGHT/ Board.Y_CELLS;
		

		addPhysicsPath(Arrays.asList(
				new Vect(pixelsX, pixelsY), // start at top left
				new Vect(pixelsX + localWidth, pixelsY), // move to top right
				new Vect(pixelsX + localWidth, pixelsY + localHeight), // move to bottom right
				new Vect(pixelsX, pixelsY + localHeight), // move to bottom left
				new Vect(pixelsX, pixelsY) // and back up to top left
		));

	}

}
