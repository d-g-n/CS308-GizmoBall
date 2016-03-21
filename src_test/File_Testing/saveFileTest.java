package File_Testing;

import General_Testing.BaseTestCase;
import gizmos.*;
import org.junit.Assert;
import org.junit.Test;
import physics.Vect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;

/**
 * Created by Declan on 19/03/2016.
 */
public class SaveFileTest extends BaseTestCase{

	static final String savePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\boards\\save_test.txt";

	private boolean saveFileHasLine(String line){
		File saveFile = new File(savePath);

		try {

			BufferedReader fileReader = new BufferedReader(new FileReader(saveFile));

			return fileReader.lines().anyMatch(s -> s.equals(line));

		} catch (FileNotFoundException e) {}

		return false;
	}

	@Test
	public void canSaveGravityAndFriction(){

		pm.saveAs(savePath);

		Assert.assertTrue("Should have default gravity line", saveFileHasLine("Gravity 25.0"));

		Assert.assertTrue("Should have default friction", saveFileHasLine("Friction 0.025 0.025"));

	}

	@Test
	public void canSaveGizmos(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		pm.addGizmo(testSquare);

		pm.saveAs(savePath);

		Assert.assertTrue("Should have square line saved", saveFileHasLine("Square 0_0 0 0"));

	}

	@Test
	public void canSaveBall(){

		Ball testBall = new Ball(0, 0, new Vect(0, 0));

		pm.addGizmo(testBall);

		pm.saveAs(savePath);

		Assert.assertTrue("Should have ball saved", saveFileHasLine("Ball 0_0 0.0 0.0 0.0 0.0"));

	}

	@Test
	public void canSaveConnects(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		LeftFlipper testLF = new LeftFlipper(0, 1);

		RightFlipper testRF = new RightFlipper(0, 3);

		pm.addGizmo(testSquare);

		pm.addGizmo(testLF);

		pm.addGizmo(testRF);

		testSquare.addGizmoListener(testLF);

		testSquare.addGizmoListener(testRF);

		pm.saveAs(savePath);

		Assert.assertTrue("Square should be connected to testLF", saveFileHasLine("Connect 0_0 0_1"));

		Assert.assertTrue("Square should be connected to testRF", saveFileHasLine("Connect 0_0 0_3"));

	}

	@Test
	public void canSaveKeyConnects(){

		SquareBumper testSquare = new SquareBumper(0, 0);

		CircleBumper testCircle = new CircleBumper(0, 1);

		pm.addGizmo(testCircle);

		pm.addGizmo(testSquare);

		pm.addKeyConnect(testSquare.getName(), 32, "down");

		pm.addKeyConnect(testCircle.getName(), 32, "down");

		pm.saveAs(savePath);

		Assert.assertTrue("Key32 down should be connected to testSquare", saveFileHasLine("KeyConnect key 32 down 0_0"));

		Assert.assertTrue("Key32 down should also be connected to testCircle", saveFileHasLine("KeyConnect key 32 down 0_1"));

	}


}
