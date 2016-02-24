package model;

import physics.Vect;

public class CollisionDetails {

	private Vect velocity;
	private double time;
	
	public CollisionDetails(Vect velocity, double time){
		this.velocity = velocity;
		this.time = time;
	}
	
	public Vect getVelocity(){
		return velocity;
	}
	
	public double getTimeToCollision(){
		return time;
	}
}