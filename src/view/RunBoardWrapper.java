package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import controller.BoardListener;
import model.GizmoConstants;
import model.ProjectManager;

/**
 * purely for testing
 * Created by Declan on 06/02/2016.
 */
public class RunBoardWrapper {

	RunBoard gp;

	public RunBoardWrapper(ProjectManager pm){

		gp = new RunBoard(pm);
		gp.addMouseListener(new BoardListener(pm, this.getBoard()));
		gp.setPreferredSize(new Dimension(GizmoConstants.BOARD_WIDTH, GizmoConstants.BOARD_HEIGHT));
	}

	public RunBoard getGizPanel(){
		return gp;
	}

	public JPanel getBoard(){
		JPanel testFrame = new JPanel();
		testFrame.setLayout(new BorderLayout());
		testFrame.add(gp, BorderLayout.CENTER);

		return testFrame;
	}

}