package view;

import gizmos.AbstractGizmo;
import gizmos.VisualShape;
import model.ProjectManager;
import physics.Angle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Declan on 06/02/2016.
 */
public class GizmoPanel extends JPanel {

	public static final int CONS_SIZE = 20;

	public ProjectManager pm;

	public GizmoPanel(ProjectManager pm){
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.pm = pm;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		// Draw background
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());


		g.setColor(Color.gray);

		// Draw guidelines on x axis

		for(int i = 1; i <= CONS_SIZE; i++){
			g.drawLine(((this.getWidth()/CONS_SIZE) * i), 0, ((this.getWidth()/CONS_SIZE) * i), this.getHeight());
		}

		// Draw Guidelines on y axis

		for(int i = 1; i <= CONS_SIZE; i++){
			g.drawLine(0, ((this.getWidth()/CONS_SIZE) * i), this.getWidth(), ((this.getWidth()/CONS_SIZE) * i));
		}

		// Draw Sample shapes

		for(AbstractGizmo giz : pm.getBoardGizmos()){
			int xC = giz.getXpos();
			int yC = giz.getYpos();
			Angle ang = giz.getGizAngle();

			for(VisualShape vs : giz.getStoredVisualShapes()){

				AffineTransform pT = g2d.getTransform();
				// for square, first two is x, y in percent, next two is width and height in percent
				if(vs.getType().equals("Square")){
					int localX = vs.getValList().get(0)/100;
					int localY = vs.getValList().get(1)/100;
					int localWidth = vs.getValList().get(2)/100;
					int localHeight = vs.getValList().get(3)/100;

					g2d.setColor(vs.getColour());
					Rectangle shape = new Rectangle(((this.getWidth()/CONS_SIZE) * xC) + (localX * (this.getWidth()/CONS_SIZE)), ((this.getWidth()/CONS_SIZE) * yC) + (localY * (this.getWidth()/CONS_SIZE)), (this.getWidth()/CONS_SIZE) * localWidth, (this.getWidth()/CONS_SIZE) * localHeight);
					g2d.rotate(ang.radians());
					g2d.draw(shape);
					g2d.fill(shape);
				} else if(vs.getType().equals("Triangle")){
					int localX = vs.getValList().get(0)/100;
					int localY = vs.getValList().get(1)/100;
					int localWidth = vs.getValList().get(2)/100;
					int localHeight = vs.getValList().get(3)/100;

					g2d.setColor(vs.getColour());
					//Rectangle shape = new Rectangle(, , (this.getWidth()/CONS_SIZE) * localWidth, (this.getWidth()/CONS_SIZE) * localHeight);
					Polygon shape = new Polygon();
					shape.addPoint(((this.getWidth()/CONS_SIZE) * xC) + (localX * (this.getWidth()/CONS_SIZE)), ((this.getWidth()/CONS_SIZE) * yC) + (localY * (this.getWidth()/CONS_SIZE)));
					shape.addPoint(((this.getWidth()/CONS_SIZE) * xC) + (localX * (this.getWidth()/CONS_SIZE)) + ((this.getWidth()/CONS_SIZE) * localWidth), ((this.getWidth()/CONS_SIZE) * yC) + (localY * (this.getWidth()/CONS_SIZE)));
					shape.addPoint(((this.getWidth()/CONS_SIZE) * xC) + (localX * (this.getWidth()/CONS_SIZE)), ((this.getWidth()/CONS_SIZE) * yC) + (localY * (this.getWidth()/CONS_SIZE))+ ((this.getHeight()/CONS_SIZE) * localHeight));
					//g2d.rotate(ang.radians()); // this does not rotate around the center point lol
					g2d.rotate(ang.radians(), shape.getBounds2D().getX() + (this.getWidth()/CONS_SIZE)/2, shape.getBounds2D().getY() + (this.getHeight()/CONS_SIZE)/2);

					g2d.draw(shape);

					g2d.fill(shape);
				}

				g2d.setTransform(pT);
			}
		}
	}
}
