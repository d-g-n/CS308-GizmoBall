package gizmos;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import view.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGizmo {

	protected double xpos, ypos, width, height;
	protected Angle gizAngle;
	protected double reflectionCoefficient;
	protected List<AbstractGizmo> gizmoListeners;
	protected String name;

	protected List<Circle> StoredCircles;
	protected List<LineSegment> StoredLines;
	protected Color gizCol;
	protected Vect vector;

	public AbstractGizmo(double x, double y, double width, double height, int angDegrees, Color c, double rc) {
		this.xpos = x * (Board.BOARD_WIDTH / Board.X_CELLS);
		this.ypos = y * (Board.BOARD_HEIGHT / Board.Y_CELLS);
		this.width = width * Board.BOARD_WIDTH / Board.X_CELLS;
		this.height = height * Board.BOARD_HEIGHT / Board.Y_CELLS;

		this.gizAngle = new Angle(Math.toRadians(angDegrees));

		this.gizmoListeners = new ArrayList<AbstractGizmo>();

		this.reflectionCoefficient = rc;
		this.gizCol = c;

		this.StoredCircles = new ArrayList<Circle>();
		this.StoredLines = new ArrayList<LineSegment>();
		vector = new Vect(x,y);
	}

	public Vect getVect(){
		return vector;
	}

	public void rotateClockwise(){
		Angle clockwiseRotate = new Angle(Math.toRadians(90));
		gizAngle = gizAngle.plus(clockwiseRotate);
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public double getXpos() {
		return xpos;
	}

	public double getYpos() {
		return ypos;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getReflectionCoefficient() {
		return reflectionCoefficient;
	}

	public Angle getGizAngle() {
		return gizAngle;
	}

	public Color getGizCol() {
		return gizCol;
	}

	public List<Circle> getStoredCircles() {
		return StoredCircles;
	}

	public void setGizAngle(Angle gizAngle) {
		this.gizAngle = gizAngle;
	}

	public List<LineSegment> getStoredLines() {
		return StoredLines;
	}

	public void setPos(double xpos, double ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	public void setGizAngle(int ang) {
		this.gizAngle = new Angle(Math.toRadians(ang));
	}

	protected void addPhysicsCircle(double x, double y, double r){
		StoredCircles.add(new Circle(x, y, r));
	}

	protected void addPhysicsPath(List<Vect> lv){
		
		Vect lastVect = null;

		for(Vect curVect : lv){
			if(lastVect == null){
				lastVect = curVect;
				continue;
			}

			StoredCircles.add(new Circle(lastVect, 0.0));
			StoredLines.add(new LineSegment(lastVect, curVect));

			lastVect = curVect;
		}
	}
	
	public void clearPhysicsCircles(){
		this.getStoredCircles().clear();
	}
	
	public void addPhysicsCircle(Circle c){
		StoredCircles.add(c);
	}
	
	public void addPhysicsLine(LineSegment line){
		StoredLines.add(line);
	}
	
	public void rotate(){
		// there is no default action but needed here to override it.
	}
	
	public Shape getShape(){
		// there is no default action but needed here to override it.
		return null;
	}

	/**
	 * This method is called by the engine when the ball collides with this
	 * gizmo Can also be extended to provide the ball-holding functionality seen
	 * in the Absorber.
	 */
	public void onHit() {
		for (AbstractGizmo g : gizmoListeners) {
			g.onCollision();
		}
	}

	/**
	 * This method is called by the gizmo that has been hit Will likely need to
	 * extend this method to provide desired functionality
	 */
	public void onCollision() {
		// there is no default action but needed here to override it.
	}

	public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setMoving(){
		
	}

}
