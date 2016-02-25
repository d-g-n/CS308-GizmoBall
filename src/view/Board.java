package view;


public final class Board {

	public static final int X_CELLS = 20;
	public static final int Y_CELLS = 20;
	public static final int BOARD_WIDTH = 800;
	public static final int BOARD_HEIGHT = 800;


	public static double convertLtoPix(double lValue){
		return lValue * (BOARD_WIDTH / X_CELLS);
	}
}
