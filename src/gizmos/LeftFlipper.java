package gizmos;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class LeftFlipper extends AbstractGizmo {
	private boolean moving;
	private Vect[] points;
	private Vect center;
	int angleVel;

	public LeftFlipper(double x, double y, double w, double h, int degrees) {

		super(x, y, w, h, degrees, Color.red, // colour of gizmo
				0.95 // reflection coefficient
		);
		initialize();
	}

	@Override
	public void rotate(int angle) {
		for (int i = 0; i < points.length; i++) {
			System.out.println(angle);
			System.out.println("Before Rotation " + "x: " + points[i].x() + " y: " + points[i].y());
			points[i] = Geometry.rotateAround(points[i], center, new Angle(Math.toRadians(angle)));
			System.out.println("AfterRotation " + "x: " + points[i].x() + " y: " + points[i].y());
			updatePhysics();
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

	@Override
	public boolean isMoving() {
		return moving;
	}

	@Override
	public void setMoving() {
		moving = !moving;
	}

	public void updatePhysics() {
		this.StoredCircles.clear();
		this.StoredLines.clear();
		
		//physics circles
		this.addPhysicsCircle(new Circle(points[0],0.0));
		this.addPhysicsCircle(new Circle(points[1],0.0));
		this.addPhysicsCircle(new Circle(points[2],0.0));
		this.addPhysicsCircle(new Circle(points[3],0.0));
		//physics lines
		this.addPhysicsLine(new LineSegment(points[0],points[1]));
		this.addPhysicsLine(new LineSegment(points[1],points[2]));
		this.addPhysicsLine(new LineSegment(points[2],points[3]));
		this.addPhysicsLine(new LineSegment(points[3],points[0]));
	}
	
	public void initialize(){
		//Initialise origin points of the flipper
		center = new Vect(xpos + width / 2, ypos + width / 2);
		points = new Vect[4];
		points[0] = new Vect(xpos, ypos);
		points[1] = new Vect(xpos + width, ypos);
		points[2] = new Vect(xpos + width, ypos + height);
		points[3] = new Vect(xpos, ypos + height);
		//physics circles
		this.addPhysicsCircle(new Circle(points[0],0.0));
		this.addPhysicsCircle(new Circle(points[1],0.0));
		this.addPhysicsCircle(new Circle(points[2],0.0));
		this.addPhysicsCircle(new Circle(points[3],0.0));
		//physics lines
		this.addPhysicsLine(new LineSegment(points[0],points[1]));
		this.addPhysicsLine(new LineSegment(points[1],points[2]));
		this.addPhysicsLine(new LineSegment(points[2],points[3]));
		this.addPhysicsLine(new LineSegment(points[3],points[0]));
	}
	
}
