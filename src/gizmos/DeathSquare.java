package gizmos;


import physics.Vect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class DeathSquare extends AbstractGizmo {
	
	Ball boardBall = null;

	public DeathSquare(int x, int y, int w, int h) {
		super(x, y, w, h,
				Color.GRAY, // colour of gizmo
				1 // reflection coefficent
		);
		
		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));


		setShape(new Rectangle2D.Double(
				(xpos),
				(ypos),
				(width),
				(height)
		));
	}
	
	@Override
	public void setGizShape(double x, double y) {
		
		
		setShape(new Rectangle2D.Double(
				(x),
				(y),
				(width),
				(height)
		));
		
	}
	
	@Override
	public void setGizPhysics(double x, double y) {
		
		
		addPhysicsPath(Arrays.asList(
				new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));
		
	}
	
	@Override
	public void onHit(AbstractGizmo hit) {

		boardBall = ((Ball) hit);

		if(boardBall != null) {
			boardBall.setVelocity(new Vect(0,0));
		}

		super.onHit(hit);
	}
	
	
	
	

}
