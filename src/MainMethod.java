import view.GizmoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * purely for testing
 * Created by Declan on 06/02/2016.
 */
public class MainMethod {

	public static void main(String[] args){
		new MainMethod();
	}


	public MainMethod(){
		JFrame testFrame = new JFrame("test panel");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.setLayout(new BorderLayout());

		GizmoPanel gp = new GizmoPanel();

		gp.setPreferredSize(new Dimension(700, 700));

		testFrame.add(gp, BorderLayout.CENTER);
		testFrame.pack();

		testFrame.setVisible(true);
	}
}