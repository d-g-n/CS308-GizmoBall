package gizmos;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import physics.Vect;
import view.Board;

public class CircleBumper extends AbstractGizmo {

	private double radius;

	public CircleBumper(int x, int y, int w, int h) {
		super(x, y, w, h,
				Color.blue, // colour of gizmo
				1 // reflection coefficent
		);

		this.radius = 0.5;

		addPhysicsCircle(xpos + radius, ypos + radius, radius);

		setShape(new Ellipse2D.Double(
				(xpos),
				(ypos),
				(width),
				(height)
		));
	}
	
	@Override
	public void moveGiz(int x,int y) {
		
		
		setShape(new Ellipse2D.Double(
				(x),
				(y),
				(width),
				(height)
		));
		
	}
	
	@Override
	public void movePhysics(int x,int y) {
		
		
		addPhysicsCircle(x + radius, y + radius, radius);
		
	}

}
