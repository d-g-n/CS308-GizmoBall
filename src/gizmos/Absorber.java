package gizmos;

import physics.Vect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Absorber extends AbstractGizmo {

	List<Ball> heldBalls;

	public Absorber(int x, int y, int w, int h) {

		super(x, y, w, h,
				Color.magenta, // colour of gizmo
				0 // reflection coefficent
		);

		this.type = "Absorber";

		this.heldBalls = new ArrayList<>();

	}

	private void sortBalls(){
		int i = 0;
		for(Ball b : heldBalls){

			b.setStopped(true);
			b.setVelocity(new Vect(0, 0));
			b.setPos((xpos + width) - (b.getRadius() * 2) - i, ypos + (b.getRadius() * 2));

			i++;
		}
	}

	/**
	 * This is an example of how to extend abstract behaviour to provide additional behaviour
	 */
	@Override
	public void onHit(AbstractGizmo hit) {
		// hold the ball in this

		Ball boardBall = ((Ball) hit);

		if(heldBalls.size() < this.getWidth()){
			heldBalls.add(boardBall);
			sortBalls();
		}
	}

	/**
	 * Like above, will want to set the velocity of the ball if the ball is currently being held
	 */
	@Override
	public void doTrigger() {

		// if ball is held, chuck it back out

		if(heldBalls.size() > 0){
			Ball throwB = heldBalls.remove(0);

			throwB.setPos(throwB.getXPos(), throwB.getYPos() - (throwB.getRadius() * 3) );

			throwB.setVelocity(new Vect(0, -50));
			throwB.setStopped(false);

			System.out.println(heldBalls.size());
			sortBalls();
		}

		/*if(boardBall != null && boardBall.isStopped()){

			boardBall.setStopped(false);

			boardBall.setVelocity(new Vect(0, -50));

			boardBall = null;

		}*/

	}
	
	@Override
	public void setGizShape(double x, double y) {
		
		
		setShape(new Rectangle2D.Double(
				(x),
				(y),
				(width),
				(height)
		));
		
	}
	
	@Override
	public void setGizPhysics(double x, double y) {
		
		
		addPhysicsPath(Arrays.asList(
				new Vect(x, y), // start at top left
				new Vect(x + width, y), // move to top right
				new Vect(x + width, y + height), // move to bottom right
				new Vect(x, y + height), // move to bottom left
				new Vect(x, y) // and back up to top left
		));
		
	}
}
