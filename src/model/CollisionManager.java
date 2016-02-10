package model;

import java.util.Observable;
import java.util.Observer;
import gizmos.BallActor;

public class CollisionManager implements Observer{

	public final int BOARD_LIMIT_RIGHT = 20;
	public final int BOARD_LIMIT_LEFT = 0;
	public final int BOARD_LIMIT_UP = 0;
	public final int BOARD_LIMIT_DOWN = 20;
	private ProjectManager pm;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
	}

	@Override
	public void update(Observable o, Object arg) {
		BallActor ba = (BallActor) o;
		
		double xp = ba.getXpos();
		double yp = ba.getYpos();

		if (xp >= BOARD_LIMIT_RIGHT) {
			xp = 0;
		} else {
			xp = xp + 0.05;
		}
		
		pm.setBallPosition(xp, yp);
	}
	
	

}
