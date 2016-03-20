package Gizmo_Testing;

import General_Testing.BaseTestCase;
import gizmos.Absorber;
import gizmos.SquareBumper;
import org.junit.Test;

/**
 * Created by Declan on 20/03/2016.
 */
public class InteractionGizmoTest extends BaseTestCase {

	@Test
	public void canConnectGizmos(){

		SquareBumper testSquare = new SquareBumper(0, 0, 1, 1);

		Absorber testAbsorber = new Absorber(0, 1, 1, 10);

		pm.addGizmo(testSquare);

		pm.addGizmo(testAbsorber);
	}
}
