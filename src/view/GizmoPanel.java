package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.sound.sampled.Line;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import gizmos.AbstractGizmo;
import gizmos.VisualShape;
import model.ProjectManager;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;

/**
 * Created by Declan on 06/02/2016.
 */
public class GizmoPanel extends JPanel {

	private static final int CONS_SIZE = 20;

	private static final boolean ENABLE_DEBUG = true;

	public ProjectManager pm;

	public GizmoPanel(ProjectManager pm){
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.pm = pm;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints ro = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // for the smoothest circle you've ever seen

		g2d.setRenderingHints(ro);

		// Draw background
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());


		g.setColor(Color.darkGray);

		// Draw guidelines on x axis

		for(int i = 1; i <= CONS_SIZE; i++){
			g.drawLine(((this.getWidth()/CONS_SIZE) * i), 0, ((this.getWidth()/CONS_SIZE) * i), this.getHeight());
		}

		// Draw Guidelines on y axis

		for(int i = 1; i <= CONS_SIZE; i++){
			g.drawLine(0, ((this.getWidth()/CONS_SIZE) * i), this.getWidth(), ((this.getWidth()/CONS_SIZE) * i));
		}


		// Primary gizmo drawcode

		for(AbstractGizmo giz : pm.getBoardGizmos()) {
			double sqWidth = (this.getWidth() / CONS_SIZE);
			double sqHeight = (this.getHeight() / CONS_SIZE);
			double xC = giz.getXpos();
			double yC = giz.getYpos();
			double gizWidth = giz.getWidth();
			double gizHeight = giz.getHeight();
			Angle ang = giz.getGizAngle();


			// MAIN manual drawcode to define gizmo components

			for (VisualShape vs : giz.getStoredVisualShapes()) {

				AffineTransform pT = g2d.getTransform();
				// for square, first two is x, y in percent, next two is width and height in percent


				if (vs.getType().equals("Square")) {
					int localX = vs.getValList().get(0);
					int localY = vs.getValList().get(1);
					int localWidth = vs.getValList().get(2);
					int localHeight = vs.getValList().get(3);

					g2d.setColor(vs.getColour());
					Rectangle shape = new Rectangle(
							(int) ((sqWidth * xC) + (sqWidth * ((double) localX / 100))),
							(int) ((sqHeight * yC) + (sqHeight * ((double) localY / 100))),
							(int) (sqWidth * ((double) localWidth / 100) * gizWidth),
							(int) ((sqHeight * ((double) localHeight / 100)) * gizHeight)
					);
					g2d.rotate(ang.radians());
					g2d.draw(shape);
					g2d.fill(shape);
				} else if(vs.getType().equals("OuterWall")){
					
					g2d.setColor(vs.getColour());
					
					Line2D.Double shape = new Line2D.Double(
							0,1,1,1);
					
					g2d.draw(shape);
					g2d.fill(shape);
				} else if (vs.getType().equals("Triangle")) {
				
					int localX = vs.getValList().get(0);
					int localY = vs.getValList().get(1);
					int localWidth = vs.getValList().get(2);
					int localHeight = vs.getValList().get(3);

					g2d.setColor(vs.getColour());
					//Rectangle shape = new Rectangle(, , (this.getWidth()/CONS_SIZE) * localWidth, (this.getWidth()/CONS_SIZE) * localHeight);
					Polygon shape = new Polygon();

					shape.addPoint(
							(int) (sqWidth * xC),
							(int) (sqHeight * yC)
					);

					shape.addPoint(
							(int) ((sqWidth * xC) + (sqHeight * ((double) localWidth / 100))),
							(int) ((sqWidth * yC) + (localY * sqHeight))
					);

					shape.addPoint(
							(int) (sqWidth * xC),
							(int) ((sqWidth * yC) + (sqHeight * ((double) localHeight / 100)))
					);

					g2d.rotate(ang.radians(), shape.getBounds2D().getX() + sqWidth / 2, shape.getBounds2D().getY() + sqHeight / 2);

					g2d.draw(shape);

					g2d.fill(shape);
				} else if (vs.getType().equals("Circle")) {
					int localX = vs.getValList().get(0);
					int localY = vs.getValList().get(1);
					int localRadius = vs.getValList().get(2);

					g2d.setColor(vs.getColour());

					Ellipse2D.Double shape = new Ellipse2D.Double(
							((sqWidth * xC) + ((((double) localX / 100) * sqWidth) * gizWidth)),
							((sqHeight * yC) + ((((double) localY / 100) * sqHeight) * gizHeight)),
							((double) localRadius / 100) * 2 * sqWidth * gizWidth,
							((double) localRadius / 100) * 2 * sqHeight * gizHeight
					);

					g2d.rotate(ang.radians());
					g2d.draw(shape);
					g2d.fill(shape);

				}

				g2d.setTransform(pT);
			}


			if (ENABLE_DEBUG) {


				// DEBUG drawcode that shows MIT physics lines and circles

				g2d.setColor(Color.white);
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
	}
}
