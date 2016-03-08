package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import model.ProjectManager;
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
