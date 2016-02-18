package gizmos;


import java.awt.Color;
import java.util.Arrays;
import physics.Vect;

public class OuterWall extends AbstractGizmo {
	

	public OuterWall(int x, int y, int w, int h, int degrees) {
		super(x, y, w, h, degrees,
				Color.black, // colour of gizmo
				1 // reflection coefficent
		);

		
		super.addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));
	}
}
