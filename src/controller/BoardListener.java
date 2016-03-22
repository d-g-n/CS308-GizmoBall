package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import gizmos.*;
import model.ProjectManager;
import physics.Vect;
import view.Board;

public class BoardListener implements MouseListener {

	private JPanel board;
	private ProjectManager pm;
	private int x,y;

	/**
	 * The BoardListener class implements the MouseListener interface and handles
	 * the mouse events on the play board.
	 *
	 */
	public BoardListener(ProjectManager pm, JPanel board) {
		this.board = board;
		this.pm = pm;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (pm.isBuildModeOn()) {
			x = e.getX() / (Board.BOARD_WIDTH / Board.X_CELLS);
			y = e.getY() / (Board.BOARD_HEIGHT / Board.Y_CELLS);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!pm.isBuildModeOn())
			return;

		int destx = e.getX() / (Board.BOARD_WIDTH / Board.X_CELLS);
		int desty = e.getY() / (Board.BOARD_HEIGHT / Board.Y_CELLS);

		CommandMapper.Command getCom = CommandMapper.getCommandByUID(pm.getCurrentCommand());

		if(getCom != null && getCom.getCommandLevel().equals(CommandMapper.CommandLevel.BOARD_LEVEL))
			getCom.getAction().onClickAndRelease(x, y, destx, desty);

		pm.pushVisualUpdate();
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
