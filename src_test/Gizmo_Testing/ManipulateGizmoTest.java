package Gizmo_Testing;

import General_Testing.BaseTestCase;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Declan on 19/03/2016.
 */
public class ManipulateGizmoTest extends BaseTestCase {

	@Test
	public void canRotateGizmo(){

		TriangleBumper testTriangle = new TriangleBumper(0, 0);

		// behaviour is generic in abstractgizmo class so no need to test for all

		pm.addGizmo(testTriangle);

		Assert.assertEquals("Triangle rotation should be 0 initially", 0, pm.getGizmoByName(testTriangle.getName()).getGizAngle());

		pm.getGizmoByName(testTriangle.getName()).rotateClockwise();

		Assert.assertEquals("Triangle rotation should be 90 now", 90, pm.getGizmoByName(testTriangle.getName()).getGizAngle());


	}

	@Test
	public void canMoveGizmo(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		pm.addGizmo(testSquare);

		Assert.assertTrue("Square should initially be at 0, 0", pm.getGizmoByName(testSquare.getName()).getXPos() == 0 && pm.getGizmoByName(testSquare.getName()).getYPos() == 0);

		pm.getGizmoByName(testSquare.getName()).moveGizmo(1, 1);

		Assert.assertTrue("Square should now be at 1, 1", pm.getGizmoByName(testSquare.getName()).getXPos() == 1 && pm.getGizmoByName(testSquare.getName()).getYPos() == 1);

	}

	@Test
	public void canDeleteGizmo(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		pm.addGizmo(testSquare);

		Assert.assertNotNull("Ensure that the added square actually exists", pm.getGizmoByName(testSquare.getName()));

		pm.deleteGizmo(testSquare);

		Assert.assertNull("Check that the added square is now gone", pm.getGizmoByName(testSquare.getName()));

	}
}
