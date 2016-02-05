package gizmos;


public class Gizmo_Absorber extends AbstractGizmo {

	public Gizmo_Absorber(int x, int y, int width, int height) {
		super(x, y, width, height);
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
	public void doAction() {
		super.doAction();

		// if ball is held, chuck it back out

	}
}
