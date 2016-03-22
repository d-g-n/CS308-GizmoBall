package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.GizmoConstants;
import model.ProjectManager;

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
			x = e.getX() / (GizmoConstants.BOARD_WIDTH / GizmoConstants.X_CELLS);
			y = e.getY() / (GizmoConstants.BOARD_HEIGHT / GizmoConstants.Y_CELLS);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!pm.isBuildModeOn())
			return;

		int destx = e.getX() / (GizmoConstants.BOARD_WIDTH / GizmoConstants.X_CELLS);
		int desty = e.getY() / (GizmoConstants.BOARD_HEIGHT / GizmoConstants.Y_CELLS);

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
