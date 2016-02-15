package gizmos;


import physics.Vect;
import model.ProjectManager;

import java.awt.*;
import java.util.Arrays;

public class Absorber extends AbstractGizmo {
	
	


	public Absorber(int x, int y, int width, int height, int degrees) {

		super(x, y, width, height, degrees,
				Color.magenta, // colour of gizmo
				0.95 // reflection coefficent
		);


		addPhysicsPath(Arrays.asList(
				new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
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
