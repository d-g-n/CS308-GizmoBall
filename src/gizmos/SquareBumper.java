package gizmos;

import physics.Vect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class SquareBumper extends AbstractGizmo {

	public SquareBumper(int x, int y, int w, int h) {
		super(x, y, w, h, Color.red, // colour of gizmo
				1 // reflection coefficent
		);

	}

	@Override
	public void setGizShape(double x, double y) {

		setShape(new Rectangle2D.Double((x), (y), (width), (height)));

	}

	@Override
	public void setGizPhysics(double x, double y) {

		addPhysicsPath(Arrays.asList(new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));

	}

	@Override
	public String getType() {
		return "Square";
	}

}
