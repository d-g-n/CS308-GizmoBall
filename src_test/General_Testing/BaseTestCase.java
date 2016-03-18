package General_Testing;

import model.ProjectManager;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Declan on 12/03/2016.
 */
public abstract class BaseTestCase {

	public ProjectManager pm;

	@Before
	public void setUp(){
		this.pm = new ProjectManager();
	}

	@After
	public void tearDown(){
		this.pm = null;
	}
}
