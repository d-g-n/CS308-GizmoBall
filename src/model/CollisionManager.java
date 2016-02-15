package model;

import gizmos.AbstractGizmo;
import gizmos.BallActor;
import physics.*;

import java.util.List;
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

		
		List<AbstractGizmo> gizmos = pm.getBoardGizmos();
		Vect velocity = new Vect(Angle.DEG_90,1);
	/*	for(AbstractGizmo gizmo : gizmos){
			if(gizmo.getClass().equals("OuterWall")){
				if(ball.getXpos() >= 20){
					velocity = Geometry.reflectWall(gizmo.getStoredLines().get(3), ball.getVelocity());
					ball.setVelocity(velocity.x(),velocity.y());
				}
			}
				
		} */

		ball.setPos(velocity.x(), velocity.y());

		if(yp >= 20)
			yp = 0;

		yp = yp + 0.10;
		ball.setPos(10, yp);

	}
	
	
	

}
