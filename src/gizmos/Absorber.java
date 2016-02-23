package gizmos;


import physics.Vect;
import view.Board;
import model.ProjectManager;

import java.awt.*;
import java.util.Arrays;

public class Absorber extends AbstractGizmo {
	
	


	public Absorber(int x, int y, int width, int height, int degrees) {

		super(x, y, width, height, degrees,
				Color.magenta, // colour of gizmo
				1.0 // reflection coefficent
		);


		double pixelsY = (y * Board.BOARD_HEIGHT / Board.Y_CELLS);
		double pixelsX = (x * Board.BOARD_WIDTH / Board.X_CELLS);
		double localWidth = (Board.BOARD_WIDTH / Board.X_CELLS);
		double localHeight = Board.BOARD_HEIGHT/ Board.Y_CELLS;
		

		addPhysicsPath(Arrays.asList(
				new Vect(pixelsX, pixelsY), // start at top left
				new Vect(pixelsX + (localWidth * width), pixelsY), // move to top right
				new Vect(pixelsX + (localWidth * width), pixelsY + (localHeight * height)), // move to bottom right
				new Vect(pixelsX, pixelsY + (localHeight * height)), // move to bottom left
				new Vect(pixelsX, pixelsY) // and back up to top left
		));

	}


	/**
	 * This is an example of how to extend abstract behaviour to provide additional behaviour
	 */
	@Override
	public void onHit() {
		super.onHit();
		
		

	}

	/**
	 * Like above, will want to set the velocity of the ball if the ball is currently being held
	 */
	@Override
	public void onCollision() {
	
		super.onCollision();

		
		// if ball is held, chuck it back out

	}
	
}
