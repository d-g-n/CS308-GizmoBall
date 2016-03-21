package gizmos;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Teleporter extends AbstractGizmo {

	private double radius;
	Ball boardBall = null;

	public Teleporter(int x, int y) {
		super(x, y, 1, 1,
				Color.CYAN, // colour of gizmo
				1 // reflection coefficent
		);

		this.type = "Teleporter";


	}
	
	@Override
	public void setGizShape(double x, double y) {
		
		setShape(new Ellipse2D.Double(
				(x),
				(y),
				(width),
				(height)
		));
		
	}
	
	@Override
	public void setGizPhysics(double x, double y) {

		this.radius = 0.5;
		
		addPhysicsCircle(x + radius, y + radius, radius);
		
	}
	
	
	@Override
	public void onHit(AbstractGizmo hit) {
		// hold the ball in this

		boardBall = ((Ball) hit);

		if(boardBall != null) {


			java.util.List<AbstractGizmo> connected = this.gizmoListeners;
			
			for (AbstractGizmo a : connected) {
				
				if (a instanceof Teleporter) {
					
					boardBall.setPos(a.getXPos() + 1, a.getYPos() + 1);
					
					break;
				}
			}
		}

		super.onHit(hit);
	}


}
