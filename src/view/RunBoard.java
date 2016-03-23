package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import gizmos.AbstractGizmo;
import model.GizmoConstants;
import model.ProjectManager;
import physics.Circle;
import physics.LineSegment;

public class RunBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final boolean DEBUG_MODE = false;
	private ProjectManager pm;

	public RunBoard(ProjectManager pm) {
		this.pm = pm;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints ro = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(ro);


		drawEmptyBoardWithGuidelines(g, GizmoConstants.BOARD_WIDTH, GizmoConstants.BOARD_HEIGHT);

		//drawBall(pm.getBallList(),g);
		for (AbstractGizmo gizmo : pm.getBoardGizmos()) {
			AffineTransform pT = g2d.getTransform();


			Shape shape = gizmo.getShape();
			Shape positionShape = new Rectangle2D.Double(gizmo.getXPos(), gizmo.getYPos(), gizmo.getWidth(), gizmo.getHeight());
			AffineTransform shapeT = new AffineTransform();

			double scaleFactor = (GizmoConstants.BOARD_WIDTH / GizmoConstants.X_CELLS);
			shapeT.scale(scaleFactor, scaleFactor);

			shape = shapeT.createTransformedShape(shape);
			positionShape = shapeT.createTransformedShape(positionShape);

			g.setColor(gizmo.getGizCol());
			g2d.draw(shape);
			g2d.fill(shape);


			g2d.setTransform(pT);
		}

		if(DEBUG_MODE) {


			//drawBall(pm.getBallList(),g);
			for (AbstractGizmo gizmo : pm.getBoardGizmos()) {


				List<LineSegment> lsList = gizmo.getStoredLines();
				List<Circle> lsCircle = gizmo.getStoredCircles();

				for (LineSegment ls : lsList) {

					AffineTransform pT = g2d.getTransform();

					double scaleFactor = (GizmoConstants.BOARD_WIDTH / GizmoConstants.X_CELLS);

					Shape shape = ls.toLine2D();

					AffineTransform shapeT = new AffineTransform();

					shapeT.scale(scaleFactor, scaleFactor);

					shape = shapeT.createTransformedShape(shape);

					g.setColor(Color.red);

					g2d.draw(shape);

					g2d.setTransform(pT);
				}

				for (Circle ls : lsCircle) {

					AffineTransform pT = g2d.getTransform();

					double scaleFactor = (GizmoConstants.BOARD_WIDTH / GizmoConstants.X_CELLS);

					Shape shape = ls.toEllipse2D();

					AffineTransform shapeT = new AffineTransform();

					shapeT.scale(scaleFactor, scaleFactor);

					shape = shapeT.createTransformedShape(shape);

					g.setColor(Color.red);

					g2d.draw(shape);

					g2d.setTransform(pT);
				}

			}

		}

		// draw arrows pointing from which gizmos will trigger what on hit, this is at the end to ensure it's drawn ontop of everything

		if(pm.isBuildModeOn()) {

			for (AbstractGizmo sourceGiz : pm.getBoardGizmos()) {

				for (AbstractGizmo listener : sourceGiz.getGizmoListeners()) {
					AffineTransform pT = g2d.getTransform();

					double scaleFactor = (GizmoConstants.BOARD_WIDTH / GizmoConstants.X_CELLS);

					Shape shape = new Line2D.Double(
							sourceGiz.getXPos() + sourceGiz.getWidth()/2,
							sourceGiz.getYPos() + sourceGiz.getHeight()/2,
							listener.getXPos() + listener.getWidth()/2,
							listener.getYPos() + listener.getHeight()/2);

					AffineTransform shapeT = new AffineTransform();

					shapeT.scale(scaleFactor, scaleFactor);

					shape = shapeT.createTransformedShape(shape);

					g.setColor(Color.white);

					g2d.draw(shape);

					g2d.setTransform(pT);
				}

			}
		}

	}

	private void drawEmptyBoardWithGuidelines(Graphics g, int boardWidth, int boardHeight) {
		// Draw background
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.darkGray);

		if(!pm.isBuildModeOn())
			return;

		// Draw guidelines on x axis
		for (int i = 1; i <= GizmoConstants.X_CELLS; i++) {
			g.drawLine((boardWidth / GizmoConstants.X_CELLS) * i, 0, (boardWidth / GizmoConstants.Y_CELLS) * i, boardHeight);
		}

		// Draw Guidelines on y axis
		for (int i = 1; i <= GizmoConstants.Y_CELLS; i++) {
			g.drawLine(0, (boardWidth / GizmoConstants.X_CELLS) * i, boardWidth, (boardHeight / GizmoConstants.Y_CELLS) * i);
		}
	}
}
