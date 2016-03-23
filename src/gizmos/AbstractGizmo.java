package gizmos;

import javafx.scene.transform.Transform;
import physics.*;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGizmo {

	protected double xpos, ypos, width, height;
	protected int gizAngle;
	protected double reflectionCoefficient, angleVel;
	protected Vect rotateAroundPoint;
	protected List<AbstractGizmo> gizmoListeners;
	protected String name, type;
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
		this.type = "";

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

		rotatePhysicsAroundPoint(this.getCenter(), 90);
		rotateVisualAroundPoint(this.getCenter(), 90);

	}

	private void rotateVisualAroundPoint(Vect center, int i) {

		AffineTransform at = new AffineTransform();

		at.rotate(Math.toRadians(i), center.x(), center.y());

		Shape path = at.createTransformedShape(this.gizShape);

		setShape(path);

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

	/**
	 * Set the shape of the gizmo. This method is overridden by 
	 * the setGizShape method in the gizmo classes.
	 * @param x The x coordinate of the gizmo
	 * @param y The y coordinate of the gizmo
	 */
	public void setGizShape(double x, double y) {}

	/**
	 * Set the physics definitions for the gizmo. This method is overridden by
	 * the setGizPhysics method in the gizmo classes.
	 * @param x The x coordinate of the gizmo
	 * @param y The y coordinate of the gizmo
	 */
	public void setGizPhysics(double x, double y) {}

	/**
	 * Delete all the physics definitions.
	 */
	public void deletePhysics() {
		this.StoredLines.clear();
		this.StoredCircles.clear();
	}

	public void moveGizmo(int x, int y){

		if (this.getClass() == Ball.class) {
			this.setPos(x + 0.5, y + 0.5);
		} else {
			this.setPos(x, y);
		}
		this.deletePhysics();
		this.setGizShape(x, y);
		this.setGizPhysics(x, y);

		for(int i = 0; i < (((double)this.getGizAngle()/360) * 4); i++){
			rotatePhysicsAroundPoint(this.getCenter(), 90);
			rotateVisualAroundPoint(this.getCenter(), 90);
		}

	}

	/**
	 * This method is called by the engine when the ball collides with this
	 * gizmo Can also be extended to provide the ball-holding functionality seen
	 * in the Absorber.
	 */
	public void onHit(AbstractGizmo hitGiz) {

	}

	/**
	 * This method is called by the gizmo that has been hit and it is overridden
	 * by the method inside the different gizmo classes, to provide specific functionality
	 * for each gizmo.
	 */
	public void doTrigger() {
		// there is no default action but needed here to override it.
	}

	public void sendTriggers(){
		for (AbstractGizmo g : gizmoListeners) {
			g.doTrigger();
		}
	}


	public Vect getCenter() {
		return new Vect(xpos + (width / 2), ypos + (height / 2));
	}

	public Shape getShape(){ return gizShape; }

	/**
	 * Recalculate the physics of the gizmo after a possible
	 * change of its location
	 */
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

	public String getType(){
		return this.type;
	}

	public List<AbstractGizmo> getGizmoListeners() {
		return gizmoListeners;
	}
}
