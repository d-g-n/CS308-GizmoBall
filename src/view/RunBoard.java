package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import gizmos.AbstractGizmo;
import model.ProjectManager;

public class RunBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	private ProjectManager pm;

	public RunBoard(ProjectManager pm) {
		this.pm = pm;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints ro = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(ro);

		drawEmptyBoardWithGuidelines(g,Board.BOARD_WIDTH, Board.BOARD_HEIGHT);

		//drawBall(pm.getBall(),g);
		for (AbstractGizmo gizmo : pm.getBoardGizmos()) {
			AffineTransform pT = g2d.getTransform();


			Shape shape = gizmo.getShape();
			AffineTransform shapeT = new AffineTransform();

			double scaleFactor = (Board.BOARD_WIDTH / Board.X_CELLS);
			shapeT.scale(scaleFactor, scaleFactor);

			shape = shapeT.createTransformedShape(shape);

			g.setColor(gizmo.getGizCol());
			g2d.rotate(
					Math.toRadians(gizmo.getGizAngle()),
					shape.getBounds2D().getCenterX(),
					shape.getBounds2D().getCenterY()
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
		for (int i = 1; i <= Board.X_CELLS; i++) {
			g.drawLine((boardWidth / Board.X_CELLS) * i, 0, (boardWidth / Board.Y_CELLS) * i, boardHeight);
		}

		// Draw Guidelines on y axis
		for (int i = 1; i <= Board.Y_CELLS; i++) {
			g.drawLine(0, (boardWidth / Board.X_CELLS) * i, boardWidth, (boardHeight / Board.Y_CELLS) * i);
		}
	}
}
