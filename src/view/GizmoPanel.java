package view;

import gizmos.AbstractGizmo;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import model.ProjectManager;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Created by Declan on 06/02/2016.
 */
public class GizmoPanel extends JPanel {

	// CONSTANTS
	private final static int CONS_SIZE = 20; // the x and y sizes the board should draw
	private final static boolean ENABLE_DEBUG = true;
	private final static double SQUARE_WIDTH = (RunGUI.BOARD_HEIGHT / CONS_SIZE); // this is how many pixels wide each square is
	private final static double SQUARE_HEIGHT = (RunGUI.BOARD_WIDTH / CONS_SIZE); // this is how many pixels high each square is


	// OTHER VARS

	private ProjectManager pm;

	public GizmoPanel(ProjectManager pm){

		this.pm = pm;

	}

	private void paintBackground(Graphics g){

		((Graphics2D) g).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

		// Draw background

		g.setColor(Color.black);
		g.fillRect(0, 0, RunGUI.BOARD_WIDTH, RunGUI.BOARD_HEIGHT);

		// Set guideline colour

		g.setColor(Color.darkGray);

		// Draw guidelines on x axis

		for(int i = 1; i <= CONS_SIZE; i++){
			g.drawLine(
					(int) (SQUARE_WIDTH * i),
					0,
					(int) (SQUARE_WIDTH * i),
					RunGUI.BOARD_HEIGHT
			);
		}

		// Draw Guidelines on y axis

		for(int i = 1; i <= CONS_SIZE; i++){
			g.drawLine(
					0,
					(int) (SQUARE_HEIGHT * i),
					RunGUI.BOARD_WIDTH,
					(int) (SQUARE_HEIGHT * i)
			);
		}
	}

	private void paintGizmos(Graphics g){

		Graphics2D g2d = (Graphics2D) g;

		for(AbstractGizmo giz : pm.getBoardGizmos()) {

			// Strip out gizmo vars to slightly easier to access names

			double gizXpos = giz.getXpos();
			double gizYpos = giz.getYpos();
			double gizWidth = giz.getWidth();
			double gizHeight = giz.getHeight();
			Angle gizAngle = giz.getGizAngle();


			// MAIN manual drawcode to define gizmo components

			for (VisualShape vs : giz.getStoredVisualShapes()) {

				// Set AffineTransform so we can reset it, otherwise the rotations will persist across objects

				AffineTransform pT = g2d.getTransform();

				// EXAMPLE CODE on how to actually do the flipper triggering and stuff
				// we're using percentX = 0.125 and percentY = 0.125 because if you look at the json, that's the midpoint
				// of the circle on the left so we use that as the pivot point

				if(giz.getClass().equals(RightFlipper.class)){

					g2d.rotate(
							Math.toRadians(System.currentTimeMillis()/10), // arbritrary incrementing variable to test
							(gizXpos * SQUARE_WIDTH) + (0.125 * (SQUARE_WIDTH * gizWidth)),
							(gizYpos * SQUARE_HEIGHT) + (0.125 * (SQUARE_HEIGHT * gizHeight))
					);

				}

				if(giz.getClass().equals(LeftFlipper.class)){

					g2d.rotate(
							Math.toRadians(System.currentTimeMillis()/10), // arbritrary incrementing variable to test
							(gizXpos * SQUARE_WIDTH) + (0.875 * (SQUARE_WIDTH * gizWidth)),
							(gizYpos * SQUARE_HEIGHT) + (0.125 * (SQUARE_HEIGHT * gizHeight))
					);

				}


				// Define our common variables that are loaded in from JSON
				// in ["Circle", [x, y], radius] .get(0) is x, 1 is y, 2 is radius
				// in ["Square", [x, y], [w, h] .get(0) is x, 1 is y, 2 is w, 3 is h

				Shape shape = new Polygon();

				double percentX = vs.getValList().get(0) / 100;
				double percentY = vs.getValList().get(1) / 100;
				double percentWidth = 1;
				double percentHeight = 1;
				double percentRadius = 1;

				if(vs.getType().equals("Circle")){
					percentRadius = (vs.getValList().get(2) * 2) / 100; // mult by 2 cos we want the diameter for drawing
				} else {
					percentWidth = vs.getValList().get(2) / 100;
					percentHeight = vs.getValList().get(3) / 100;
				}


				// Main if stack for the manual definitions of the component shapes
				// explanation for xpos etc
				// gizXpos is the int of what x square the gizmo is in from 0 to 19, multiplying that by the pixel width
				// which is SQUARE_WIDTH gives you the number of pixels on the x that the top left corner of the square
				// the gizmo should be drawn in is at

				// the percentWhatever values are the converted 0-100 values from the JSON file that determine where in
				// the 1x1 or 2x2 or whatever grid the specific visual shape appears at.
				// as SQUARE_WIDTH is the pixel length of one visible square, multiplying that by gizWidth gives us
				// the pixel length of the entire gizmo, then multiplying that by percentX (keep in mint that percentX)
				// was divided by 100 above so is now a value from 0.00 to 1.00, doing that gives us how many pixels
				// along this shape should start at

				// apply this similar logic for width and height calcs as above, gizWidth * SQUARE_WIDTH gives you
				// the pixel width of the entire shape space, then all you need to do is mult that by percentWidth
				// and that scales it to whatever is specified in the json

				if (vs.getType().equals("Square")) {

					shape = new Rectangle2D.Double(
							(gizXpos * SQUARE_WIDTH) + (percentX * (SQUARE_WIDTH * gizWidth)),
							(gizYpos * SQUARE_HEIGHT) + (percentY * (SQUARE_HEIGHT * gizHeight)),
							(gizWidth * SQUARE_WIDTH) * percentWidth,
							(gizHeight * SQUARE_HEIGHT) * percentHeight
					);

				} else if (vs.getType().equals("Triangle")) {

					shape = new Path2D.Double();

					((Path2D.Double) shape).moveTo(
							(gizXpos * SQUARE_WIDTH),
							(gizYpos * SQUARE_HEIGHT)
					); // start at 0, 0 in the grid essentially

					((Path2D.Double) shape).lineTo(
							(gizXpos * SQUARE_WIDTH) + (gizWidth * (SQUARE_WIDTH * percentWidth)),
							(gizYpos * SQUARE_HEIGHT)
					); // draw a line from 0, 0 to width, 0

					((Path2D.Double) shape).lineTo(
							(gizXpos * SQUARE_WIDTH),
							(gizYpos * SQUARE_HEIGHT) + (gizHeight * (SQUARE_HEIGHT * percentHeight))
					); // draw a line from width, 0 to 0, height

					((Path2D.Double) shape).closePath(); // automatically close it and draw a line back to the origin

				} else if (vs.getType().equals("Circle")) {

					shape = new Ellipse2D.Double(
							(gizXpos * SQUARE_WIDTH) + (percentX * (SQUARE_WIDTH * gizWidth)),
							(gizYpos * SQUARE_HEIGHT) + (percentY * (SQUARE_HEIGHT * gizHeight)),
							(gizWidth * SQUARE_WIDTH) * percentRadius,
							(gizHeight * SQUARE_HEIGHT) * percentRadius
					);

				}

				// Set the colour, rotate it around the middle of the gizmo and draw it

				g.setColor(vs.getColour());
				g2d.rotate(
						gizAngle.radians(),
						(gizXpos * SQUARE_WIDTH) + (gizWidth * SQUARE_WIDTH)/2,
						(gizYpos * SQUARE_HEIGHT) + (gizHeight * SQUARE_HEIGHT)/2
				);
				g2d.draw(shape);
				g2d.fill(shape);

				// Reset the transform back to what it was to prevent it carrying over

				g2d.setTransform(pT);
			}

			if(ENABLE_DEBUG)
				paintDebug(giz, g);

		}
	}

	private void paintDebug(AbstractGizmo giz, Graphics g){

		Graphics2D g2d = (Graphics2D) g;

		double gizXpos = giz.getXpos();
		double gizYpos = giz.getYpos();
		double gizWidth = giz.getWidth();
		double gizHeight = giz.getHeight();
		Angle gizAngle = giz.getGizAngle();

		Shape shape;

		// Note that the data storage for physics objects is slightly different
		// Instead of storing width and height in the second tuple, it stores destination x and y coords
		// in ["MITCircle", [x, y], radius] Circle behaves exactly the same as the visible circle
		// in ["MITLine", [x1, y1], [x2, y2]] But physics are comprised of lines rather than shapes so instead of using
		// the "other" datapart to store width and height of shapes, we use it to store x and y destinations so the
		// aforementioned object represents a LineSeg object from x1, y1 to x2, y2. Note that as i mentioned before,
		// the x1/y1/x2/y2 values are stored in percentage based vals, divide by 100 to get modifier and then manipulate
		// them as you desire using the various ways i've describes in this file

		g.setColor(Color.white);
		// draw the lines
		for (LineSegment ls : giz.getStoredLines()) {

			AffineTransform pT = g2d.getTransform();

			double percentXsrc = ls.p1().x() / 100;
			double percentYsrc = ls.p1().y() / 100;
			double percentXdest = ls.p2().x() / 100;
			double percentYdest = ls.p2().y() / 100;

			shape = new Line2D.Double(
					(gizXpos * SQUARE_WIDTH) + (percentXsrc * (SQUARE_WIDTH * gizWidth)),
					(gizYpos * SQUARE_HEIGHT) + (percentYsrc * (SQUARE_HEIGHT * gizHeight)),
					(gizXpos * SQUARE_WIDTH) + (percentXdest * (SQUARE_WIDTH * gizWidth)),
					(gizYpos * SQUARE_HEIGHT) + (percentYdest * (SQUARE_HEIGHT * gizHeight))
			);

			g2d.rotate(
					gizAngle.radians(),
					(gizXpos * SQUARE_WIDTH) + (gizWidth * SQUARE_WIDTH)/2,
					(gizYpos * SQUARE_HEIGHT) + (gizHeight * SQUARE_HEIGHT)/2
			);
			g2d.draw(shape);

			g2d.setTransform(pT);

		}

		for (Circle cs : giz.getStoredCircles()) {

			AffineTransform pT = g2d.getTransform();

			double percentX = cs.getCenter().x() / 100;
			double percentY = cs.getCenter().y() / 100;
			double percentRadius = cs.getRadius() * 2 / 100;

			shape = new Ellipse2D.Double(
					(gizXpos * SQUARE_WIDTH) + (percentX * (SQUARE_WIDTH * gizWidth)),
					(gizYpos * SQUARE_HEIGHT) + (percentY * (SQUARE_HEIGHT * gizHeight)),
					(gizWidth * SQUARE_WIDTH) * percentRadius,
					(gizHeight * SQUARE_HEIGHT) * percentRadius
			);

			g2d.rotate(
					gizAngle.radians(),
					(gizXpos * SQUARE_WIDTH) + (gizWidth * SQUARE_WIDTH)/2,
					(gizYpos * SQUARE_HEIGHT) + (gizHeight * SQUARE_HEIGHT)/2
			);
			g2d.draw(shape);

			g2d.setTransform(pT);
		}

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		this.paintBackground(g);

		this.paintGizmos(g);

	}
}
