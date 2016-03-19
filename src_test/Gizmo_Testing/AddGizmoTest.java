package Gizmo_Testing;

import General_Testing.BaseTestCase;
import gizmos.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Declan on 19/03/2016.
 */
public class AddGizmoTest extends BaseTestCase {

	@Test
	public void canAddStandardGizmo(){

		// Standard gizmos being squarebumper, circle or triangle

		SquareBumper testSquare = new SquareBumper(0, 0, 1, 1);

		TriangleBumper testTriangle = new TriangleBumper(1, 0, 1, 1);

		CircleBumper testCircle = new CircleBumper(2, 0, 1, 1);

		pm.addGizmo(testSquare);
		pm.addGizmo(testTriangle);
		pm.addGizmo(testCircle);

		Assert.assertNotEquals("Square should be not null as is found on board", pm.getGizmoByName(testSquare.getName()), null );

		Assert.assertNotEquals("Triangle should be not null as is found on board", pm.getGizmoByName(testTriangle.getName()), null );

		Assert.assertNotEquals("Circle should be not null as is found on board", pm.getGizmoByName(testCircle.getName()), null );


	}

	@Test
	public void canAddFlipperGizmos(){

		LeftFlipper testLeftFlipper = new LeftFlipper(0, 0);

		RightFlipper testRightFlipper = new RightFlipper(2, 0);

		pm.addGizmo(testLeftFlipper);

		pm.addGizmo(testRightFlipper);

		Assert.assertNotEquals("Left Flipper should be not null as is found on board", pm.getGizmoByName(testLeftFlipper.getName()), null );

		Assert.assertNotEquals("Right Flipper should be not null as is found on board", pm.getGizmoByName(testRightFlipper.getName()), null );

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

		Assert.assertNotEquals("Absorber should be not null as is found on board", pm.getGizmoByName(testAbsorberShort.getName()), null );

		Assert.assertEquals("Wide absorber should be 10 wide", 10, (int) testAbsorberWide.getWidth());

		Assert.assertEquals("Wide absorber should be 10 high", 10, (int) testAbsorberHigh.getHeight());


	}
}
