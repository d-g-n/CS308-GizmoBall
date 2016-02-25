package gizmos;


import model.ProjectManager;
import physics.Vect;
import view.Board;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Random;

public class Absorber extends AbstractGizmo {

	public Absorber(int x, int y, int w, int h) {

		super(x, y, w, h,
				Color.magenta, // colour of gizmo
				0 // reflection coefficent
		);


		addPhysicsPath(Arrays.asList(
				new Vect(xpos, ypos), // start at top left
				new Vect(xpos + width, ypos), // move to top right
				new Vect(xpos + width, ypos + height), // move to bottom right
				new Vect(xpos, ypos + height), // move to bottom left
				new Vect(xpos, ypos) // and back up to top left
		));

		setShape(new Rectangle2D.Double(
				(xpos),
				(ypos),
				(width),
				(height)
		));

		// FOR DEBUGGING, ADD ALL ABSORBERS TO THEIR OWN LISTENER LIST TO ENABLE AUTO FIRING

		this.addGizmoListener(this);

	}


	/**
	 * This is an example of how to extend abstract behaviour to provide additional behaviour
	 */
	@Override
	public void onHit() {
		// hold the ball in this

		Ball boardBall = ProjectManager.getBall();

		boardBall.setStopped(true);

		boardBall.setVelocity(new Vect(0, 0));

		boardBall.setPos(xpos + width - boardBall.getRadius(), ypos);




		super.onHit();
	}

	/**
	 * Like above, will want to set the velocity of the ball if the ball is currently being held
	 */
	@Override
	public void doTrigger() {
		super.doTrigger();

		// if ball is held, chuck it back out

		Ball boardBall = ProjectManager.getBall();

		if(boardBall.isStopped()){
			boardBall.setStopped(false);

			boardBall.setVelocity(new Vect(0, -50));
		}

	}
}
