package gizmos;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class CircleBumper extends AbstractGizmo {

	private double radius;

	public CircleBumper(int x, int y, int w, int h) {
		super(x, y, w, h, Color.blue, // colour of gizmo
				1 // reflection coefficent
		);

		this.radius = 0.5;

		addPhysicsCircle(xpos + radius, ypos + radius, radius);

		setShape(new Ellipse2D.Double((xpos), (ypos), (width), (height)));
	}

	@Override
	public void setGizShape(double x, double y) {

		setShape(new Ellipse2D.Double((x), (y), (width), (height)));

	}

	@Override
	public void setGizPhysics(double x, double y) {

		addPhysicsCircle(x + radius, y + radius, radius);

	}

	@Override
	public String getType() {
		return "Circle";
	}

}
