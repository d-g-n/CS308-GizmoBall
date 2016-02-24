package gizmos;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import view.Board;

public class CircleBumper extends AbstractGizmo {

	private double radius;

	public CircleBumper(int x, int y, int w, int h) {
		super(x, y, w, h,
				Color.blue, // colour of gizmo
				1 // reflection coefficent
		);

		this.radius = Board.BOARD_WIDTH / Board.X_CELLS / 2;

		addPhysicsCircle(xpos + radius, ypos + radius, radius);

		setShape(new Ellipse2D.Double(
				(xpos),
				(ypos),
				(width),
				(height)
		));
	}

}
