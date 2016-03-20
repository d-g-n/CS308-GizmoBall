package gizmos;

public class RightFlipper extends Flipper {

	public RightFlipper(int x, int y) {
		super(x, y);

		rotateClockwise = true;

		this.type = "RightFlipper";

	}

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
