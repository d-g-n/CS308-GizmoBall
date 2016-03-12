package File_Testing;

import General_Testing.BaseTestCase;
import org.junit.Assert;
import org.junit.Test;

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

	}
}
