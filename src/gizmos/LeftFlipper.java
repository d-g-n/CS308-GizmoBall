package gizmos;

public class LeftFlipper extends Flipper {

	public LeftFlipper(int x, int y) {

		super(x, y);

		rotateClockwise = false;
		this.type = "LeftFlipper";

	}

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
