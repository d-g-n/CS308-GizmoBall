package model;

/**
 * The GizmoConstants class provides information about the board such as
 * board dimensions, frame rate, move time etc.
 * 
 */
public final class GizmoConstants {

	public static final int X_CELLS = 20;
	public static final int Y_CELLS = 20;
	public static final int BOARD_WIDTH = 600;
	public static final int BOARD_HEIGHT = 600;

	public static final double FRAME_RATE = 60;

	public static final double PHYSICS_FRAME_RATE = 1000;
	public static final double MOVE_TIME = (double) (1 / (PHYSICS_FRAME_RATE));

}
