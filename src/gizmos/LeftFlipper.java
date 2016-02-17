package gizmos;


import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class LeftFlipper extends AbstractGizmo {
	
	Point[] points;
	Point[] originPoints;
	Point center;

	public LeftFlipper(int x, int y, int width, int height, int degrees) {

		super(x, y, 2, 2, degrees,
				Color.blue, // colour of gizmo
				0.95 // reflection coefficient
		);
		
			points = new Point[4];
			originPoints = new Point[4];
			center = new Point(x + width/2,y + width/2);
			
			originPoints[0] = new Point(x,y);//start at top left
			originPoints[1] = new Point(x + width,y); // move to top right
			originPoints[2] =	new Point(x + width, y + height); //move to bottom right
			originPoints[3] = new Point(x,y + height);// move to bottom left
			
			for(int i =0; i < points.length; i++){
				points[i] = originPoints[i];
			}
		
	}
	@Override
	public void rotate(int angle){
		
		AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(angle),center.x, center.y);
		rotate.transform(originPoints, 0, points, 0, 4);
		
	}
	
	@Override
	public Shape getShape(){
		
		GeneralPath flipper = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		flipper.moveTo(points[0].x * 26.0,(points[0].y)*26);
		flipper.lineTo((points[1].x*26), (points[1].y*26.0));
		flipper.lineTo((points[2].x)*26, points[2].y*26.0);
		flipper.lineTo((points[3].x)*26.0, (points[3].y)*26.0);
		flipper.closePath();
		return flipper;
		
//		return new RoundRectangle2D.Double(
//				(26.0 * points[0].getY()),
//				(26.0 * points[0].getY()),
//				(26.0 * width),
//				(26.0 * height) * 0.25,
//				25,
//				100
//		);
		
	}
	
	public void addPhysics(){
		
	}
}
