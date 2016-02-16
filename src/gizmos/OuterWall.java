package gizmos;


import java.awt.Color;
import java.util.Arrays;
import physics.Vect;

public class OuterWall extends AbstractGizmo {
	

	public OuterWall(int x, int y, int width, int height, int degrees) {
		super(x, y, width, height, degrees,
				Color.black, // colour of gizmo
				0.95 // reflection coefficent
		);

		
		super.addPhysicsPath(Arrays.asList(
				new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));
	}
}
