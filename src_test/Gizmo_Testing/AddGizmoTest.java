package Gizmo_Testing;

import General_Testing.BaseTestCase;
import gizmos.*;
import org.junit.Assert;
import org.junit.Test;
import physics.Vect;

/**
 * Created by Declan on 19/03/2016.
 */
public class AddGizmoTest extends BaseTestCase {

	@Test
	public void canAddStandardGizmo(){

		// Standard gizmos being squarebumper, circle or triangle

		SquareBumper testSquare = new SquareBumper(0, 0);

		TriangleBumper testTriangle = new TriangleBumper(1, 0);

		CircleBumper testCircle = new CircleBumper(2, 0);

		pm.addGizmo(testSquare);
		pm.addGizmo(testTriangle);
		pm.addGizmo(testCircle);

		Assert.assertNotNull("Square should be not null as is found on board", pm.getGizmoByName(testSquare.getName()) );

		Assert.assertNotNull("Triangle should be not null as is found on board", pm.getGizmoByName(testTriangle.getName()) );

		Assert.assertNotNull("Circle should be not null as is found on board", pm.getGizmoByName(testCircle.getName()));


	}

	@Test
	public void canAddFlipperGizmos(){

		LeftFlipper testLeftFlipper = new LeftFlipper(0, 0);

		RightFlipper testRightFlipper = new RightFlipper(2, 0);

		pm.addGizmo(testLeftFlipper);

		pm.addGizmo(testRightFlipper);

		Assert.assertNotNull("Left Flipper should be not null as is found on board", pm.getGizmoByName(testLeftFlipper.getName()) );

		Assert.assertNotNull("Right Flipper should be not null as is found on board", pm.getGizmoByName(testRightFlipper.getName()) );

		Assert.assertTrue("Flippers should be 2 high and 2 wide", testLeftFlipper.getWidth() == 2 && testLeftFlipper.getHeight() == 2);
	}

	@Test
	public void canAddAbsorberGizmo(){

		Absorber testAbsorberShort = new Absorber(0, 0, 1, 1);

		Absorber testAbsorberWide = new Absorber(0, 1, 10, 1);

		Absorber testAbsorberHigh = new Absorber(0, 2, 1, 10);

		pm.addGizmo(testAbsorberShort);

		pm.addGizmo(testAbsorberWide);

		pm.addGizmo(testAbsorberHigh);

		Assert.assertNotNull("Absorber should be not null as is found on board", pm.getGizmoByName(testAbsorberShort.getName()) );

		Assert.assertEquals("Wide absorber should be 10 wide", 10, (int) testAbsorberWide.getWidth());

		Assert.assertEquals("Wide absorber should be 10 high", 10, (int) testAbsorberHigh.getHeight());

	}

	@Test
	public void canAddBall(){

		Ball testBall = new Ball(0, 0, new Vect(0, 0)); // test with initial velocity 0

		Ball testBallFast = new Ball(1, 1, new Vect(100, 100));

		pm.addGizmo(testBall);

		pm.addGizmo(testBallFast);

		Assert.assertNotNull("Ball should be not null as is found on board", pm.getGizmoByName(testBall.getName()));

		Assert.assertEquals("Regular ball should have starting velocity 0, 0", new Vect(0, 0), ((Ball) pm.getGizmoByName(testBall.getName())).getVelocity());

		Assert.assertEquals("Fast ball should have starting velocity 100, 100", new Vect(100, 100), ((Ball) pm.getGizmoByName(testBallFast.getName())).getVelocity());
	}
}
