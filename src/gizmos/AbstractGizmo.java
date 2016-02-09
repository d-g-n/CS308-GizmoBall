package gizmos;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class AbstractGizmo extends Observable{
	
	protected double xpos, ypos, width, height;
	protected Angle gizAngle;
	protected double reflectionCoefficient;
	protected List<AbstractGizmo> gizmoListeners;


	protected List<Circle> StoredCircles;
	protected List<LineSegment> StoredLines;

	protected List<VisualShape> StoredVisualShapes;
	
	public AbstractGizmo(double x, double y, double width, double height, int angDegrees){
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;
		this.gizAngle = new Angle(Math.toRadians(angDegrees));

		this.gizmoListeners = new ArrayList<AbstractGizmo>();

		GizmoSettings gs = new GizmoSettings(this);
		this.reflectionCoefficient = gs.getReflectionCoefficient();
		this.StoredCircles = gs.getParsedCircles();
		this.StoredLines = gs.getParsedLines();
		this.StoredVisualShapes = gs.getVisualShapes();

	}

	public double getXpos(){ return xpos; }
	public double getYpos(){ return ypos; }

	public double getWidth(){ return width; }
	public double getHeight(){ return height; }

	public double getReflectionCoefficient(){
		return reflectionCoefficient;
	}
	public Angle getGizAngle(){ return gizAngle; }

	public List<Circle> getStoredCircles(){ return StoredCircles; }
	public List<LineSegment> getStoredLines(){ return StoredLines; }
	public List<VisualShape> getStoredVisualShapes() { return StoredVisualShapes; }


	public void setPos(double xpos, double ypos){
		this.xpos = xpos;
		this.ypos = ypos;
	}

	/**
	 * This method is called by the engine when the ball collides with this gizmo
	 * Can also be extended to provide the ball-holding functionality seen in the Absorber.
	 */
	public void onHit(){
		for(AbstractGizmo g : gizmoListeners){
			g.doAction();
		}
	}

	/**
	 * This method is called by the gizmo that has been hit
	 * Will likely need to extend this method to provide desired functionality
	 */
	public void doAction(){
		// there is no default action but needed here to override it.
	}

}
