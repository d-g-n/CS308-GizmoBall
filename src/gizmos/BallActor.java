package gizmos;

import physics.Vect;

import java.awt.*;

public class BallActor extends AbstractGizmo {

	private Vect velocity;

	public BallActor(double x, double y, double width, double height, int degrees, Vect velocity) {
		super(x, y, 0.5, 0.5, 0,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficient
		);

		this.velocity = velocity;

		this.addPhysicsCircle(
				x + (this.getWidth() / 2),
				y + (this.getHeight() / 2),
				this.getWidth()
		);
	}
	
	public Vect getVelocity(){
		return velocity;
	}

	public void setVelocity(double x, double y){
		velocity = new Vect(x, y);
	}
	
	public void applyGravityConstant(double tickTime) {
		
		Vect gravityApplied = new Vect(velocity.x(), velocity.y() + (25 * tickTime));
		
		this.setVelocity(gravityApplied.x(), gravityApplied.y());
	}
	
	public void applyFriction(double tickTime, double mu, double mu2) {
		
		Vect frictionApplied = new Vect(velocity.x() * (mu * tickTime) - (mu2 * velocity.x()) * tickTime, velocity.y() * (mu * tickTime) - (mu2 * velocity.y()) * tickTime);  
		
		this.setVelocity(frictionApplied.x(), frictionApplied.y());
		
		
	}

}
