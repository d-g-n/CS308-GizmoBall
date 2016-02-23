package view;

import gizmos.*;
import model.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Observable;
import java.util.Observer;

public class RunBoard extends JPanel implements Board {

	private static final long serialVersionUID = 1L;
	private ProjectManager pm;
	int count = 0;

	public RunBoard(ProjectManager pm) {
		this.pm = pm;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints ro = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(ro);
		
		drawEmptyBoardWithGuidelines(g,BOARD_WIDTH, BOARD_HEIGHT);
		//leftFlipper
		Shape leftFlipper = pm.getLeftFlipper().getShape();
		g2d.setColor(pm.getLeftFlipper().getColor());
		g2d.fill(leftFlipper);
		
		//rightFlipper
		Shape rightFlipper = pm.getRightFlipper().getShape();
		g2d.setColor(pm.getRightFlipper().getColor());
		g2d.fill(rightFlipper);

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
