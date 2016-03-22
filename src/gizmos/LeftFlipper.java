package gizmos;

/**
 * The LeftFlipper class is a subclass of Flipper and
 * provides core implementation for a flipper that flips
 * anticlockwise.
 *
 */
public class LeftFlipper extends Flipper {

	public LeftFlipper(int x, int y) {

		super(x, y);

		rotateClockwise = false;
		this.type = "LeftFlipper";

	}

	/**
	 * @see gizmos.AbstractGizmo#doPhysicsCalculations()
	 */
	@Override
	public void doPhysicsCalculations() {
		if (flipperMoving) {
			if (!rotateClockwise) {
				flipAntiClockwise(90);
			} else {
				flipClockwise(180);
			}
		}
	}

	@Override
	public String getType() {
		return "LeftFlipper";
	}

}
