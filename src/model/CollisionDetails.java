package model;

import gizmos.AbstractGizmo;
import physics.Vect;

public class CollisionDetails {

	private Vect velocity;
	private double time;
	private AbstractGizmo hitGizmo;
	
	public CollisionDetails(Vect velocity, double time, AbstractGizmo hitGiz){
		this.velocity = velocity;
		this.time = time;
		this.hitGizmo = hitGiz;
	}
	
	public Vect getVelocity(){
		return velocity;
	}
	
	public double getTimeToCollision(){
		return time;
	}

	public AbstractGizmo getHitGizmo() { return hitGizmo; }
}