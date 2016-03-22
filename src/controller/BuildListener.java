package controller;

import java.awt.event.*;
import java.util.HashMap;

import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.ProjectManager;
import view.BuildGUI;

/**
 * 
 * The BuildListener class handles all the events on the build panel.
 * 
 */
public class BuildListener implements ActionListener, ChangeListener, WindowListener {

	private BuildGUI view;
	private ProjectManager pm;
	private Timer visualTimer;
	private HashMap<String,String> gizmoMap;

	public BuildListener(BuildGUI view, Timer visualTimer, ProjectManager pm) {
		this.view = view;
		this.visualTimer = visualTimer;
		this.pm = pm;
		this.gizmoMap = new HashMap<>();
		fillMap();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String statusLabel = "";
		String action = e.getActionCommand();

		pm.setCurrentCommand(action);

		// check highlevel first
		CommandMapper.Command getCom = CommandMapper.getCommandByUID(pm.getCurrentCommand());

		if(getCom != null && getCom.getCommandLevel().equals(CommandMapper.CommandLevel.BUTTON_LEVEL))
			getCom.getAction().onClickAndRelease(-1, -1, -1, -1); // questionable

		if(gizmoMap.containsKey(action))
			pm.setStatusLabel(gizmoMap.get(action));

		pm.pushVisualUpdate();

	}

	// need to move this to the commandlist file somewhere or just define it in the actual command func
	
	public void fillMap(){
		String label = "Click on any free position on the board to place the ";
		gizmoMap.put("add_ball",label + "ball");
		gizmoMap.put("add_trianglebumper", label + "triangle");
		gizmoMap.put("add_squarebumper", label + "square");
		gizmoMap.put("add_leftflipper", label + "left flipper");
		gizmoMap.put("add_rightflipper", label + "right flipper");
		gizmoMap.put("add_circlebumper", label + "circle");
		gizmoMap.put("add_deathsquare", label + "death square");
		gizmoMap.put("add_teleporter", label + "teleporter");
		gizmoMap.put("add_booster", label + "booster");
		gizmoMap.put("add_spinner", label + "spinner");
		gizmoMap.put("connect_gizmos", "Click and hold on the gizmo want to connect as the trigger, then drag the mouse over to the gizmo you want to connect as the action and release");
		gizmoMap.put("manip_rotate", "Click on the gizmo you want to rotate. (You cannot rotate an absorber, sorry!)");
		gizmoMap.put("keyconnect_gizmos", "Click on the Gizmo you want to connect a keyboard key to");
		gizmoMap.put("keydisconnect_gizmos","Select the key that you want to disconnect from the menu");
		gizmoMap.put("add_absorber", "Click and hold over the desired starting position of the absorber, then drag the mouse to it's desired end position and release");
		gizmoMap.put("disconnect_gizmos", "Click and hold on the gizmo want to disconnect as the trigger, then drag the mouse over to the gizmo you want to disconnect as the action and release");
		gizmoMap.put("manip_delete", "Click on the gizmo you want to delete");
		gizmoMap.put("manip_move", "Click and hold the mouse over the gizmo you wish to move, then drag the mouse to the gizmos new desired location and release");
		
	}

	// need to add commands for this aswell
	// might be a bit weird on client tho

	@Override
	public void stateChanged(ChangeEvent c) {

		JSlider source = (JSlider) c.getSource();

		if (source.getName() == "Gravity") {

			pm.setGravity(source.getValue());

		} else if (source.getName() == "Friction") {
			double fric = source.getValue() / 1000.0;
			pm.setFriction(fric,fric);

		}

	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		pm.dynamicModeOff();
		pm.setCurrentCommand("");
		pm.setStatusLabel("Score: " + pm.getScore() + " HighScore " + pm.getHighScore() + " Lives: " + pm.getLives());
		pm.setBuildModeOn(false);
		view.disposeFrame();
		visualTimer.start();
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}
