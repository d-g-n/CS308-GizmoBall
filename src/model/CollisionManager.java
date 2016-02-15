package model;

import java.util.List;
import java.util.Observable;

import gizmos.AbstractGizmo;
import gizmos.BallActor;
import gizmos.CircularBumper;
import gizmos.OuterWall;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class CollisionManager extends Observable {

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
		Vect velocity = ball.getVelocity();
		for (AbstractGizmo gizmo : gizmos) {
			if (gizmo instanceof OuterWall) {
				
				double timeToCollision = Geometry.timeUntilWallCollision(gizmo.getStoredLines().get(0), ball.getPhysicsCircle(),
						ball.getVelocity());
				if (timeToCollision != Double.POSITIVE_INFINITY && timeToCollision < 0.425) {
					System.out.println(velocity.angle());
					LineSegment line = gizmo.getStoredLines().get(0);
					velocity = Geometry.reflectWall(line, velocity, ball.getReflectionCoefficient());
					System.out.println(velocity.angle());
					ball.setPos(velocity.x(), velocity.y());
					ball.setGizAngle(velocity.angle());
					break;
				}
			}else if(gizmo instanceof CircularBumper){
				/*
				if(Geometry.timeUntilCircleCollision(gizmo.getStoredCircles().get(0), ball.getPhysicsCircle(), velocity) < 1){
					velocity = Geometry.reflectCircle(gizmo.getStoredCircles().get(0).getCenter(), ball.getVect(),
							ball.getVelocity());
				}
				*/
			}else{
				velocity = new Vect(xp+STEP,yp+0.05);
			}
		}
		ball.setPos(velocity.x(), velocity.y());
		ball.setVelocity(velocity);
	}

}
