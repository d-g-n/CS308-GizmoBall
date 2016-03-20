package Project_Testing;

import General_Testing.BaseTestCase;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Declan on 12/03/2016.
 */
public class GizmoFunctionsTest extends BaseTestCase {

	@Test
	public void testOverlappingGizmos(){

	}

	@Test
	public void canSetGravity(){

		Assert.assertEquals("The default value of gravity should be 25 L/sec", 25.0, pm.getGravity());

		pm.setGravity(15.5);

		Assert.assertEquals("The new value should be 15.5 L/sec", 15.5, pm.getGravity());
	}

	@Test
	public void canSetFriction(){

		Assert.assertEquals("The default value of friction for mu and mu2 should be 0.025", 0.025, pm.getFriction());

		pm.setFriction(0.05);

		Assert.assertEquals("The new value should be 0.05", 0.05, pm.getFriction());
	}
}
