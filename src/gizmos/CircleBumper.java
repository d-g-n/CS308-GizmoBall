package gizmos;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import physics.Vect;
import view.Board;

public class CircleBumper extends AbstractGizmo {

	private final double radius = 0.5;


	public CircleBumper(int x, int y, int w, int h) {
		super(x, y, w, h,
				Color.blue, // colour of gizmo
				1 // reflection coefficent
		);

		this.type = "Circle";

	}
	
	@Override
	public void setGizShape(double x, double y) {
		
		
		setShape(new Ellipse2D.Double(
				(x),
				(y),
				(width),
				(height)
		));
		
	}
	
	@Override
	public void setGizPhysics(double x, double y) {

		addPhysicsCircle(x + radius, y + radius, radius);
		
	}

}
