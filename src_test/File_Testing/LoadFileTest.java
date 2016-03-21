package File_Testing;

import General_Testing.BaseTestCase;
import gizmos.AbstractGizmo;
import gizmos.SquareBumper;
import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Declan on 12/03/2016.
 */
public class LoadFileTest extends BaseTestCase {

	@Test
	public void testCanLoadFile(){

		Assert.assertTrue("Expect to start with only the outer walls", pm.getBoardGizmos().size() == 4);

		pm.loadFile("boards/testboard.txt");

		Assert.assertTrue("Expect to now have more than 0 gizmos", pm.getBoardGizmos().size() > 4);
	}

	@Test
	public void testLoadsAllGizmos(){

		/*
		Triangle T1 0 0

		Square S1 1 0

		Circle C1 2 0

		LeftFlipper LF1 3 0

		RightFlipper RF1 5 0

		Absorber A 0 2 5 3

		 */

		// just check the triangle, circle and absorber

		pm.loadFile("boards/testboard.txt");

		Assert.assertFalse("Should not be able to place anything in 0 0", pm.canPlaceGizmoAt(new SquareBumper(0, 0)));

		Assert.assertFalse("Should not be able to place anything in 4 1 due to flippers being 2x2", pm.canPlaceGizmoAt(new SquareBumper(4, 1)));
	}

	@Test
	public void testRotateGizmos(){

		pm.loadFile("boards/testboard.txt");

		// T1 AT 0, 0 has been rotated


		Assert.assertTrue("Angle should be 90 degrees because it has been rotated once", pm.getGizmoByName("T1").getGizAngle() == 90);

		// C1 has not been rotated

		Assert.assertTrue("Angle should be 0 degrees because has not been rotated", pm.getGizmoByName("C1").getGizAngle() == 0);


	}

	@Test
	public void testGizmoConnects(){
		// A should be a listener of S1

		pm.loadFile("boards/testboard.txt");

		Assert.assertEquals("Should only have one listener", 1, pm.getGizmoByName("S1").getGizmoListeners().size());

		Assert.assertTrue("First listener should be A", pm.getGizmoByName("S1").getGizmoListeners().get(0).getName().equals("A"));
	}

	@Test
	public void testGizmoKeyConnects(){

		pm.loadFile("boards/testboard.txt");

		//KeyConnect key 32 down LF1

		Assert.assertEquals("Should only have one keyconnect", 1, pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)).size());

		Assert.assertTrue("First connection should be to LF1", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)).get(0).equals(pm.getGizmoByName("LF1")));

	}
}
