package gizmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class AbstractGizmo extends Observable{
	
	protected int xpos, ypos, width, height;
	protected double reflectionCoefficient;
	protected List<AbstractGizmo> gizmoListeners;
	
	public AbstractGizmo(int x, int y, int width, int height, double rCo){
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;
		this.reflectionCoefficient = rCo;

		this.gizmoListeners = new ArrayList<AbstractGizmo>();
	}

	public double getReflectionCoefficient(){
		return reflectionCoefficient;
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
