package gizmos;


import java.awt.Color;
import java.util.Arrays;

import physics.LineSegment;
import physics.Vect;

public class OuterWall extends AbstractGizmo {
	
	protected String position;

	public OuterWall(int x, int y, int width, int height, int degrees, String position) {
		super(x, y, width, height, degrees,
				Color.black, // colour of gizmo
				0.95 // reflection coefficent
		);

		this.position = position;
		LineSegment physicsLine;
		if(position.equals("TOP")){
			physicsLine = new LineSegment(new Vect(x,y),new Vect(x+width,y));
		}else if(position.equals("BOTTOM")){
			physicsLine = new LineSegment(new Vect(x,y+height),new Vect(x+width,y + height));
		}else if(position.equals("LEFT")){
			physicsLine = new LineSegment(new Vect(x,y),new Vect(x,y+height));
		}else{
			physicsLine = new LineSegment(new Vect(20,0),new Vect(20,20));
		}
		super.StoredLines.add(physicsLine);
		/*
		super.addPhysicsPath(Arrays.asList(
				new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));
*/
	}
}
