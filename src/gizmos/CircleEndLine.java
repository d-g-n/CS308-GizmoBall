package gizmos;

/**
 * Supposed to represent a line with two 0 size circles stuck on the end, the physics engine interprets it properly
 * or something lol
 * Created by Declan on 06/02/2016.
 */
public class CircleEndLine {
	private int xSource, ySource, xDest, yDest;

	public CircleEndLine(int xs, int ys, int xd, int yd){
		this.xSource = xs;
		this.ySource = ys;
		this.xDest = xd;
		this.yDest = yd;
	}
}
