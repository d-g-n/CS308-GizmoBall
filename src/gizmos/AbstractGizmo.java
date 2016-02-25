package gizmos;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import view.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGizmo {

	protected double xpos, ypos, width, height;
	protected int gizAngle;
	protected double reflectionCoefficient;
	protected List<AbstractGizmo> gizmoListeners;
	protected String name;

	protected List<Circle> StoredCircles;
	protected List<LineSegment> StoredLines;
	protected Color gizCol;

	protected Shape gizShape;

	public AbstractGizmo(double x, double y, double width, double height, Color c, double rc) {
		this.xpos = Board.convertLtoPix(x);
		this.ypos = Board.convertLtoPix(y);
		this.width = Board.convertLtoPix(width);
		this.height = Board.convertLtoPix(height);

		this.gizAngle = 0;

		this.gizmoListeners = new ArrayList<AbstractGizmo>();

		this.reflectionCoefficient = rc;
		this.gizCol = c;

		this.StoredCircles = new ArrayList<Circle>();
		this.StoredLines = new ArrayList<LineSegment>();
	}


	public void rotateClockwise(){ // this is experimental
		gizAngle += 90;

		// this needs work
		/*
		List<Circle> tempCirc = new ArrayList<>();

		for(Circle c : StoredCircles){

			double x1 = c.getCenter().x() - width/2;
			double y1 = c.getCenter().y() - height/2;

			tempCirc.add(new Circle(
					x1 * Math.cos(Math.toRadians(90)) - y1 * Math.sin(Math.toRadians(90)),
					x1 * Math.sin(Math.toRadians(90)) + y1 * Math.cos(Math.toRadians(90)),
					c.getRadius()
			));
		}

		StoredCircles = tempCirc;

		List<LineSegment> tempLine = new ArrayList<>();

		for(LineSegment ls : StoredLines){

			double x1 = ls.p1().x() - width/2;
			double y1 = ls.p1().y() - height/2;

			double x2 = ls.p2().x() - width/2;
			double y2 = ls.p2().y() - height/2;

			tempLine.add(new LineSegment(
					x1 * Math.cos(Math.toRadians(90)) - y1 * Math.sin(Math.toRadians(90)),
					x1 * Math.sin(Math.toRadians(90)) + y1 * Math.cos(Math.toRadians(90)),
					x2 * Math.cos(Math.toRadians(90)) - y2 * Math.sin(Math.toRadians(90)),
					x2 * Math.sin(Math.toRadians(90)) + y2 * Math.cos(Math.toRadians(90))
			));
		}

		StoredLines = tempLine;
		*/

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


	public void setShape(Shape gizShape){
		this.gizShape = gizShape;
	}
	


	/**
	 * This method is called by the engine when the ball collides with this
	 * gizmo Can also be extended to provide the ball-holding functionality seen
	 * in the Absorber.
	 */
	public void onHit() {
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


	public Shape getShape(){ return gizShape; }

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

}
