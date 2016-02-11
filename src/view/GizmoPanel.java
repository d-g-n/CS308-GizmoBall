package view;

import gizmos.AbstractGizmo;
import gizmos.VisualShape;
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

				if (vs.getType().equals("Square")) {

					shape = new Rectangle2D.Double(
							(gizXpos * SQUARE_WIDTH) + ((percentX * SQUARE_WIDTH) * gizWidth),
							(gizYpos * SQUARE_HEIGHT) + ((percentY * SQUARE_HEIGHT) * gizHeight),
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
							(gizXpos * SQUARE_WIDTH) + ((gizWidth * SQUARE_WIDTH) * percentWidth),
							(gizYpos * SQUARE_HEIGHT)
					); // draw a line from 0, 0 to width, 0

					((Path2D.Double) shape).lineTo(
							(gizXpos * SQUARE_WIDTH),
							(gizYpos * SQUARE_HEIGHT) + ((gizHeight * SQUARE_HEIGHT) * percentHeight)
					); // draw a line from width, 0 to 0, height

					((Path2D.Double) shape).closePath(); // automatically close it and draw a line back to the origin

				} else if (vs.getType().equals("Circle")) {

					shape = new Ellipse2D.Double(
							(gizXpos * SQUARE_WIDTH) + ((percentX * SQUARE_WIDTH) * gizWidth),
							(gizYpos * SQUARE_HEIGHT) + ((percentY * SQUARE_HEIGHT) * gizHeight),
							(gizWidth * SQUARE_WIDTH) * percentRadius,
							(gizHeight * SQUARE_HEIGHT) * percentRadius
					);

				}

				// Set the colour, rotate it around the middle of the gizmo and draw it

				g.setColor(vs.getColour());
				g2d.rotate(gizAngle.radians(), (gizXpos * SQUARE_WIDTH) + (gizWidth * SQUARE_WIDTH)/2, (gizYpos * SQUARE_HEIGHT) + (gizHeight * SQUARE_HEIGHT)/2);
				g2d.draw(shape);
				g2d.fill(shape);

				// Reset the transform back to what it was to prevent it carrying over

				g2d.setTransform(pT);
			}
		}
	}

	private void paintDebug(Graphics g){

		Graphics2D g2d = (Graphics2D) g;

		for(AbstractGizmo giz : pm.getBoardGizmos()) {
			double sqWidth = (this.getWidth() / CONS_SIZE);
			double sqHeight = (this.getHeight() / CONS_SIZE);
			double xC = giz.getXpos();
			double yC = giz.getYpos();
			double gizWidth = giz.getWidth();
			double gizHeight = giz.getHeight();
			Angle ang = giz.getGizAngle();


			// DEBUG drawcode that shows MIT physics lines and circles

			g.setColor(Color.white);
			// draw the lines
			for (LineSegment ls : giz.getStoredLines()) {
				AffineTransform pT = g2d.getTransform();

				double sourceX = ls.p1().x();
				double sourceY = ls.p1().y();
				double destX = ls.p2().x();
				double destY = ls.p2().y();

				g2d.setStroke(new BasicStroke(1));

				Line2D.Double shape = new Line2D.Double(
						((sqWidth * xC) + (sqWidth * (sourceX / 100)) * gizWidth),
						((sqHeight * yC) + (sqHeight * (sourceY / 100)) * gizHeight),
						((sqWidth * xC) + (sqWidth * (destX / 100)) * gizWidth),
						((sqHeight * yC) + (sqHeight * (destY / 100)) * gizHeight)
				);


				g2d.rotate(ang.radians(), (sqWidth * xC) + sqWidth / 2, (sqHeight * yC) + sqHeight / 2);
				g2d.draw(shape);
				g2d.setStroke(new BasicStroke(1));

				g2d.setTransform(pT);
			}

			for (Circle cs : giz.getStoredCircles()) {
				double centerX = cs.getCenter().x();
				double centerY = cs.getCenter().y();
				double radius = cs.getRadius();


				g2d.setStroke(new BasicStroke(1));
				Ellipse2D.Double shape = new Ellipse2D.Double(
						((sqWidth * xC) + ((centerX / 100) * sqWidth) * gizWidth),
						((sqHeight * yC) + ((centerY / 100) * sqHeight) * gizHeight),
						(radius / 100) * 2 * sqWidth * gizWidth,
						(radius / 100) * 2 * sqHeight * gizHeight
				);

				g2d.draw(shape);
				g2d.setStroke(new BasicStroke(1));
			}

		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		this.paintBackground(g);

		this.paintGizmos(g);

		if(ENABLE_DEBUG)
			this.paintDebug(g);

	}
}
