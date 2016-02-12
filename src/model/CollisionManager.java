package model;

import gizmos.AbstractGizmo;
import gizmos.BallActor;

import java.util.Observable;

public class CollisionManager extends Observable{

	public final double STEP = 0.05;
	private ProjectManager pm;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
	}

	public void collisionUpdate(AbstractGizmo b) {
		BallActor ball = (BallActor) b;
		double xp = ball.getXpos();
		double yp = ball.getYpos();

		/*
		List<AbstractGizmo> gizmos = pm.getBoardGizmos();
		Vect velocity = new Vect(Angle.DEG_90,1);
		for(AbstractGizmo gizmo : gizmos){
			if(gizmo.getClass().equals("OuterWall")){
				if(gizmo.getXpos() >= 20){
					velocity = Geometry.reflectWall(gizmo.getStoredLines().get(3), ball.getVelocity());
				}
			}
				
		}
*/
		//ball.setPos(velocity.x(), velocity.y());

		if(xp >= 20)
			xp = 0;

		xp = xp + 0.10;
		ball.setPos(xp, 10);

	}
	
	

}
