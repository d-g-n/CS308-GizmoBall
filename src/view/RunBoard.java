package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.BallActor;
import gizmos.CircularBumper;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import model.ProjectManager;
import physics.Vect;

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

		// Some useless gizmo objects to help identity the type of gizmo below
		CircularBumper aCircle = new CircularBumper(0, 0, 0, 0, 0);
		Absorber anAbsorber = new Absorber(0, 0, 0, 0, 0);
		BallActor aBall = new BallActor(0, 0, 0, 0, 0, new Vect(0, 0));
		SquareBumper aSquare = new SquareBumper(0, 0, 0, 0, 0);
		TriangleBumper aTriangle = new TriangleBumper(0, 0, 0, 0, 0);

		
		drawEmptyBoardWithGuidelines(g, boardWidth, boardHeight);
		
		for (AbstractGizmo gizmo : pm.getBoardGizmos()) {
			AffineTransform pT = g2d.getTransform();

			double gizmoXpos = gizmo.getXpos();
			double gizmoYpos = gizmo.getYpos();
			double gizmoWidth = gizmo.getWidth();
			double gizmoHeight = gizmo.getHeight();

			//If the gizmo is a Circle or a Ball then paint an Ellipse
			if (aCircle.getClass().isInstance(gizmo) || aBall.getClass().isInstance(gizmo)) {
				if (aBall.getClass().isInstance(gizmo)) {
					g.setColor(Color.blue);
				} else {
					g.setColor(Color.red);
				}
				Ellipse2D.Double shape = new Ellipse2D.Double(
						(cellWidth * gizmoXpos),
						(cellHeight * gizmoYpos),
						(cellWidth * gizmoWidth),
						(cellHeight * gizmoHeight)
				);

				g2d.rotate(gizmo.getGizAngle().radians());
				g2d.draw(shape);
				g2d.fill(shape);

			} else if (anAbsorber.getClass().isInstance(gizmo) //If it is an absorber or a square
					|| aSquare.getClass().isInstance(gizmo)) { //paint a Rectangle
				if(aSquare.getClass().isInstance(gizmo)){
					g.setColor(Color.red);
				}else{
					g2d.setColor(Color.magenta);
				}
				Rectangle shape = new Rectangle(
						(int) (cellWidth * gizmoXpos), 
						(int) (cellHeight * gizmoYpos),
						(int) (cellWidth * gizmoWidth), 
						(int) (cellHeight * gizmoHeight));
				g2d.rotate(gizmo.getGizAngle().radians());
				g2d.draw(shape);
				g2d.fill(shape);
			}else if(aTriangle.getClass().isInstance(gizmo)){
				g.setColor(Color.yellow);
				Polygon shape = new Polygon();
				//Add the three points of the triangle to the shape
				
				/*
				 *  *    
				 *  - -
				 */
				shape.addPoint((int) (cellWidth * gizmoXpos),
								(int) (cellHeight * gizmoYpos)
				);
				
				/*
				 *  -    
				 *  - *
				 */
				shape.addPoint(
						(int) ((cellWidth * gizmoXpos) + (cellWidth * gizmoWidth)),
						(int) ((cellWidth * gizmoYpos) + (cellHeight * gizmoHeight))
				);
				
				/*
				 *  -    
				 *  * -
				 */
				shape.addPoint(
						(int) (cellWidth * gizmoXpos),
						(int) ((cellWidth * gizmoYpos) + (cellHeight * gizmoHeight))			
				);
				
				// Rotate appropriately the triangle
				g2d.rotate(gizmo.getGizAngle().radians(),
						shape.getBounds2D().getX() + cellWidth / 2, shape.getBounds2D().getY() + cellHeight / 2);
				g2d.draw(shape);
				g2d.fill(shape);
			}
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
