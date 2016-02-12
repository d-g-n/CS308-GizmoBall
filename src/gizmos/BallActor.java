package gizmos;

import physics.Vect;

public class BallActor extends AbstractGizmo {

	private Vect velocity;

	public BallActor(double x, double y, double width, double height, int degrees, Vect velocity) {
		super(x, y, 0.5, 0.5, 0);
		this.velocity = velocity;
	}
	
	public Vect getVelocity(){
		return velocity;
	}

	public void setVelocity(double x, double y){
		velocity = new Vect(x, y);
	}

}
