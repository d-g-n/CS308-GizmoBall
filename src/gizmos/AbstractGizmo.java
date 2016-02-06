package gizmos;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class AbstractGizmo extends Observable{
	
	protected int xpos, ypos, width, height;
	protected Angle gizAngle;
	protected double reflectionCoefficient;
	protected List<AbstractGizmo> gizmoListeners;


	protected List<Circle> StoredCircles;
	protected List<LineSegment> StoredLines;

	protected List<VisualShape> StoredVisualShapes;
	
	public AbstractGizmo(int x, int y, int width, int height, double angRadians){
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;
		this.gizAngle = new Angle(angRadians);

		this.gizmoListeners = new ArrayList<AbstractGizmo>();

		GizmoSettings gs = new GizmoSettings(this);
		this.reflectionCoefficient = gs.getReflectionCoefficient();
		this.StoredCircles = gs.getParsedCircles();
		this.StoredLines = gs.getParsedLines();
		this.StoredVisualShapes = gs.getVisualShapes();

	}

	public int getXpos(){ return xpos; }
	public int getYpos(){ return ypos; }
	public double getReflectionCoefficient(){
		return reflectionCoefficient;
	}
	public Angle getGizAngle(){ return gizAngle; }

	public List<Circle> getStoredCircles(){ return StoredCircles; }
	public List<LineSegment> getStoredLines(){ return StoredLines; }
	public List<VisualShape> getStoredVisualShapes() { return StoredVisualShapes; }

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
