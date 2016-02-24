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

		drawEmptyBoardWithGuidelines(g,BOARD_WIDTH, BOARD_HEIGHT);

		drawBall(pm.getBall(),g);
		for (AbstractGizmo gizmo : pm.getBoardGizmos()) {
			AffineTransform pT = g2d.getTransform();

			Shape shape = gizmo.getShape();

			g.setColor(gizmo.getGizCol());
			g2d.rotate(
					gizmo.getGizAngle().radians(),
					shape.getBounds2D().getX() + Board.CELL_WIDTH / 2,
					shape.getBounds2D().getY() + Board.CELL_HEIGHT / 2
			);
			g2d.draw(shape);
			g2d.fill(shape);


			g2d.setTransform(pT);
		}

	}

	private void drawBall(Ball b,Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		int x = (int) (b.getXPos() - b.getRadius());
		int y = (int) (b.getYPos() - b.getRadius());
		int width = (int) (2 * b.getRadius());
		g2.fillOval(x, y, width, width);
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
