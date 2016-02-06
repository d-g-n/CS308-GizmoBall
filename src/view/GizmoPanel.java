package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Declan on 06/02/2016.
 */
public class GizmoPanel extends JPanel {

	public static final int CONS_SIZE = 20;

	public GizmoPanel(){
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
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
	}
}
