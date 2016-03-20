package Project_Testing;

import General_Testing.BaseTestCase;
import gizmos.Absorber;
import gizmos.Ball;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import junit.framework.Assert;
import org.junit.Test;
import physics.Vect;

/**
 * Created by Declan on 12/03/2016.
 */
public class GizmoFunctionsTest extends BaseTestCase {

	@Test
	public void testOverlappingGizmos(){

		// test regular overlapping

		SquareBumper testSquare = new SquareBumper(19, 19, 1, 1);

		pm.addGizmo(testSquare);

		TriangleBumper testTriangle = new TriangleBumper(19, 19, 1, 1);

		Assert.assertFalse("Should be false as 19/19 is taken", pm.canPlaceGizmoAt(testTriangle));

		Absorber testAbsorber = new Absorber(0, 0, 5, 5); // should occupy all tiles from 0, 0 to 4, 4

		pm.addGizmo(testAbsorber);

		// should not be able to place anything at 0, 0, 2, 2 or 4, 4

		SquareBumper sq00 = new SquareBumper(0, 0, 1, 1);

		SquareBumper sq22 = new SquareBumper(2, 2, 1, 1);

		SquareBumper sq44 = new SquareBumper(4, 4, 1, 1);

		Assert.assertFalse("Should be false as Absorber takes up 0, 0 -> 4, 4", pm.canPlaceGizmoAt(sq00));

		Assert.assertFalse("Should be false as Absorber takes up 0, 0 -> 4, 4", pm.canPlaceGizmoAt(sq22));

		Assert.assertFalse("Should be false as Absorber takes up 0, 0 -> 4, 4", pm.canPlaceGizmoAt(sq22));


		// now test ball placement, note that there's an absorber at 0, 0 and a square at 19, 19
		// should be able to place ball inside absorber but not inside square

		Ball b00 = new Ball(0, 0, new Vect(0, 0));

		Ball b1919 = new Ball(19, 19, new Vect(19, 19));

		Assert.assertTrue("Should be able to place the ball inside the absorber", pm.canPlaceGizmoAt(b00));

		Assert.assertFalse("Shoiuld not be able to add ball inside the square", pm.canPlaceGizmoAt(b1919));


	}

	@Test
	public void canSetGravity(){

		Assert.assertEquals("The default value of gravity should be 25 L/sec", 25.0, pm.getGravity());

		pm.setGravity(15.5);

		Assert.assertEquals("The new value should be 15.5 L/sec", 15.5, pm.getGravity());
	}

	@Test
	public void canSetFriction(){

		Assert.assertEquals("The default value of friction for mu and mu2 should be 0.025", 0.025, pm.getMuFriction());

		pm.setFriction(0.05, 0.05);

		Assert.assertEquals("The new value should be 0.05", 0.05, pm.getMuFriction());
	}
}
