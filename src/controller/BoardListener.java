package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gizmos.Absorber;
import gizmos.Ball;
import gizmos.CircleBumper;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import model.ProjectManager;
import physics.Vect;
import view.Board;

public class BoardListener implements MouseListener {

	private JPanel board;
	private ProjectManager pm;
	public BoardListener(ProjectManager pm, JPanel board){
		this.board = board;
		this.pm = pm;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(pm.isBuildModeOn()){
			int x =  e.getX() / (Board.BOARD_WIDTH / Board.X_CELLS);
			int y =  e.getY() / (Board.BOARD_HEIGHT / Board.Y_CELLS);
			int width = 1;
			if(pm.getFocusedButton().equals("Square")){
				pm.addGizmo(new SquareBumper(x, y,width , width));
				pm.pushVisualUpdate();
			}else if(pm.getFocusedButton().equals("Triangle")){
				pm.addGizmo(new TriangleBumper(x, y,width , width));
				pm.pushVisualUpdate();
			} else if(pm.getFocusedButton().equals("Circle")){
				pm.addGizmo(new CircleBumper(x, y,width , width));
				pm.pushVisualUpdate();
			} else if(pm.getFocusedButton().equals("LFlipper")){
				pm.addGizmo(new LeftFlipper(x, y));
				pm.pushVisualUpdate();
			} else if(pm.getFocusedButton().equals("RFlipper")){
				pm.addGizmo(new RightFlipper(x, y));
				pm.pushVisualUpdate();
			} else if(pm.getFocusedButton().equals("Add Ball")){
				Vect initialVelocity = new Vect(0.0, 0.0);
				Ball ball = new Ball((double)x, (double)y, initialVelocity);
				pm.addGizmo(ball);
				pm.addBall(ball);
				pm.pushVisualUpdate();
			}  else if(pm.getFocusedButton().equals("Add Absorber")){
				
				/* Need to implement Absorber properly. User should be able to choose it's width and height. MouseDragged Listener maybe?   */
				
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
