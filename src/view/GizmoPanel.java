package view;

import gizmos.AbstractGizmo;
import gizmos.VisualShape;
import model.ProjectManager;

import javax.swing.*;
import java.awt.*;

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
			for(VisualShape vs : giz.getStoredVisualShapes()){
				// for square, first two is x, y in percent, next two is width and height in percent
				if(vs.getType().equals("Square")){
					int localX = vs.getValList().get(0)/100;
					int localY = vs.getValList().get(1)/100;
					int localWidth = vs.getValList().get(2)/100;
					int localHeight = vs.getValList().get(3)/100;

					g.setColor(vs.getColour());
					g.fillRect(((this.getWidth()/CONS_SIZE) * xC) + (localX * (this.getWidth()/CONS_SIZE)), ((this.getWidth()/CONS_SIZE) * yC) + (localX * (this.getWidth()/CONS_SIZE)), (this.getWidth()/CONS_SIZE) * localWidth, (this.getWidth()/CONS_SIZE) * localHeight);
				}
			}
		}
	}
}
