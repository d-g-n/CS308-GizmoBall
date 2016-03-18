package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.CircleBumper;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import model.ProjectManager;
import view.Board;

public class BoardListener implements MouseListener {

	private JPanel board;
	private ProjectManager pm;

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
			int x = e.getX() / (Board.BOARD_WIDTH / Board.X_CELLS);
			int y = e.getY() / (Board.BOARD_HEIGHT / Board.Y_CELLS);
			int width = 1;

			switch (pm.getFocusedButton()) {
			case "Square":
				pm.addGizmo(new SquareBumper(x, y, width, width));
				pm.pushVisualUpdate();
				break;
			case "Triangle":
				pm.addGizmo(new TriangleBumper(x, y, width, width));
				pm.pushVisualUpdate();
				break;
			case "Circle":
				pm.addGizmo(new CircleBumper(x, y, width, width));
				pm.pushVisualUpdate();
				break;
			case "Absorber":

				if (pm.getAbsorberToBeAddedX() == -1) {

					pm.setAbsorberToBeAddedX(x);
					pm.setAbsorberToBeAddedY(y);

				} else {

					if (x < pm.getAbsorberToBeAddedX() || y < pm.getAbsorberToBeAddedY()) {

						pm.setAbsorberToBeAddedX(-1);
						pm.setAbsorberToBeAddedY(-1);

					} else {

						int absorberWidth = x - pm.getAbsorberToBeAddedX() + 1;
						int absorberHeight = y - pm.getAbsorberToBeAddedY() + 1;
						pm.addGizmo(new Absorber(pm.getAbsorberToBeAddedX(), pm.getAbsorberToBeAddedY(), absorberWidth,
								absorberHeight));
						pm.setAbsorberToBeAddedX(-1);
						pm.setAbsorberToBeAddedY(-1);
						pm.pushVisualUpdate();

					}

				}
				break;
			case "LFlipper":
				pm.addGizmo(new LeftFlipper(x, y));
				pm.pushVisualUpdate();
				break;
			case "RFlipper":
				pm.addGizmo(new RightFlipper(x, y));
				pm.pushVisualUpdate();
				break;
			case "Rotate":
				for (AbstractGizmo a : pm.getBoardGizmos()) {
					/*
					 * This will need to change to allow for flipper rotation
					 * also; we will need to override the rotate method for the
					 * flippers
					 */
					if ((int) a.getXPos() == x && a.getYPos() == y && a instanceof TriangleBumper) {

						a.rotateClockwise();
						break;
					}
				}
				pm.pushVisualUpdate();
				break;
			case "Delete":
				AbstractGizmo del = null;
				for (AbstractGizmo a : pm.getBoardGizmos()) {
					if ((int) a.getXPos() == x && a.getYPos() == y) {

						del = a;
						break;
					}
				}
				pm.deleteGizmo(del);
				pm.pushVisualUpdate();

				break;
			case "Move":

				if (pm.getGizmoToMove() == null) {
					AbstractGizmo move = null;
					for (AbstractGizmo a : pm.getBoardGizmos()) {

						if ((int) a.getXPos() == x && a.getYPos() == y) {

							move = a;
							break;
						}

					}
					pm.setGizmoToMove(move);

				} else {

					if (pm.canPlaceGizmoAt(pm.getGizmoToMove(), x, y)) {

						pm.getGizmoToMove().moveGiz(x, y);
						pm.getGizmoToMove().setPos(x, y);
						pm.getGizmoToMove().deletePhysics();
						pm.getGizmoToMove().movePhysics(x, y);

						int numberOfRotations = pm.getGizmoToMove().getGizAngle() / 90;

						for (int i = 0; i < numberOfRotations; i++) {

							pm.getGizmoToMove().rotatePhysicsAroundPoint(
									pm.getGizmoToMove().getXPos() + pm.getGizmoToMove().getWidth() / 2,
									pm.getGizmoToMove().getYPos() + (pm.getGizmoToMove().getHeight() / 2), 90.0);

						}
						pm.setGizmoToMove(null);

						pm.pushVisualUpdate();
					}
				}

				break;
			case "Connect Gizmos":

				for (AbstractGizmo a : pm.getBoardGizmos()) {
					if ((int) a.getXPos() == x && a.getYPos() == y) {
						if (pm.getGizmoToConnect() == null) {
							pm.setGizmoToConnect(a);
						} else {
							a.addGizmoListener(pm.getGizmoToConnect());
							pm.setGizmoToConnect(null);
						}

						break;
					}
				}
				break;

			case "Disconnect Gizmos":

				for (AbstractGizmo a : pm.getBoardGizmos()) {
					if ((int) a.getXPos() == x && a.getYPos() == y) {
						if (pm.getGizmoToDisconnect() == null) {
							pm.setGizmoToDisconnect(a);
						} else {
							a.removeGizmoListener(pm.getGizmoToDisconnect());
							pm.setGizmoToDisconnect(null);
						}

						break;
					}
				}

				break;
				
			case "Key Connect":


				if (pm.getGizmoToKeyConnect() == null) {
					
					for (AbstractGizmo a : pm.getBoardGizmos()) {
						if ((int) a.getXPos() == x && a.getYPos() == y) {
							
								pm.setGizmoToKeyConnect(a);
								break;
						}
					}

				}

				break;
				
			case "Key Disconnect":


				if (pm.getGizmoToKeyDisconnect() == null) {
					
					for (AbstractGizmo a : pm.getBoardGizmos()) {
						if ((int) a.getXPos() == x && a.getYPos() == y) {
							
								pm.setGizmoToKeyDisconnect(a);
								break;
						}
					}

				}

				break;

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
