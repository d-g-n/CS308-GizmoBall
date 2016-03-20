package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

import gizmos.AbstractGizmo;
import model.ProjectManager;

/**
 * 
 * The MagicKeyListener class implements the KeyListener interface
 * and it handles all the keyboard events.
 *
 */
public class MagicKeyListener implements KeyListener {
	private ProjectManager pm;
	
	public MagicKeyListener(ProjectManager pm){
		this.pm = pm;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (pm.getFocusedButton() == "Key Connect" && pm.getGizmoToKeyConnect() != null) {
			
		pm.addKeyConnect(pm.getGizmoToKeyConnect().getName(), key, "down");
		pm.setGizmoToKeyConnect(null);
	
		}
		
		if (pm.getFocusedButton() == "Key Disconnect" && pm.getGizmoToKeyDisconnect() != null) {
			
		pm.removeKeyConnect(pm.getGizmoToKeyDisconnect().getName(), key, "down");
		pm.removeKeyConnect(pm.getGizmoToKeyDisconnect().getName(), key, "up");
		pm.setGizmoToKeyDisconnect(null);
	
		}

		for(Map.Entry<Map.Entry<String, Integer>, List<AbstractGizmo>> ent : pm.getKeyConnects().entrySet()){
			Map.Entry<String, Integer> inEnt = ent.getKey();
			List<AbstractGizmo> gizList = ent.getValue();

			if(!inEnt.getKey().equals("down"))
				continue;

			if(key == inEnt.getValue()) {
				for(AbstractGizmo g : gizList){
					g.doTrigger();
				}
			}
		}

	}
		
	@Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		
		/*if (pm.getFocusedButton() == "Key Connect" && pm.getGizmoToKeyConnect() != null) {
			
		pm.addKeyConnect(pm.getGizmoToKeyConnect().getName(), key, "down");
		pm.setGizmoToKeyConnect(null);
	
		} */

		for(Map.Entry<Map.Entry<String, Integer>, List<AbstractGizmo>> ent : pm.getKeyConnects().entrySet()){
			Map.Entry<String, Integer> inEnt = ent.getKey();
			List<AbstractGizmo> gizList = ent.getValue();

			if(!inEnt.getKey().equals("up"))
				continue;

			if(key == inEnt.getValue()) {
				for(AbstractGizmo g : gizList){
					g.doTrigger();
				}
			}
		}
	}


}
