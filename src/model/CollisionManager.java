package model;

import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.BallActor;
import physics.*;

public class CollisionManager extends Observable{

	public final double STEP = 0.05;
	private ProjectManager pm;

	public CollisionManager(ProjectManager pm) {
		this.pm = pm;
	}

	public void update(AbstractGizmo b) {
		BallActor ball = (BallActor) b;
		double xp = ball.getXpos();
		double yp = ball.getYpos();

		List<AbstractGizmo> gizmos = pm.getBoardGizmos();
		Vect velocity = new Vect(Angle.DEG_90,1);
		for(AbstractGizmo gizmo : gizmos){
			if(gizmo.getClass().equals("OuterWall")){
				if(gizmo.getXpos() >= 20){
					velocity = Geometry.reflectWall(gizmo.getStoredLines().get(3), ball.getVelocity());
				}
			}
				
		}
		
		pm.setBallPosition(velocity.x(), velocity.y());
	}
	
	

}
