package gizmos;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class LeftFlipper extends AbstractGizmo {
	private boolean moving,forward;
	double angleVel, rotation;
	Shape flipper;
	Color color;

	public LeftFlipper(int x, int y, int w, int h) {

		super(x, y, 2, 2,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficent
		);
		initialize();

		setShape(new RoundRectangle2D.Double(
				(xpos),
				(ypos),
				(width),
				(height) * 0.25,
				25,
				100
		));
	}

	public void rotate() {
		if (moving){
			if(forward){
				rotation -= angleVel;
			}else{
				rotation += angleVel;
			}

			if(rotation <= 270){
				stop();
				rotation = 270;
			}
			if(rotation >= 360){
				stop();
				rotation = 360;
			}

		AffineTransform at = new AffineTransform();
		Shape shape = new RoundRectangle2D.Double(xpos, ypos, width, height, 10, 40);
		at.setToRotation(Math.toRadians(rotation), xpos + width / 2, ypos + width / 2);
		Shape path = at.createTransformedShape(shape);
		this.setShape(path);
		}

	}

	public void move() {
		moving = true;
	}

	public void stop() {
		moving = false;
	}

	public void setShape(Shape s) {
		flipper = s;
	}

	@Override
	public Shape getShape() {
		return flipper;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setForward(){
		forward = !forward;
	}

	public void setMoving() {
		moving = !moving;
	}

	public Color getColor() {
		return color;
	}

	public void updatePhysics() {
		// update physics...
	}

	public void initialize() {
		angleVel = 1080 * 0.005;
		rotation = 360;
		flipper = new RoundRectangle2D.Double(xpos, ypos, width, height, 10, 40.0);
		color = Color.RED;
		moving = false;
		forward = false;
	}

	public void stopForward() {
		// TODO Auto-generated method stub
		forward = false;
	}

	public void moveForward() {
		forward = true;
	}

}
