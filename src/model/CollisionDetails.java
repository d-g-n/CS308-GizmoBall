package model;

import gizmos.AbstractGizmo;
import physics.Vect;

public class CollisionDetails {

	private Vect velocity, velocity2;
	private double time;
	private AbstractGizmo hitGizmo;
	
	public CollisionDetails(Vect velocity1, Vect velocity2, double time, AbstractGizmo hitGiz){
		this.velocity = velocity1;
		this.velocity2 = velocity2;
		this.time = time;
		this.hitGizmo = hitGiz;
	}
	
	public Vect getVelocity(){
		return velocity;
	}
	public Vect getOtherVelocity(){
		return velocity2;
	}
	
	public double getTimeToCollision(){
		return time;
	}

	public AbstractGizmo getHitGizmo() { return hitGizmo; }
}