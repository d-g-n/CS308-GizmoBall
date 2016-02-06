package gizmos;

import java.util.List;

/**
 * Created by Declan on 06/02/2016.
 */
public final class ShapeGlobals {

	// note each line delimits a line drawn in space
	// will probably need to add modifiers so it can be just circles too
	// could probably do it via enums hooray
	// doing it this way lets us have rounded rectangles or something

	// note that the "drawing bounds" for the shape is 1 to 100 drawn from top left to bottom right
	public final static List<CircleEndLine> Gizmo_SquareBumper = ShapeFactory(new int [][]
			{
					{1, 1, 1, 100}, // format is as such {xsource, ysource, xdest, ydest}
					{1, 1, 100, 1},
					{100, 1, 1, 100},
					{1, 100, 100, 1},
			}
	);




	private static List<CircleEndLine> ShapeFactory(int[][] rawList){

		return null;
	}
}
