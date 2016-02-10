package gizmos;


public class Absorber extends AbstractGizmo {

	public Absorber(int x, int y, int width, int height, int degrees) {
		super(x, y, width, height, degrees);
	}


	/**
	 * This is an example of how to extend abstract behaviour to provide additional behaviour
	 */
	@Override
	public void onHit() {
		super.onHit();

		// hold the ball in this

	}

	/**
	 * Like above, will want to set the velocity of the ball if the ball is currently being held
	 */
	@Override
	public void onCollision() {
		super.onCollision();

		// if ball is held, chuck it back out

	}
}
