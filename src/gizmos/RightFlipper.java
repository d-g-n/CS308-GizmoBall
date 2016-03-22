package gizmos;

/**
 *  The RightFlipper class is a subclass of Flipper and
 * provides core implementation for a flipper that flips
 * clockwise.
 */
public class RightFlipper extends Flipper {

	public RightFlipper(int x, int y) {
		super(x, y);
		rotateClockwise = true;
		this.type = "RightFlipper";
	}

	/**
	 * @see gizmos.AbstractGizmo#doPhysicsCalculations()
	 */
	@Override
	public void doPhysicsCalculations() {
		if (flipperMoving) {
			if (rotateClockwise) {
				flipClockwise(270);
			} else {
				flipAntiClockwise(180);
			}
		}
	}

}
