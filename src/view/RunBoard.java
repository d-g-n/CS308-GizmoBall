package view;

import gizmos.*;
import model.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class RunBoard extends JPanel implements Board {

	private ProjectManager pm;

	public RunBoard(ProjectManager pm) {
		this.pm = pm;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints ro = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(ro);

		//Some information about the board
		int boardWidth = this.getWidth();
		int boardHeight = this.getHeight();
		double cellWidth = boardWidth / X_CELLS;
		double cellHeight = boardHeight / Y_CELLS;
		
		drawEmptyBoardWithGuidelines(g, boardWidth, boardHeight);
		
		for (AbstractGizmo gizmo : pm.getBoardGizmos()) {
			AffineTransform pT = g2d.getTransform();


			double gizmoXpos = gizmo.getXpos();
			double gizmoYpos = gizmo.getYpos();
			double gizmoWidth = gizmo.getWidth();
			double gizmoHeight = gizmo.getHeight();

			Shape shape = new Polygon();

			//If the gizmo is a Circle or a Ball then paint an Ellipse
			if (gizmo.getClass().equals(CircularBumper.class)
					|| gizmo.getClass().equals(BallActor.class)) {

				shape = new Ellipse2D.Double(
						(cellWidth * gizmoXpos),
						(cellHeight * gizmoYpos),
						(cellWidth * gizmoWidth),
						(cellHeight * gizmoHeight)
				);

			} else if (gizmo.getClass().equals(Absorber.class) //If it is an absorber or a square
					|| gizmo.getClass().equals(SquareBumper.class)) { //paint a Rectangle

				shape = new Rectangle2D.Double(
						(cellWidth * gizmoXpos),
						(cellHeight * gizmoYpos),
						(cellWidth * gizmoWidth),
						(cellHeight * gizmoHeight)
				);

				g2d.rotate(gizmo.getGizAngle().radians());
				g2d.draw(shape);
				g2d.fill(shape);

			}else if(gizmo.getClass().equals(TriangleBumper.class)){

				shape = new Polygon();
				//Add the three points of the triangle to the shape
				
				/*
				 *  *    
				 *  - -
				 */
				((Polygon) shape).addPoint(
						(int) (cellWidth * gizmoXpos),
						(int) (cellHeight * gizmoYpos)
				);
				
				/*
				 *  -    
				 *  - *
				 */
				((Polygon) shape).addPoint(
						(int) ((cellWidth * gizmoXpos) + (cellWidth * gizmoWidth)),
						(int) ((cellWidth * gizmoYpos) + (cellHeight * gizmoHeight))
				);
				
				/*
				 *  -    
				 *  * -
				 */
				((Polygon) shape).addPoint(
						(int) (cellWidth * gizmoXpos),
						(int) ((cellWidth * gizmoYpos) + (cellHeight * gizmoHeight))			
				);

			} else if(gizmo.getClass().equals(LeftFlipper.class)
					|| gizmo.getClass().equals(RightFlipper.class)){

				shape = new RoundRectangle2D.Double(
						(cellWidth * gizmoXpos),
						(cellHeight * gizmoYpos),
						(cellWidth * gizmoWidth),
						(cellHeight * gizmoHeight) * 0.25,
						25,
						100
				);

			}

			g.setColor(gizmo.getGizCol());
			g2d.rotate(
					gizmo.getGizAngle().radians(),
					shape.getBounds2D().getX() + cellWidth / 2,
					shape.getBounds2D().getY() + cellHeight / 2
			);
			g2d.draw(shape);
			g2d.fill(shape);

			g2d.setTransform(pT);
		}

	}

	private void drawEmptyBoardWithGuidelines(Graphics g, int boardWidth, int boardHeight) {
		// Draw background
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.darkGray);

		// Draw guidelines on x axis
		for (int i = 1; i <= X_CELLS; i++) {
			g.drawLine((boardWidth / X_CELLS) * i, 0, (boardWidth / Y_CELLS) * i, boardHeight);
		}

		// Draw Guidelines on y axis
		for (int i = 1; i <= Y_CELLS; i++) {
			g.drawLine(0, (boardWidth / X_CELLS) * i, boardWidth, (boardHeight / Y_CELLS) * i);
		}
	}
}
