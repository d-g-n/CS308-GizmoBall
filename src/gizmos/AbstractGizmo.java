package gizmos;

import model.ProjectManager;
import physics.*;
import view.Board;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGizmo {

	protected double xpos, ypos, width, height;
	protected int gizAngle;
	protected double reflectionCoefficient, angleVel;
	protected Vect rotateAroundPoint;
	protected List<AbstractGizmo> gizmoListeners;
	protected String name;
	protected int score;
	
	protected List<Circle> StoredCircles;
	protected List<LineSegment> StoredLines;
	protected Color gizCol;

	protected Shape gizShape;

	public AbstractGizmo(double x, double y, double width, double height, Color c, double rc) {
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;

		this.gizAngle = 0;
		this.name = ""+(int)x+"_"+(int)y; // Added Very Basic naming to new gizmos that have not been read in from file

		this.angleVel = 0;
		this.rotateAroundPoint = this.getCenter();

		this.gizmoListeners = new ArrayList<AbstractGizmo>();

		this.reflectionCoefficient = rc;
		this.gizCol = c;

		this.StoredCircles = new ArrayList<Circle>();
		this.StoredLines = new ArrayList<LineSegment>();

		setGizPhysics(xpos, ypos);
		setGizShape(xpos, ypos);
	}


	public void rotateClockwise(){ // this is experimental
		gizAngle += 90;

		rotatePhysicsAroundPoint(xpos + (width/2), ypos + (height/2), 90);

	}

	public void rotatePhysicsAroundPoint(Vect pivot, double degrees){
		rotatePhysicsAroundPoint(pivot.x(), pivot.y(), degrees);
	}

	public void rotatePhysicsAroundPoint(double pivotX, double pivotY, double degrees){

		Angle rad = new Angle(Math.toRadians(degrees));

		List<Circle> tempCirc = new ArrayList<>();

		for(Circle c : StoredCircles){

			tempCirc.add(Geometry.rotateAround(c, new Vect(pivotX, pivotY), rad));

		}

		StoredCircles = tempCirc;

		List<LineSegment> tempLine = new ArrayList<>();

		for(LineSegment ls : StoredLines){

			tempLine.add(Geometry.rotateAround(ls, new Vect(pivotX, pivotY), rad));
		}

		StoredLines = tempLine;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setPos(double xpos, double ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
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

	public void addGizmoListener(AbstractGizmo listener){
		this.gizmoListeners.add(listener);
	}
	
	public void removeGizmoListener(AbstractGizmo listener){
		this.gizmoListeners.remove(listener);
	}


	public void setShape(Shape gizShape){
		this.gizShape = gizShape;
	}

	public void setGizShape(double x, double y) {}

	public void setGizPhysics(double x, double y) {}

	public void deletePhysics() {

		this.StoredLines.clear();
		this.StoredCircles.clear();

	}

	public void moveGizmo(int x, int y){

		this.setPos(x, y);
		this.deletePhysics();
		this.setGizShape(x, y);
		this.setGizPhysics(x, y);

	}

	/**
	 * This method is called by the engine when the ball collides with this
	 * gizmo Can also be extended to provide the ball-holding functionality seen
	 * in the Absorber.
	 */
	public void onHit(AbstractGizmo hitGiz) {
		for (AbstractGizmo g : gizmoListeners) {
			g.doTrigger();
		}
	}

	/**
	 * This method is called by the gizmo that has been hit Will likely need to
	 * extend this method to provide desired functionality
	 */
	public void doTrigger() {
		// there is no default action but needed here to override it.
	}



	public Vect getCenter() {
		return new Vect(xpos + (width / 2), ypos + (height / 2));
	}

	public Shape getShape(){ return gizShape; }

	public void doPhysicsCalculations() {}

	public String getName(){
		return this.name;
	}

	public double getXPos() {
		return xpos;
	}

	public double getYPos() {
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

	public int getGizAngle() {
		return gizAngle;
	}

	public Color getGizCol() {
		return gizCol;
	}

	public List<Circle> getStoredCircles() {
		return StoredCircles;
	}

	public List<LineSegment> getStoredLines() {
		return StoredLines;
	}
	
	public double getAngularVelocity(){
		return angleVel;
	}

	public Vect getRotateAroundPoint(){
		return rotateAroundPoint;
	}
}
