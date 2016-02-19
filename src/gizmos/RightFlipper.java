package gizmos;


import physics.Angle;
import physics.Geometry;
import physics.Vect;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

public class RightFlipper extends AbstractGizmo {
	
	private boolean moving;
	private Vect[] points;
	private Vect center;

	public RightFlipper(double x, double y, double w, double h, int degrees) {
		super(x, y, w, h, degrees,
				Color.orange, // colour of gizmo
				0.95 // reflection coefficent
		);
		
		center = new Vect(xpos + width / 2, ypos + width / 2);
		points = new Vect[4];
		points[0] = new Vect(xpos, ypos);
		points[1] = new Vect(xpos + width, ypos);
		points[2] = new Vect(xpos + width, ypos + height);
		points[3] = new Vect(xpos, ypos + height);
	}
	
	@Override
	public void rotate(int angle) {
		for (int i = 0; i < points.length; i++) {
			points[i] = Geometry.rotateAround(points[i], center, new Angle(Math.toRadians(angle)));
			System.out.println("x: " + points[i].x() + " y: " + points[i].y());
		}
	}
	
	@Override
	public Shape getShape() {

		GeneralPath flipper = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		flipper.moveTo(points[0].x(), points[0].y());
		flipper.lineTo((points[1].x()), (points[1].y()));
		flipper.lineTo((points[2].x()), points[2].y());
		flipper.lineTo((points[3].x()), (points[3].y()));
		flipper.lineTo(points[0].x(), points[0].y());
		return flipper;
	}
}
