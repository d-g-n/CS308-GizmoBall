package Gizmo_Testing;

import General_Testing.BaseTestCase;
import gizmos.Absorber;
import gizmos.LeftFlipper;
import gizmos.SquareBumper;
import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;

/**
 * Created by Declan on 20/03/2016.
 */
public class InteractionGizmoTest extends BaseTestCase {

	@Test
	public void canConnectGizmos(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		Absorber testAbsorber = new Absorber(0, 1, 1, 10);

		LeftFlipper testFlipper = new LeftFlipper(5, 5);

		pm.addGizmo(testSquare);

		pm.addGizmo(testAbsorber);

		pm.addGizmo(testFlipper);

		// test is same as file loading except is for project manager methods

		Assert.assertEquals("Should have 0 listeners", 0, pm.getGizmoByName(testSquare.getName()).getGizmoListeners().size());

		pm.getGizmoByName(testSquare.getName()).addGizmoListener(testAbsorber);

		Assert.assertEquals("Should only have one listener", 1, pm.getGizmoByName(testSquare.getName()).getGizmoListeners().size());

		Assert.assertTrue("First listener should be testAbsorber", pm.getGizmoByName(testSquare.getName()).getGizmoListeners().get(0).getName().equals(testAbsorber.getName()));

		// now test for multiple listeners on one gizmo firing events

		pm.getGizmoByName(testSquare.getName()).addGizmoListener(testFlipper);

		Assert.assertEquals("Should now have two listeners", 2, pm.getGizmoByName(testSquare.getName()).getGizmoListeners().size());

		Assert.assertTrue("Second listener should be testFlipper", pm.getGizmoByName(testSquare.getName()).getGizmoListeners().get(1).getName().equals(testFlipper.getName()));

	}

	@Test
	public void canGizmoKeyConnects(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		Absorber testAbsorber = new Absorber(0, 1, 1, 10);

		LeftFlipper testFlipper = new LeftFlipper(5, 5);

		pm.addGizmo(testSquare);

		pm.addGizmo(testAbsorber);

		pm.addGizmo(testFlipper);

		// test is same as file loading except is for project manager methods

		Assert.assertNull("Should only have 0 keyconnects for that mapping", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)));

		pm.addKeyConnect(testAbsorber.getName(), 32, "down");

		Assert.assertNotNull("Should now have keyconnects for that mapping", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)));

		Assert.assertTrue("First connection should be to testAbsorber", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)).get(0).equals(pm.getGizmoByName(testAbsorber.getName())));

		// test that we can have multiple gizmos connected to one key

		pm.addKeyConnect(testFlipper.getName(), 32, "down");

		Assert.assertTrue("Second connection should be to testFlipper", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)).get(1).equals(pm.getGizmoByName(testFlipper.getName())));


	}

	@Test
	public void canGizmoDisconnect(){

		// repeated code but it's easier this way rather than iterating to find the name

		SquareBumper testSquare = new SquareBumper(0, 0);

		Absorber testAbsorber = new Absorber(0, 1, 1, 10);

		pm.addGizmo(testSquare);

		pm.addGizmo(testAbsorber);

		pm.getGizmoByName(testSquare.getName()).addGizmoListener(testAbsorber);

		Assert.assertEquals("Should have 1 listener", 1, pm.getGizmoByName(testSquare.getName()).getGizmoListeners().size());

		Assert.assertTrue("First listener should be A", pm.getGizmoByName(testSquare.getName()).getGizmoListeners().get(0).getName().equals(testAbsorber.getName()));

		pm.getGizmoByName(testSquare.getName()).removeGizmoListener(testAbsorber);

		Assert.assertEquals("Should have 0 listeners", 0, pm.getGizmoByName(testSquare.getName()).getGizmoListeners().size());

	}

	@Test
	public void canGizmoKeyDisconnect(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		Absorber testAbsorber = new Absorber(0, 1, 1, 10);

		LeftFlipper testFlipper = new LeftFlipper(5, 5);

		pm.addGizmo(testSquare);

		pm.addGizmo(testAbsorber);

		pm.addGizmo(testFlipper);

		// test is same as file loading except is for project manager methods

		Assert.assertNull("Should only have 0 keyconnects for that mapping", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)));

		pm.addKeyConnect(testAbsorber.getName(), 32, "down");

		Assert.assertNotNull("Should now have keyconnects for that mapping", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)));

		pm.removeKeyConnect(testAbsorber.getName(), 32, "down");

		Assert.assertNull("Should only have 0 keyconnects for that mapping after removal", pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>("down", 32)));


	}
}
