package gizmos;


import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

import physics.Angle;
import physics.Geometry;
import physics.Vect;

public class LeftFlipper extends AbstractGizmo {
	private boolean moving;
	private Vect[] points;
	private Vect[] originPoints;
	private Vect center;
	int angleVel;

	public LeftFlipper(double x, double y, double w, double h, int degrees) {

		super(x, y, w, h, degrees,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficient
		);


		    center = new Vect(xpos + width/2,ypos + width/2);
			originPoints = new Vect[4];
			points = new Vect[4];
			originPoints[0] = new Vect(xpos,ypos);
			originPoints[1] = new Vect(xpos+width,ypos);
			originPoints[2] = new Vect(xpos+width,ypos+height);
			originPoints[3] = new Vect(xpos,ypos+height);
			
			for(int i =0; i < points.length; i++){
				points[i] = originPoints[i];
			}
			
			moving = false;
			angleVel = 0;
	}
	@Override
	public void rotate(int angle){
		
		for(int i = 0; i < originPoints.length; i++)
		points[i] = Geometry.rotateAround(points[i], center, new Angle(45));
		
	
	}
	
	@Override
	public Shape getShape(){
		
		GeneralPath flipper = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		flipper.moveTo(points[0].x(),points[0].y());
		flipper.lineTo((points[1].x()), (points[1].y()));
		flipper.lineTo((points[2].x()), points[2].y());
		flipper.lineTo((points[3].x()), (points[3].y()));
		flipper.lineTo(points[0].x(), points[0].y());
		return flipper;
		}
		
	@Override
	public boolean isMoving(){
		return moving;
	}
	@Override
	public void setMoving(){
		moving = !moving;
	}
	
	public void addPhysics(){
		
	}
}
